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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PackageApiServiceImplUTest {

    @InjectMocks
    private final PackageApiServiceImpl service = new PackageApiServiceImpl();

    @Mock
    MetadataDao mdDao;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallDaoGetPkgVersionsMethod() {
        String pkgName = "au.org";
        short offset = 1;
        short limit = 5;
        // TODO: update the result set to a correct one
        String resultSet = "{\"fields\":[{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"package_version_id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"name\",\"type\":\"CLOB\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"created_at\",\"type\":\"TIMESTAMP\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"metadata\",\"type\":\"JSONB\"}],\"records\":[]}\n";
        Response response = Response.status(200).entity(resultSet).build();

        when(mdDao.getPackageVersions(pkgName, offset, limit)).thenReturn(resultSet);

        var servResp = service.getPackageVersions(pkgName, offset, limit, mdDao);

//        assertEquals(response, servResp);
        // FIXME: this might not work. Needs to find a way to assert the entity content and not the response object

        verify(mdDao, times(1)).getPackageVersions(pkgName, offset, limit);
    }

    @Test
    public void shouldCallDaoGetPkgVersionMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        short offset = 1;
        short limit = 5;
        // TODO: update the result set to a correct one
        String resultSet = "{\"fields\":[{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"package_version_id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"name\",\"type\":\"CLOB\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"created_at\",\"type\":\"TIMESTAMP\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"metadata\",\"type\":\"JSONB\"}],\"records\":[]}\n";
        Response response = Response.status(200).entity(resultSet).build();

        when(mdDao.getPackageVersion(pkgName, pkgVersion, offset, limit)).thenReturn(resultSet);

        var servResp = service.getPackageVersion(pkgName, pkgVersion, offset, limit, mdDao);

//        assertEquals(response, servResp);
        // FIXME: this might not work. Needs to find a way to assert the entity content and not the response object

        verify(mdDao, times(1)).getPackageVersion(pkgName, pkgVersion, offset, limit);
    }

    @Test
    public void shouldCallDaoGetPkgMetadataMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        short offset = 1;
        short limit = 5;
        String resultSet = "{\"fields\":[{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"package_version_id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"name\",\"type\":\"CLOB\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"created_at\",\"type\":\"TIMESTAMP\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"metadata\",\"type\":\"JSONB\"}],\"records\":[]}\n";
        Response response = Response.status(200).entity(resultSet).build();

        when(mdDao.getPackageMetadata(pkgName, pkgVersion, offset, limit)).thenReturn(resultSet);

        var servResp = service.getPackageMetadata(pkgName, pkgVersion, offset, limit, mdDao);

//        assertEquals(response, servResp);
        // FIXME: this might not work. Needs to find a way to assert the entity content and not the response object

        verify(mdDao, times(1)).getPackageMetadata(pkgName, pkgVersion, offset, limit);
    }

    @Test
    public void shouldCallDaoGetPkgCallgraphMethod() {
        String pkgName = "au.org";
        String pkgVersion = "1.1.1";
        short offset = 1;
        short limit = 5;
        String resultSet = "{\"fields\":[{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"package_version_id\",\"type\":\"BIGINT\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"name\",\"type\":\"CLOB\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"created_at\",\"type\":\"TIMESTAMP\"},{\"schema\":\"public\",\"table\":\"binary_modules\",\"name\":\"metadata\",\"type\":\"JSONB\"}],\"records\":[]}\n";
        Response response = Response.status(200).entity(resultSet).build();

        when(mdDao.getPackageCallgraph(pkgName, pkgVersion, offset, limit)).thenReturn(resultSet);

        var servResp = service.getPackageCallgraph(pkgName, pkgVersion, offset, limit, mdDao);

//        assertEquals(response, servResp);
        // FIXME: this might not work. Needs to find a way to assert the entity content and not the response object

        verify(mdDao, times(1)).getPackageCallgraph(pkgName, pkgVersion, offset, limit);
    }

}
