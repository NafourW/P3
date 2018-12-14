# Tour Planning Application



## Setup

#### Running the software:

In order to run the application, a local MySQL database is needed.

The easiest way to set it up, is to download [XAMPP](https://www.apachefriends.org/index.html) and start MySQL.

The application should automatically connect to the database, with the default settings.

The address, port, username and password can be changed in the mySQL.properties file

In order to find coordinates from adresses, an API-key for GraphHopper must be set in the settings menu.

#### Libraries required:

In order to compile the application these libraries must be present:

1. mysql-connector-java 

   can be downloaded from Maven (mysql:mysql-connector-java:5.1.40)

2. [graphhopper-tools-0.4.1-jar-with-dependencies.jar](https://cdn.discordapp.com/attachments/493773814711189514/519472014063304704/graphhopper-tools-0.4.1-jar-with-dependencies.jar)

3. [json-simple-1.1.1.jar](https://cdn.discordapp.com/attachments/493773814711189514/520185506865348618/json-simple-1.1.1.jar)
