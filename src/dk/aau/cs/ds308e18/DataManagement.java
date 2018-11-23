package dk.aau.cs.ds308e18;
import java.sql.*;

public class DataManagement {

    /*
    Establish connection to Local Database
    The Local Database has to be up and running for this function to work.
    Without this function, the rest in the class won't work.
     */
    private Connection establishConnectionToDatabase() {
        String host = "jdbc:mysql://localhost:3306/vibocold_db";
        String uName = "";
        String uPass = "";
        try {
            return DriverManager.getConnection(host, uName, uPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Couldn't establish a connection.\n" +
                "Try restarting the Database.");
        return null;
    }

    // Create the Database in case it doesn't exist
    private void createDatabase() {
        Connection conn = establishConnectionToDatabase();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("CREATE DATABASE vibocold_db");

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void createOrderTable() {
        Connection conn = establishConnectionToDatabase();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("CREATE TABLE Orders ");

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void createOrder() {
        Connection conn = establishConnectionToDatabase();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("INSERT INTO Orders (ordername) VALUES (Test1)");

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void exportOrders() {
        Connection conn = establishConnectionToDatabase();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders");

            while (rs.next()) {
                System.out.println(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Export tours from Local Database
    private void exportTours() {

    }

    public static void main(String[] args) {
        DataManagement db = new DataManagement();
        db.createDatabase();
        db.createOrderTable();

        db.createOrder();
        db.exportOrders();
    }
}
