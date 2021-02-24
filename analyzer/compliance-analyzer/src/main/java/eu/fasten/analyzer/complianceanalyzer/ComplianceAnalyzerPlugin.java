package eu.fasten.analyzer.complianceanalyzer;

import eu.fasten.core.plugins.KafkaPlugin;
import org.json.JSONObject;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Plugin which runs qmstr command line tool to detect
 * license compatibility and compliance.
 */
public class ComplianceAnalyzerPlugin extends Plugin {

    /**
     * Name of the environment variable containing the cluster credentials file path.
     */
    protected static final String CLUSTER_CREDENTIALS_ENV = "clusterCredentials";

    public ComplianceAnalyzerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class CompliancePluginExtension implements KafkaPlugin {

        private final Logger logger = LoggerFactory.getLogger(CompliancePluginExtension.class.getName());

        protected String consumerTopic = "fasten.RepoCloner.out";
        protected Throwable pluginError = null;
        protected String repoUrl;

        @Override
        public Optional<List<String>> consumeTopic() {
            return Optional.of(Collections.singletonList(consumerTopic));
        }

        @Override
        public void setTopic(String topicName) {
            this.consumerTopic = topicName;
        }

        @Override
        public void consume(String record) {
            try { // Fasten error-handling guidelines

                this.pluginError = null;
                var consumedJsonPayload = new JSONObject(record);
                if (consumedJsonPayload.has("payload")) {
                    consumedJsonPayload = consumedJsonPayload.getJSONObject("payload");
                }
                this.repoUrl = consumedJsonPayload.getString("repoUrl");
                String repoPath = consumedJsonPayload.getString("repoPath");
                String artifactID = consumedJsonPayload.getString("artifactId");

                logger.info("Repo url: " + repoUrl);
                logger.info("Path to the cloned repo: " + repoPath);
                logger.info("Artifact id: " + artifactID);

                if (repoUrl == null) {
                    IllegalArgumentException missingRepoUrlException =
                            new IllegalArgumentException("Invalid repository information: missing repository URL.");
                    setPluginError(missingRepoUrlException);
                    throw missingRepoUrlException;
                }

                // Retrieving the Docker-compose file
                File dockerComposeFile =
                        new File(ComplianceAnalyzerPlugin.class.getResource("/docker-compose.yml").getPath());

                // Launching Quartermaster
                try (DockerComposeContainer environment =
                             new DockerComposeContainer(dockerComposeFile)
                                     .withEnv("REPOSITORY_URL", repoUrl)
                                     .withExposedService("alpha", 8080, Wait.forHttp("/health")) // DGraph
                ) {

                    environment.start();

                    // TODO Introduce waiting instruction(s)
                    // environment.wait(10000L);
                }

            } catch (Exception e) { // Fasten error-handling guidelines
                logger.error(e.getMessage());
                setPluginError(e);
            }

        }

        @Override
        public Optional<String> produce() {
            return Optional.empty();
        }

        @Override
        public String getOutputPath() {
            // TODO
            throw new UnsupportedOperationException(
                    "Output path will become available as soon as the QMSTR reporting phase will be released."
            );
        }

        @Override
        public String name() {
            return "License Compliance Plugin";
        }

        @Override
        public String description() {
            return "License Compliance Plugin."
                    + "Consumes Repository Urls from Kafka topic,"
                    + " connects to cluster and starts a Kubernetes Job."
                    + " The Job spins a qmstr process which detects the project's"
                    + " license compliance and compatibility."
                    + " Once the Job is done the output is written to another Kafka topic.";
        }

        @Override
        public String version() {
            return "0.0.1";
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }

        @Override
        public Throwable getPluginError() {
            return this.pluginError;
        }

        public void setPluginError(Throwable throwable) {
            this.pluginError = throwable;
        }

        @Override
        public void freeResource() {
        }
    }
}
