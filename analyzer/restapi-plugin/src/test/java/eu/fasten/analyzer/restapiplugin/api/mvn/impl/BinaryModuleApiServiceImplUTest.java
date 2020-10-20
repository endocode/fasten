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

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BinaryModuleApiServiceImplUTest {

    @Mock
    MetadataDao kbDao;

    @InjectMocks
    private final BinaryModuleApiServiceImpl service = new BinaryModuleApiServiceImpl();
//    private final RestAPIPlugin.RestAPIExtension restExt = new RestAPIPlugin.RestAPIExtension();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
//        restExt.setDBConnection(dslContext);
        var dslContext = Mockito.mock(DSLContext.class);
        var restExt = new RestAPIPlugin.RestAPIExtension();
        restExt.setDBConnection(dslContext);
    }

    @Test
    public void shouldCallDaoGetPkgBinaryModMethod() {
//        var kbDao = Mockito.mock(MetadataDao.class);
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        short offset = 1;
        short limit = 5;
        service.getPackageBinaryModules(pkgName, pkgVersion, offset, limit);
        verify(kbDao, times(1)).getPackageBinaryModules(pkgName, pkgVersion, offset, limit);
    }

    @Test
    public void shouldCallDaoGetBinaryModMetadataMethod() {
//        var kbDao = Mockito.mock(MetadataDao.class);
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        String binaryMod = "xx";
        short offset = 1;
        short limit = 5;
        service.getBinaryModuleMetadata(pkgName, pkgVersion, binaryMod, offset, limit);
        verify(kbDao, times(1)).getBinaryModuleMetadata(pkgName, pkgVersion, binaryMod, offset, limit);
    }

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
