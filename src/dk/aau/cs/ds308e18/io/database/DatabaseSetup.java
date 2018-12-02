package dk.aau.cs.ds308e18.io.database;

import java.sql.*;

public class DatabaseSetup {

    public DatabaseSetup() {
        databaseSetup();
    }

    // Run this function to make sure a database and corresponding tables are created.
    private void databaseSetup() {
        createDatabase();
        createOrderTable();
        createTourTable();
        createWareTable();
        createRegionTable();
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
            conn.close();
        } catch(SQLException e) {
            System.out.println("The database already exists.");
        }
    }

    /*
    Create a table called "orders" with all the attributes associated with the class "Order".
    If it already exists, it'll print a response.
    */
    private void createOrderTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                String sql = "CREATE TABLE orders (" +
                        "pluckRoute INT," +
                        "id INT," +
                        "orderReference VARCHAR(255)," +
                        "expeditionStatus VARCHAR(255)," +
                        "customerName VARCHAR(255)," +
                        "orderDate VARCHAR(255)," +
                        "address VARCHAR(255)," +
                        "zipCode INT," +
                        "receipt INT," +
                        "pickup VARCHAR(255)," +
                        "warehouse VARCHAR(255)," +
                        "category VARCHAR(255)," +
                        "fleetOwner VARCHAR(255)," +
                        "printed VARCHAR(255)," +
                        "route VARCHAR(255)," +
                        "FV VARCHAR(255)," +
                        "project VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }

    /*
    Create a table called "tours" with all the attributes associated with the class "tour".
    If it already exists, it'll print a response.
    */
    private void createTourTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                String sql = "CREATE TABLE tours (" +
                        "tourDate VARCHAR(255)," +
                        "packingDate VARCHAR(255)," +
                        "id INT," +
                        "region VARCHAR(255)," +
                        "regionDetail VARCHAR(255)," +
                        "driver VARCHAR(255)," +
                        "status VARCHAR(255)," +
                        "consignor VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }

    /*
    Create a table called "wares" with all the attributes associated with the class "Ware".
    If it already exists, it'll print a response.
    */
    private void createWareTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                String sql = "CREATE TABLE wares (" +
                        "supplier VARCHAR(255)," +
                        "wareNumber VARCHAR(255)," +
                        "height INT," +
                        "depth INT," +
                        "grossHeight INT," +
                        "grossDepth INT," +
                        "grossWidth INT," +
                        "width INT," +
                        "wareName VARCHAR(255)," +
                        "searchName VARCHAR(255)," +
                        "wareGroup INT," +
                        "wareType VARCHAR(255)," +
                        "liftAlone VARCHAR(255)," +
                        "liftingTools VARCHAR(255)," +
                        "moveTime FLOAT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }

    /*
    ....
    */
    private void createRegionTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        DatabaseImport dbImpo = new DatabaseImport();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                String sql = "CREATE TABLE regions (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "regionName VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();

                /* Import region names into the table ONLY if doesn't exist
                to avoid importing the names every time the program is executed.
                */
                dbImpo.importRegionNames();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }
}
