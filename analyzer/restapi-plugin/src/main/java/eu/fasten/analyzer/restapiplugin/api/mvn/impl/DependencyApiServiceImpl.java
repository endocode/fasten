package eu.fasten.analyzer.restapiplugin.api.mvn.impl;

import eu.fasten.analyzer.restapiplugin.RestAPIPlugin;
import eu.fasten.analyzer.restapiplugin.api.mvn.DependencyApiService;

import javax.ws.rs.core.Response;

public class DependencyApiServiceImpl implements DependencyApiService {

    @Override
    public Response getPackageDependencies(String package_name, String package_version) {
        String result = RestAPIPlugin.RestAPIExtension.kbDao.getPackageDependencies(package_name, package_version);
        return Response.status(200).entity(result).build();
    }
}