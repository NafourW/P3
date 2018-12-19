package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.io.files.GetProperties;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {
    private static String host;
    private static String databaseName;
    private static String userName;
    private static String password;

    public static DatabaseImport dbImport;
    public static DatabaseExport dbExport;

    public Database() throws IOException{
        loadConfiguration();
        dbImport = new DatabaseImport();
        dbExport = new DatabaseExport();
        databaseSetup();
    }

    private void loadConfiguration() throws IOException {
        Properties properties = GetProperties.getProperties("mySQL");

        String address      = properties.getProperty("address",      "localhost");
        String port         = properties.getProperty("port",         "3306");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jdbc:mysql://")
                .append(address)
                .append(":")
                .append(port)
                .append("/");

        host = stringBuilder.toString();
        databaseName = properties.getProperty("databaseName", "vibocold_db");

        userName = properties.getProperty("userName", "root");
        password = properties.getProperty("password", "");
    }

    public static String getHost(){
        return host;
    }

    public static String getHostWithDatabase() {return host + databaseName;}

    public static String getDatabaseName() {return databaseName;}

    public static String getUserName(){
        return userName;
    }

    public static String getPassword(){
        return password;
    }

    // Run this function to make sure a database and corresponding tables are created.
    private void databaseSetup() {
        createDatabase();
        createTourTable();
        createWareListTable();
        createRegionTable();
        createAddressesTable();
        createOrderLineTable();
        createOrderTable();
    }

    /*
    Create a database called "vibocold_db".
    If it already exists, it'll print a response.
    */
    private void createDatabase() {
        try(Connection conn = DriverManager.getConnection(getHost(), getUserName(), getPassword())) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE " + getDatabaseName());
        } catch(SQLException e) {
            System.out.println("The database already exists.");
        }
    }

    public void reloadDatabase() {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DROP DATABASE " + getDatabaseName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseSetup();
    }

    /*
    Create a table called "orders" with all the attributes associated with the class "Order".
    If it already exists, it'll print a response.
    */
    private void createOrderTable() {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "CREATE TABLE orders (" +
                        "orderID INT PRIMARY KEY AUTO_INCREMENT," +
                        "tourID INT," +
                        "pluckRoute INT," +
                        "id VARCHAR(255)," +
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
                        "project VARCHAR(255)," +
                        "liftAlone VARCHAR(255)," +
                        "liftingTools VARCHAR(255)," +
                        "moveTime INT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("The order table already exists.");
        }
    }

    /*
    Create a table called "tours" with all the attributes associated with the class "tour".
    If it already exists, it'll print a response.
    */
    private void createTourTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "CREATE TABLE tours (" +
                        "tourID INT PRIMARY KEY AUTO_INCREMENT," +
                        "tourDate VARCHAR(255)," +
                        "packingDate VARCHAR(255)," +
                        "id INT," +
                        "region VARCHAR(255)," +
                        "driver VARCHAR(255)," +
                        "status VARCHAR(255)," +
                        "consignor VARCHAR(255)," +
                        "tourTime INT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("The tour table already exists.");
        }
    }

    /*
    Create a table called "addresses" that holds addresses and their latitude and longitude.
    */
    private void createAddressesTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "CREATE TABLE addresses (" +
                        "address VARCHAR(255) UNIQUE ," +
                        "latitude VARCHAR(255)," +
                        "longitude VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("The addresses table already exists.");
        }
    }

    private void createOrderLineTable() {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "CREATE TABLE orderlines(" +
                        "orderID INT," +
                        "orderReference VARCHAR(255)," +
                        "wareNumber VARCHAR(255)," +
                        "wareName VARCHAR(255)," +
                        "labels INT," +
                        "delivered INT," +
                        "individual VARCHAR(255)," +
                        "preparing VARCHAR(255)," +
                        "individualNumber VARCHAR(255)," +
                        "model VARCHAR(255)," +
                        "fullName VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("The orderline table already exists.");
        }
    }

    /*
    Create a table called "warelist" with all the attributes associated with the class "Ware".
    If it already exists, it'll print a response.
    */
    private void createWareListTable() {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "CREATE TABLE warelist (" +
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
                        "moveTime INT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("The warelist table already exists.");
        }
    }

    /*
    ....
    */
    private void createRegionTable() {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "CREATE TABLE regions (" +
                        "regionName VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();

                /*
                Import region names into the table ONLY if doesn't exist
                to avoid importing the names every time the program is executed.
                */
                dbImport.importRegionNames();
            }
        } catch(SQLException e) {
            System.out.println("The region table already exists.");
        }
    }
}
