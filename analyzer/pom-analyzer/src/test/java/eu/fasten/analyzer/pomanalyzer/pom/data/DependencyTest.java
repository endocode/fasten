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

package eu.fasten.analyzer.pomanalyzer.pom.data;

import eu.fasten.core.maven.data.Dependency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class DependencyTest {

    @Test
    public void dependencyTest() {
        var expected = new Dependency("junit", "junit", "4.11");
        var json = expected.toJSON();
        var actual = Dependency.fromJSON(json);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void exclusionTest() {
        var expected = new Dependency.Exclusion("junit", "junit");
        var json = expected.toJSON();
        var actual = Dependency.Exclusion.fromJSON(json);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void versionConstraintTest() {
        var expected = new Dependency.VersionConstraint("1.0", false, "1.0", false);
        var json = expected.toJSON();
        var actual = Dependency.VersionConstraint.fromJSON(json);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void versionConstraintsParsingTest1() {
        var spec = "1.0";
        var expected = List.of(
                new Dependency.VersionConstraint("1.0", false, "1.0", false)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(1, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, dependency.getVersionConstraints()[0]);
    }

    @Test
    public void versionConstraintsParsingTest2() {
        var spec = "[1.0]";
        var expected = List.of(
                new Dependency.VersionConstraint("1.0", true, "1.0", true)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(1, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, dependency.getVersionConstraints()[0]);
    }

    @Test
    public void versionConstraintsParsingTest3() {
        var spec = "(,1.0]";
        var expected = List.of(
                new Dependency.VersionConstraint("", false, "1.0", true)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(1, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, dependency.getVersionConstraints()[0]);
    }

    @Test
    public void versionConstraintsParsingTest4() {
        var spec = "[1.2,1.3]";
        var expected = List.of(
                new Dependency.VersionConstraint("1.2", true, "1.3", true)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(1, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, dependency.getVersionConstraints()[0]);
    }

    @Test
    public void versionConstraintsParsingTest5() {
        var spec = "[1.0,2.0)";
        var expected = List.of(
                new Dependency.VersionConstraint("1.0", true, "2.0", false)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(1, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, dependency.getVersionConstraints()[0]);
    }

    @Test
    public void versionConstraintsParsingTest6() {
        var spec = "[1.5,)";
        var expected = List.of(
                new Dependency.VersionConstraint("1.5", true, "", false)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(1, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, dependency.getVersionConstraints()[0]);
    }

    @Test
    public void versionConstraintsParsingTest7() {
        var spec = "(,1.0],[1.2,)";
        var expected = List.of(
                new Dependency.VersionConstraint("", false, "1.0", true),
                new Dependency.VersionConstraint("1.2", true, "", false)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(2, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, String.join(",", dependency.getVersionConstraints()));
    }

    @Test
    public void versionConstraintsParsingTest8() {
        var spec = "(,1.1),(1.1,)";
        var expected = List.of(
                new Dependency.VersionConstraint("", false, "1.1", false),
                new Dependency.VersionConstraint("1.1", false, "", false)
        );
        Assertions.assertEquals(expected, Dependency.VersionConstraint.resolveMultipleVersionConstraints(spec));
        var dependency = new Dependency("test", "test", spec);
        Assertions.assertEquals(2, dependency.getVersionConstraints().length);
        Assertions.assertEquals(spec, String.join(",", dependency.getVersionConstraints()));
    }
}
