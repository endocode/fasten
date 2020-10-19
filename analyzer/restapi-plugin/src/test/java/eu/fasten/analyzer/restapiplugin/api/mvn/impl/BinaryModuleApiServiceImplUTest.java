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

import eu.fasten.core.data.metadatadb.MetadataDao;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;


public class BinaryModuleApiServiceImplUTest {

    @Mock
    Response response;

    @Mock
    MetadataDao kbDao;

    @InjectMocks
    private final BinaryModuleApiServiceImpl service = new BinaryModuleApiServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallDaoGetPkgBinaryModMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        service.getPackageBinaryModules(pkgName, pkgVersion);
        verify(kbDao, times(1)).getPackageBinaryModules(pkgName, pkgVersion);
    }

    @Test
    public void shouldReturnBinaryMod_whenGetPkgBinaryMod() {
        // getPackageBinaryModules: should return 200 and the result of the string
        // if pkg binary modules are found
    }

    @Test
    public void shouldReturnErrorMsg_whenPkgBinaryModNotFound() {
        // getPackageBinaryModules: should return 404
        // if pkg binary modules are not found
    }

    @Test
    public void shouldReturnMetadata_whenGetBinaryModMetadata() {
        // getBinaryModuleMetadata: should return 200 and the result of the string
        // if pkg binary modules metadata are found
    }

    @Test
    public void shouldReturnErrorMsg_whenGetBinaryModMetadataNotFound() {
        // getBinaryModuleMetadata: should return 404
        // if pkg binary modules are not found
    }

    @Test
    public void shouldReturnFilesInfo_whenGetBinaryModFiles() {
        // getBinaryModuleFiles: should return 200 and the result of the string
        // if pkg binary modules are found
    }

    @Test
    public void shouldReturnErrorMsg_whenGetBinaryModFilesNotFound() {
        // getBinaryModuleFiles: should return 404
        // if pkg binary modules are not found
    }

}
