package eu.fasten.server.utils;

import it.unimi.dsi.fastutil.Hash;
import org.apache.commons.lang.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


/**
 * A simple crawler to fetch metadata of projects from Maven repositories.
 * It generates a CSV file with following fields:
 * - Project name
 * - Group ID
 * - Artifact ID
 * - Version
 * - Timestamp
 */


public class MVNCrawler {

    private String csvFileName;
    public String mvnRepo = "http://repo2.maven.apache.org/maven2/";

    static class POMFile{
        /**
         * It represents a POM file with its timestamp
         */
        public String fileName;
        public String date;
        public String time;

        POMFile(final String fileName, final String date, final String time){
            this.fileName = fileName;
            this.date = date;
            this.time = time;
        }
    }

    static class MVNProject{
        /**
         * It encapsulates a project on the Maven repository.
         */

        private String projectName;
        private String groupID;
        private String artifactID;
        private String version;
        private String timestamp;

        public MVNProject(String projectName, String groupID, String artifactID, String version, String timestamp) {
            this.projectName = projectName;
            this.groupID = groupID;
            this.artifactID = artifactID;
            this.version = version;
            this.timestamp = timestamp;
        }

        public String getProjectName() {
            return projectName;
        }

        public String getGroupID() {
            return groupID;
        }

        public String getArtifactID() {
            return artifactID;
        }

        public String getVersion() {
            return version;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public void setArtifactID(String artifactID) {
            this.artifactID = artifactID;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public MVNCrawler(final String csvFileName){
        this.csvFileName = csvFileName;
    }

    public void startCrawler() throws IOException {
        this.ExtractMVNProjects(this.GetPOMFiles());
    }

    // TODO: It doesn't get all the POM Files, there are rare cases... hierarchy of files might be deeper!
    private HashMap<String, HashMap<String, HashMap<String, POMFile>>> GetPOMFiles() throws IOException {

        //Runtime rt = Runtime.getRuntime();

//        String[] args = new String[] {"/bin/bash", "-c", "wget -q -O - -p --user-agent=\"Mozilla/5.0 " +
////                "(Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36\" " +
////                "http://repo2.maven.apache.org/maven2/" };
////        Process proc = new ProcessBuilder(args).start();
////
////        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getInputStream()));
////
////        String s = null;
////        while ((s = stdError.readLine()) != null){
////            System.out.println(s);
////        }

        Document doc = Jsoup.connect(mvnRepo).get();
        Elements links = doc.getElementsByTag("a");

        HashMap<String, HashMap<String, HashMap<String, POMFile>>> authorRepos = new HashMap<>();

        // Regex patterns for extracting only repositories and their versions
        String namePattern = "\\w+/";
        String verPattern = ".+\\.\\w+";
        String pomFilePattern = ".+\\.pom";

        int i = 0;

        // Iterates through organizations or users
        for(Element author : links){
            String authorName = author.text();
            authorRepos.put(authorName, new HashMap<String, HashMap<String, POMFile>>());

            System.out.println("Author's" + authorName + "Repos:");
            if(authorName.matches(namePattern)){
                //System.out.println(repoName.substring(0, repoName.length() - 1));
                Document authorPage = Jsoup.connect(mvnRepo + authorName).get();
                Elements authorLinks = authorPage.getElementsByTag("a");

                // Iterates through repositories
                for(Element repo: authorLinks){
                    String repoName = repo.text();
                    authorRepos.get(authorName).put(repoName, new HashMap<String, POMFile>());

                    // "(?!\\.\\.)" pattern for excluding up level dirs (e.g. ../)
                    if(repoName.matches("(?!\\.\\.)" + namePattern)){
                        System.out.println(repoName);

                        Document repoPage = Jsoup.connect(mvnRepo + authorName + repoName).get();
                        Elements repoLinks = repoPage.getElementsByTag("a");

                        // Iterates through releases
                        for(Element ver: repoLinks){
                            String verNumber = ver.text();
                            //System.out.println(verNumber);

                            if(verNumber.matches("(?!\\.\\.).*") && !verNumber.matches(verPattern)){
                                System.out.println(verNumber);
                                System.out.println(mvnRepo + authorName + "/" + repoName + "/" + verNumber + "/");

                                Document verPage = Jsoup.connect(mvnRepo + authorName + repoName + verNumber).get();
                                Element content = verPage.getElementById("contents");

                                // Ignores releases without any files
                                if(content != null){
                                    Elements verLinks = verPage.getElementsByTag("a");
                                    //System.out.println(content.ownText());
                                    String timeStampList[] = content.ownText().split("\\r?\\n");

                                    int j = 0;
                                    // Iterates through files in a release
                                    for(Element file : verLinks){
                                        String fileName = file.text();
                                        if(fileName.matches(pomFilePattern)){
                                            // For separating timestamp and file size
                                            String[] timeStampSplit = timeStampList[j].trim().split("\\s+");
                                            authorRepos.get(authorName).get(repoName).put(verNumber, new POMFile(fileName, timeStampList[0], timeStampList[1]));
                                            break;
                                        }

                                        j++;
                                    }
                                }
                            }
                        }
                        }
                    }
                }

                System.out.println("--------------------------------");
                i++;
                if(i == 4) break;
            }
        return authorRepos;
        }

    public void ExtractMVNProjects(HashMap<String, HashMap<String, HashMap<String, POMFile>>> POMFiles) throws IOException {

        for(String user : POMFiles.keySet()){
            //System.out.println(user);
            for(String repo : POMFiles.get(user).keySet()) {
                //System.out.println(repo);

                for(HashMap.Entry<String, POMFile> verAndPOM : POMFiles.get(user).get(repo).entrySet()){
                    System.out.println(verAndPOM.getKey() + verAndPOM.getValue().fileName);
                    Document XMLDoc = Jsoup.parse(new URL(mvnRepo + user + repo + verAndPOM.getKey() + verAndPOM.getValue().fileName).openStream(),
                            "UTF-8", "", Parser.xmlParser());

                    for(Element e: XMLDoc.getElementsByTag("project"))
                    {
                        System.out.println(e.getElementsByTag("groupId").text());
                        System.out.println(e.getElementsByTag("artifactId").text());
                    }

                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MVNCrawler crawler =  new MVNCrawler("mvn_repo.csv");
        crawler.startCrawler();
    }



}