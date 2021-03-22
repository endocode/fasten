package eu.fasten.analyzer.complianceanalyzer;

import eu.fasten.core.plugins.KafkaPlugin;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


/**
 * Plugin which runs qmstr command line tool to detect
 * license compatibility and compliance.
 */
public class ComplianceAnalyzerPlugin extends Plugin {

    public ComplianceAnalyzerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class CompliancePluginExtension implements KafkaPlugin {

        private final Logger logger = LoggerFactory.getLogger(CompliancePluginExtension.class.getName());

        protected String consumerTopic = "fasten.RepoCloner.out";
        protected Throwable pluginError = null;
        protected String repoUrl;

        private static class StreamGobbler implements Runnable {
            private final InputStream inputStream;
            private final Consumer<String> consumer;

            public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
                this.inputStream = inputStream;
                this.consumer = consumer;
            }

            @Override
            public void run() {
                new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
            }
        }

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
                try (InputStream in = getClass().getResourceAsStream("/docker-compose.yml")) {

                    // Converting it into a File (`InputStream` needed by JAR)
                    File f = new File("docker-compose.yml");
                    FileUtils.copyInputStreamToFile(in, f);

                    // Running Quartermaster
                    Process qmstrProc = Runtime.getRuntime().exec(
                            new String[]{
                                    "sh",
                                    "-c",

                                    // Runs Quartermaster locally
                                    "docker-compose " +
                                            "--file " + f.getAbsolutePath() + " " +
                                            "up " +
                                            "--remove-orphans " +
                                            "--renew-anon-volumes "
                            },

                            // setting the repo URL as an ENV variable
                            new String[]{"REPOSITORY_URL=" + repoUrl}
                    );
                    StreamGobbler qmstrStreamGobbler = new StreamGobbler(qmstrProc.getInputStream(), logger::info);
                    Executors.newSingleThreadExecutor().submit(qmstrStreamGobbler);

                    // Waiting for the Quartermaster's client exit code
                    Process clientExitCodeWaiterProc = Runtime.getRuntime().exec(
                            new String[]{"sh", "-c", "docker wait client"}
                    );
                    StreamGobbler clientExitCodeWaiterStreamGobbler = new StreamGobbler(
                            clientExitCodeWaiterProc.getInputStream(), logger::info
                    );
                    Executors.newSingleThreadExecutor().submit(clientExitCodeWaiterStreamGobbler);
                    clientExitCodeWaiterProc.waitFor();
                    // TODO Inspect Quartermaster's client exit code

                    // Cleaning resources
                    Process cleaningProc = Runtime.getRuntime().exec(
                            new String[]{
                                    "sh",
                                    "-c",

                                    "docker-compose " +
                                            "--file " + f.getAbsolutePath() + " " +
                                            "down " +
                                            "--volumes " +
                                            "--remove-orphans"
                            }
                    );
                    StreamGobbler cleaningStreamGobbler = new StreamGobbler(cleaningProc.getInputStream(), logger::info);
                    Executors.newSingleThreadExecutor().submit(cleaningStreamGobbler);
                    int cleaningExitCode = cleaningProc.waitFor();
                    assert cleaningExitCode == 0;
                    // TODO Handle cleaning failure
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
