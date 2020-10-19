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
import static org.mockito.Mockito.*;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.ws.rs.core.Response;


public class BinaryModuleApiServiceImplUTest {

    @Mock
    DSLContext dslContext;

    @InjectMocks
    private final BinaryModuleApiServiceImpl service = new BinaryModuleApiServiceImpl();
    private final RestAPIPlugin.RestAPIExtension restExt = new RestAPIPlugin.RestAPIExtension(9090, "url", "user");

    public final MetadataDao kbDao = RestAPIPlugin.RestAPIExtension.kbDao;

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        kbDao.setContext(dslContext);
    }

    @Test
    public void shouldCallDaoGetPkgBinaryModMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        service.getPackageBinaryModules(pkgName, pkgVersion);
        verify(kbDao, times(1)).getPackageBinaryModules(pkgName, pkgVersion);
    }

    @Test
    public void shouldCallDaoGetBinaryModMetadataMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        String binaryMod = "xx";
        service.getBinaryModuleMetadata(pkgName, pkgVersion, binaryMod);
        verify(kbDao, times(1)).getBinaryModuleMetadata(pkgName, pkgVersion, binaryMod);
    }

    @Test
    public void shouldCallDaoGetBinaryModFilesMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        String binaryMod = "xx";

        when(service.getBinaryModuleFiles(pkgName, pkgVersion, binaryMod)).thenReturn(response);

        verify(kbDao, times(1)).getBinaryModuleFiles(pkgName, pkgVersion, binaryMod);
    }

}
