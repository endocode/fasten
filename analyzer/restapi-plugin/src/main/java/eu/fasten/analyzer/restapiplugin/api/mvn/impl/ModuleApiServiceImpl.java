package eu.fasten.analyzer.restapiplugin.api.mvn.impl;

import eu.fasten.analyzer.restapiplugin.RestAPIPlugin;
import eu.fasten.analyzer.restapiplugin.api.mvn.ModuleApiService;

import javax.ws.rs.core.Response;

public class ModuleApiServiceImpl implements ModuleApiService {

    @Override
    public Response getPackageModules(String package_name, String package_version) {
        String result = RestAPIPlugin.RestAPIExtension.kbDao.getPackageModules(package_name, package_version);
        return Response.status(200).entity(result).build();
    }

    @Override
    public Response getModuleMetadata(String package_name, String package_version, String module_namespace) {
        String result = RestAPIPlugin.RestAPIExtension.kbDao.getModuleMetadata(package_name, package_version, module_namespace);
        return Response.status(200).entity(result).build();
    }

    @Override
    public Response getModuleFiles(String package_name, String package_version, String module_namespace) {
        String result = RestAPIPlugin.RestAPIExtension.kbDao.getModuleFiles(package_name, package_version, module_namespace);
        return Response.status(200).entity(result).build();
    }
}