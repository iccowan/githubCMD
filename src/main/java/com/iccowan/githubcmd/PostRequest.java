// Post Request Package
package com.iccowan.githubcmd;

// Import packages
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.io.*;
import java.net.*;

public class PostRequest {
    // Variables
    String token;
    String sshURL;
    String httpURL;

    // Gets the GitHub Private token from the config
    // Inputs:
    //  NONE
    // Outputs:
    //  String token
    private static String getGitHubPrivate() {
        // Set the token as empty string
        String token = null;

        try {
            // Open the config file
            File file = new File("config.properties");
            InputStream config = new FileInputStream(file);

            // Get the token property
            Properties prop = new Properties();
            prop.load(config);
            token = prop.getProperty("github_api_key", null);
            token = token.trim();
        } catch(IOException e) {
            //
        }

        if(token == null) {
            try {
                // If the config doesn't exist, create it
                File file = new File("config.properties");
                file.createNewFile();
                OutputStream configOut = new FileOutputStream(file);
                Properties prop = new Properties();

                prop.setProperty("github_api_key", "");
                prop.store(configOut, "GitHub API Developer Token (Personal Token)");
            } catch(IOException e) {
                System.out.println("No config file found and unable to create the config file.");
            }
        }

        return token;
    }

    // Construct
    public PostRequest(String name, String desc, String web, String priv) {
        // Get the GitHub Private token from config
        token = getGitHubPrivate();

        if(token == null) {
            System.out.println("Config file created.");
        } else {
            // If there was a config file, continue with the POST request to GitHub
            try {
                URL url = new URL("https://api.github.com/user/repos");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", "token " + token);

                // Set the data
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> map = new HashMap();
                map.put("name", name);
                map.put("description", desc);
                map.put("homepage", web);
                map.put("private", priv);

                // Convert map to JSON and make request
                // Take the request and map it
                try {
                    String data = mapper.writeValueAsString(map);

                    OutputStream os = connection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();

                    if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + connection.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (connection.getInputStream())));

                    Map<String, String> responseMap = mapper.readValue(br, Map.class);

                    // Set the correct values
                    sshURL = responseMap.get("ssh_url");
                    httpURL = responseMap.get("html_url");
                } catch(JsonProcessingException e) {
                    e.printStackTrace();
                }

                connection.disconnect();

            } catch(MalformedURLException e) {
                //
            } catch(IOException e) {
                //
            }
        }
    }

    // Get the SSH url
    public String getSSHurl() {
        return this.sshURL;
    }

    // Get the HTTP url
    public String getHTTPurl() {
        return this.httpURL;
    }
}