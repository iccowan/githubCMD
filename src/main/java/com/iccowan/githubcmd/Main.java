// Main file
package com.iccowan.githubcmd;

// Import packages
import java.util.Scanner;

public class Main {
    // Create a repo
    private static void create(Config configFile) {
        System.out.println("Create a GitHub Repository");
        // Get the user inputs
        Scanner in = new Scanner(System.in);

        System.out.print("Repository Name: ");
        String name = in.nextLine();

        System.out.print("Description: ");
        String desc = in.nextLine();

        System.out.print("Website URL: ");
        String web = in.nextLine();

        System.out.print("Private? 1: YES, 2: NO: 2: ");
        String priv = in.nextLine();
        if(priv.equals(""))
            priv = "2";

        // Wait until either 1 or 2 is entered
        while(!priv.equals("1") && !priv.equals("2")) {
            System.out.println("Enter either 1 or 2.");
            System.out.print("Private? 1: YES, 2: NO: 2: ");
            priv = in.nextLine();
            if(priv.equals(""))
                priv = "2";
        }

        // Set the variable from priv
        String privB = "false";
        if(priv.equals("1"))
            privB = "true";


        // Create the Repo
        System.out.println("Creating the repository...");
        PostRequestCreate request = new PostRequestCreate(configFile, name, desc, web, privB);

        // Print the SSH and HTTP URLs
        System.out.println("Created successfully!");
        System.out.println("SSH URL: " + request.getSSHurl());
        System.out.println("HTTP URL: " + request.getHTTPurl());
    }

    public static void delete(Config configFile) {
        // Get the name of the repository to delete
        Scanner in = new Scanner(System.in);

        System.out.print("Repository Name: ");
        String name = in.nextLine();

        // Check and make sure the repository exists
        Repository repo = new Repository(configFile);
        boolean repoExists = repo.exists(name);
    }

    public static void printCommands() {
        // Print the commands and their descriptions
        System.out.println("GitHub Commands:");
        System.out.println("create - 'github create' - Create a repository.");
        System.out.println("createconfig - 'github createconfig' - Create the config.properties file.");
    }

    public static void main(String[] args) {
        // Make sure the config file exists
        Config configFile = new Config();

        // Check to see what the command was if there was one
        if(1 > args.length) {
            printCommands();
        } else {
            if (args[0].equals("create")) {
                create(configFile);
            } else if(args[0].equals("createconfig")) {
                new Config();
            } else if(args[0].equals("delete")) {
                delete(configFile);
            } else {
                printCommands();
            }
        }
    }
}