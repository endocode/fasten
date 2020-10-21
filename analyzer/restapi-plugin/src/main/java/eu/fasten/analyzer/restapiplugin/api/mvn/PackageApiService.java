package eu.fasten.analyzer.restapiplugin.api.mvn;

import eu.fasten.core.data.metadatadb.MetadataDao;

import javax.ws.rs.core.Response;

public interface PackageApiService {

    Response getPackageVersions(String package_name,
                                short offset,
                                short limit,
                                MetadataDao metadataDao);

    Response getPackageVersion(String package_name,
                               String package_version,
                               short offset,
                               short limit,
                               MetadataDao metadataDao);

    Response getPackageMetadata(String package_name,
                                String package_version,
                                short offset,
                                short limit,
                                MetadataDao metadataDao);

    Response getPackageCallgraph(String package_name,
                                 String package_version,
                                 short offset,
                                 short limit,
                                 MetadataDao metadataDao);
}
