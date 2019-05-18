// Get config information for githubCMD
package com.iccowan.githubcmd;

import java.io.*;
import java.util.Properties;

public class Config {
    // Attributes
    private boolean exists;
    private String githubToken;

    // Construct
    public Config() {
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
            exists = true;
        } catch(IOException e) {
            exists = false;
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
                exists = false;
            }
        }
        githubToken = token;
    }

    public String getToken() {
        return this.githubToken;
    }

    public boolean configExists() {
        return this.exists;
    }
}
