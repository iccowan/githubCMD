// Open the user's repository information
package com.iccowan.githubcmd;

// Import packages
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.io.*;
import java.net.*;

public class Repository {
    //Attributes
    private Map<String, String> info;

    //Construct
    public Repository(Config configFile) {
        // Set the token for the repository
        String token = configFile.getToken();
        String sshURL = "";
        String httpURL = "";

        // Look up the user's repositories
        try {
            URL url = new URL("https://api.github.com/user/repos?affiliation=owner");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "token " + token);

            // Convert map to JSON and make request
            // Take the request and map it
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();

                ObjectMapper mapper = new ObjectMapper();
                OutputStream os = connection.getOutputStream();
                os.flush();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + connection.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (connection.getInputStream())));

                Map<String, String> responseMap = mapper.readValue(br, Map.class);
                System.out.println(responseMap);

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

    // Get the information
    public boolean exists(String repoName) {
        //
        return true;
    }
}
