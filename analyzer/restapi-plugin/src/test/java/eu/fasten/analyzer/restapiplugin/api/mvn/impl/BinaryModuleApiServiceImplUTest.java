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
package eu.fasten.analyzer.restapiplugin.api.mvn.impl;

import eu.fasten.analyzer.restapiplugin.RestAPIPlugin;
import eu.fasten.core.data.metadatadb.MetadataDao;

import eu.fasten.core.data.metadatadb.codegen.tables.BinaryModules;
import eu.fasten.core.data.metadatadb.codegen.tables.PackageVersions;
import eu.fasten.core.data.metadatadb.codegen.tables.Packages;
import org.jooq.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

public class BinaryModuleApiServiceImplUTest {

//     @Mock
//     DSLContext dslContext;

//     @Mock
//     MetadataDao kbDao;

//     @InjectMocks
//     private final BinaryModuleApiServiceImpl service = new BinaryModuleApiServiceImpl();
// //    private final RestAPIPlugin.RestAPIExtension restExt = new RestAPIPlugin.RestAPIExtension();

//     @BeforeEach
//     public void setUp() throws Exception {
//         MockitoAnnotations.initMocks(this);
//         var restExt = new RestAPIPlugin.RestAPIExtension();
//         restExt.setDBConnection(dslContext);
//     }

//     @Test
//     public void shouldCallDaoGetPkgBinaryModMethod() {
//         String pkgName = "au.org";
//         String pkgVersion = "1.1.1";
//         short offset = 1;
//         short limit = 5;
//         String resultSet = "{\"fields\":[{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"package_version_id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"name\",\"type\":\"CLOB\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"created_at\",\"type\":\"TIMESTAMP\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"metadata\",\"type\":\"JSONB\"}],\"records\":[]}\n";
//         Response response = Response.status(200).entity(resultSet).build();
//         when(service.getPackageBinaryModules(pkgName, pkgVersion, offset, limit)).thenReturn(response);
//         verify(kbDao, times(1)).getPackageBinaryModules(pkgName, pkgVersion, offset, limit);
//     }

//     @Test
//     public void shouldCallDaoGetBinaryModMetadataMethod() {
//         String pkgName = "au.org";
//         String pkgVersion = "1.1.1";
//         String binaryMod = "xx";
//         short offset = 1;
//         short limit = 5;
//         String resultSet = "{\"fields\":[{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"package_version_id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"name\",\"type\":\"CLOB\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"created_at\",\"type\":\"TIMESTAMP\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"metadata\",\"type\":\"JSONB\"}],\"records\":[]}\n";
//         Response response = Response.status(200).entity(resultSet).build();
//         when(service.getBinaryModuleMetadata(pkgName, pkgVersion, binaryMod, offset, limit)).thenReturn(response);
//         verify(kbDao, times(1)).getBinaryModuleMetadata(pkgName, pkgVersion, binaryMod, offset, limit);
//     }

//    @Test
//    public void shouldCallDaoGetBinaryModFilesMethod() {
//        String pkgName = "au.org";
//        String pkgVersion = "1.1.1";
//        String binaryMod = "xx";
//
//        when(service.getBinaryModuleFiles(pkgName, pkgVersion, binaryMod)).thenReturn(response);
//
//        verify(restExt.kbDao, times(1)).getBinaryModuleFiles(pkgName, pkgVersion, binaryMod);
//    }

}
