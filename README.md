GitHub CMD
==========

By Ian Cowan

<i>Written in Java 1.8.0_201</i>

Installation
------------
- Download the jar file and run for the first time to create the config.properties file
- Put your GitHub API Key into the config file
- In the github.bat file, change `[java.exe Path]` to your java.exe file, and change `[githubCMD.jar Path]` to the path of the downloaded jar file
- Add the github.bat file to `C:\Windows\System32`
- Now to add a GitHub repository, type `github create` in the command line and follow the prompts
- Finally, the SSH and HTTP URLs will be displayed to add as your remote to your project

For Git Bash Users
------------------
- If you're using Git Bash, you will need to add the alias in .bashrc to your .bashrc file (normally in your home directory)
