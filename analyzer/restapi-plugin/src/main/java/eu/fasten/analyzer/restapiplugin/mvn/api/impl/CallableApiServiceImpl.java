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

package eu.fasten.analyzer.restapiplugin.mvn.api.impl;

import eu.fasten.analyzer.restapiplugin.KnowledgeBaseConnector;
import eu.fasten.analyzer.restapiplugin.LazyIngestionProvider;
import eu.fasten.analyzer.restapiplugin.mvn.api.CallableApiService;
import eu.fasten.core.maven.data.PackageVersionNotFoundException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CallableApiServiceImpl implements CallableApiService {

    @Override
    public ResponseEntity<String> getPackageCallables(String package_name,
                                                      String package_version,
                                                      int offset,
                                                      int limit,
                                                      String artifactRepo,
                                                      Long date) {
        String result;
        try {
            result = KnowledgeBaseConnector.kbDao.getPackageCallables(
                    package_name, package_version, offset, limit);
        } catch (PackageVersionNotFoundException e) {
            try {
                LazyIngestionProvider.ingestArtifactIfNecessary(package_name, package_version, artifactRepo, date);
            } catch (IllegalArgumentException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Package version not found, but should be processed soon. Try again later", HttpStatus.CREATED);
        }
        result = result.replace("\\/", "/");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getCallableMetadata(String package_name,
                                                      String package_version,
                                                      String fasten_uri,
                                                      String artifactRepo,
                                                      Long date) {
        String result = KnowledgeBaseConnector.kbDao.getCallableMetadata(
                package_name, package_version, fasten_uri);
        if (result == null) {
            try {
                LazyIngestionProvider.ingestArtifactIfNecessary(package_name, package_version, artifactRepo, date);
            } catch (IllegalArgumentException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Package version not found, but should be processed soon. Try again later", HttpStatus.CREATED);
        }
        result = result.replace("\\/", "/");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getCallables(List<Long> callableIds) {
        var callablesMap = KnowledgeBaseConnector.kbDao.getCallables(callableIds);
        var json = new JSONObject();
        for (var id : callableIds) {
            json.put(String.valueOf(id), callablesMap.get(id));
        }
        var result = json.toString();
        result = result.replace("\\/", "/");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
