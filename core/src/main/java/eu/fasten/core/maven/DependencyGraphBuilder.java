/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.fasten.core.maven;

import eu.fasten.core.data.Constants;
import eu.fasten.core.data.metadatadb.codegen.tables.Dependencies;
import eu.fasten.core.data.metadatadb.codegen.tables.PackageVersions;
import eu.fasten.core.data.metadatadb.codegen.tables.Packages;
import eu.fasten.core.dbconnectors.PostgresConnector;
import eu.fasten.core.maven.data.Dependency;
import eu.fasten.core.maven.data.Revision;
import eu.fasten.core.maven.data.DependencyEdge;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jooq.DSLContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DependencyGraphBuilder {

    private static final Logger logger = LoggerFactory.getLogger(DependencyGraphBuilder.class);

    public static void main(String[] args) throws SQLException {
        var tsStart = System.currentTimeMillis();
        var dbContext = PostgresConnector.getDSLContext("jdbc:postgresql://localhost:5432/fasten_java", "fastenro");
        var graphBuilder = new DependencyGraphBuilder();
        var graph = graphBuilder.buildDependencyGraph(dbContext);
        var tsEnd = System.currentTimeMillis();
        System.out.println("____________________________________________________________________");
        System.out.println("Graph has " + graph.vertexSet().size() + " nodes and "
                + graph.edgeSet().size() + " edges (" + (tsEnd - tsStart) + " ms)");
    }

    public Map<Revision, List<Dependency>> getDependencyList(DSLContext dbContext) {

        return dbContext.select(Packages.PACKAGES.PACKAGE_NAME,
                        PackageVersions.PACKAGE_VERSIONS.VERSION,
                        Dependencies.DEPENDENCIES.METADATA,
                        PackageVersions.PACKAGE_VERSIONS.CREATED_AT)
                .from(Packages.PACKAGES)
                .rightJoin(PackageVersions.PACKAGE_VERSIONS)
                .on(PackageVersions.PACKAGE_VERSIONS.PACKAGE_ID.eq(Packages.PACKAGES.ID))
                .leftJoin(Dependencies.DEPENDENCIES)
                .on(Dependencies.DEPENDENCIES.PACKAGE_VERSION_ID.eq(PackageVersions.PACKAGE_VERSIONS.ID))
                .where(Packages.PACKAGES.FORGE.eq(Constants.mvnForge))
                .and(PackageVersions.PACKAGE_VERSIONS.CREATED_AT.isNotNull())
//                .limit(10000)
                .fetch()
                .stream()
//                .parallel()
                .map(x -> {
                    if (x.component1() == null) {
                        throw new NullPointerException("package.package_name is null for " + x);
                    }
                    if (x.component2() == null) {
                        throw new NullPointerException("package_version.version is null for " + x);
                    }
                    if (x.component3() == null) {
                        throw new NullPointerException("dependency.metadata is null for " + x);
                    }
                    if (x.component4() == null) {
                        throw new NullPointerException("package_version.created_at is null for " + x);
                    }
                    var artifact = x.component1().split(":")[0];
                    var group = x.component1().split(":")[1];
                    return new AbstractMap.SimpleEntry<>(new Revision(artifact, group, x.component2(), x.component4()),
                            Dependency.fromJSON(new JSONObject(x.component3().data())));
                }).collect(Collectors.toConcurrentMap(
                        AbstractMap.SimpleEntry::getKey,
                        x -> List.of(x.getValue()),
                        (x, y) -> {
                            var z = new ArrayList<Dependency>();
                            z.addAll(x);
                            z.addAll(y);
                            return z;
                        }
                ));
    }

    public List<Revision> findMatchingRevisions(List<Revision> revisions,
                                                       List<Dependency.VersionConstraint> constraints) {
        return revisions.parallelStream().filter(r -> {
            for (var constraint : constraints) {
                if (checkVersionLowerBound(constraint, r.version) && checkVersionUpperBound(constraint, r.version)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    private static boolean checkVersionLowerBound(Dependency.VersionConstraint constraint, DefaultArtifactVersion version) {
        if (constraint.isLowerHardRequirement) {
            return version.compareTo(new DefaultArtifactVersion(constraint.lowerBound)) >= 0;
        } else {
            return version.compareTo(new DefaultArtifactVersion(constraint.lowerBound)) > 0;
        }
    }

    private static boolean checkVersionUpperBound(Dependency.VersionConstraint constraint, DefaultArtifactVersion version) {
        if (constraint.isUpperHardRequirement) {
            return version.compareTo(new DefaultArtifactVersion(constraint.upperBound)) <= 0;
        } else {
            return version.compareTo(new DefaultArtifactVersion(constraint.upperBound)) < 0;
        }
    }


    public Graph<Revision, DependencyEdge> buildDependencyGraph(DSLContext dbContext) {
        var dependencies = getDependencyList(dbContext);

        var productRevisionMap = new HashMap<String, List<Revision>>();
        for (var revision : dependencies.keySet()) {
            var product = revision.groupId + ":" + revision.artifactId;
            if (productRevisionMap.containsKey(product)) {
                var revisions = productRevisionMap.get(product);
                productRevisionMap.remove(product);
                revisions.add(revision);
                productRevisionMap.put(product, revisions);
            } else {
                productRevisionMap.put(product, List.of(revision));
            }
        }

        long startTs;

        startTs = System.currentTimeMillis();
        logger.info("Indexing dependency pairs");

        var dependencyGraph = new DefaultDirectedGraph<Revision, DependencyEdge>(DependencyEdge.class);

        long idx = 0;
        for (var entry : dependencies.entrySet()) {
            var source = entry.getKey();
            for (var dependency : entry.getValue()) {
                var potentialRevisions = productRevisionMap.get(dependency.groupId + ":" + dependency.artifactId);
                var matchingRevisions = findMatchingRevisions(potentialRevisions, dependency.versionConstraints);
                for (var target : matchingRevisions) {
                    var edge = new DependencyEdge(idx++, dependency.scope, dependency.optional, dependency.exclusions);
                    dependencyGraph.addEdge(source, target, edge);
                }
            }
        }
        logger.info(String.format("Created graph: %d ms", System.currentTimeMillis() - startTs));
        logger.info("Successfully generated ecosystem-wide dependency graph");
        return dependencyGraph;
    }
}