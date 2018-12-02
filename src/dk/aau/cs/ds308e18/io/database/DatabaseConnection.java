package dk.aau.cs.ds308e18.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection establishConnectionToDatabase() {
        String host = "jdbc:mysql://localhost:3306/vibocold_db";
        String uName = "root";
        String uPass = "";
        try {
            return DriverManager.getConnection(host, uName, uPass);
        } catch (SQLException e) {
            System.out.println("Couldn't establish a connection.\n" +
                    "Try restarting the Database");
            e.printStackTrace();
        }
        return null;
    }
}
