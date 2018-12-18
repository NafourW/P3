package dk.aau.cs.ds308e18.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    /*
    Establish connection to the Local Database.
    This is needed for any form of Database management such as "SELECT" and "INSERT".
    For every method, it's needed to call this function for the SQL statements to work.

    The Local Database has to be up and running for this function to work.
    Without this function, the rest in the class won't work.
    */
    public Connection establishConnectionToDatabase() {
        try {
            return DriverManager.getConnection(Database.getHostWithDatabase(), Database.getUserName(), Database.getPassword());
        } catch (SQLException e) {
            System.out.println("Couldn't establish a connection.\n" +
                    "Try restarting the Database");
            e.printStackTrace();
        }
        return null;
    }
}
