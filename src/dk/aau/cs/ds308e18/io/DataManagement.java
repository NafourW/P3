package dk.aau.cs.ds308e18.io;
import java.sql.*;

public class DataManagement {

    /*
    Establish connection to the Local Database.
    This is needed for any form of Database management such as "SELECT" and "INSERT".
    For every method, it's needed to call this function for the SQL statements to work.

    The Local Database has to be up and running for this function to work.
    Without this function, the rest in the class won't work.
    */
    private Connection establishConnectionToDatabase() {
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

    // Run this function to make sure a database and corresponding tables are created
    private void databaseSetup() {
        createDatabase();
        createOrderTable();
    }

    /*
    Create a database called "vibocold_db".
    If it already exists, it'll print a response.
    */
    private void createDatabase() {
        String host = "jdbc:mysql://localhost:3306/";
        String uName = "root";
        String uPass = "";
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE vibocold_db");
        } catch(SQLException e) {
            System.out.println("The database already exists.");
        }
    }

    /*
    Create a table called "orders".
    If it already exists, it'll print a response.
    */
    private void createOrderTable() {
        Connection conn = establishConnectionToDatabase();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE orders (name VARCHAR(255))");

        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }

    /*
    ....
    */
    private void createOrder(String ordername) {
        String host = "jdbc:mysql://localhost:3306/vibocold_db";
        String uName = "root";
        String uPass = "";
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (name) VALUES (?)");
            stmt.setString(1, ordername);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ....
    */
    private void exportOrders() {
        Connection conn = establishConnectionToDatabase();
        try {
            Statement stmt = conn.createStatement();
            ResultSet orders = stmt.executeQuery("SELECT * FROM orders");

            while (orders.next()) {
                System.out.println(orders.getString(1));
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
        db.databaseSetup();
        db.createOrder("i love memos");
        db.exportOrders();
    }
}
