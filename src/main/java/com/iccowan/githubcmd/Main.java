// Main file
package com.iccowan.githubcmd;

// Import packages
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
        PostRequest request = new PostRequest(name, desc, web, privB);

        // Print the SSH and HTTP URLs
        System.out.println("Created successfully!");
        System.out.println("SSH URL: " + request.getSSHurl());
        System.out.println("HTTP URL: " + request.getHTTPurl());
    }
}