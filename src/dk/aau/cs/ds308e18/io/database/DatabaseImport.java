package dk.aau.cs.ds308e18.io;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import dk.aau.cs.ds308e18.model.Ware;

public class DataManagement {

    public DataManagement() {
        databaseSetup();
    }

    /*
    Establish connection to the Local Database.
    This is needed for any form of Database management such as "SELECT" and "INSERT".
    For every method, it's needed to call this function for the SQL statements to work.

    The Local Database has to be up and running for this function to work.
    Without this function, the rest in the class won't work.
    */
    //TODO ADD FINALLY TO ALL TRY-CATCH (CLOSE CONNECTION)
    //TODO Check Mac'en

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
        Connection conn = establishConnectionToDatabase();
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
        Connection conn = establishConnectionToDatabase();
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
        Connection conn = establishConnectionToDatabase();
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
        Connection conn = establishConnectionToDatabase();
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
                importRegionNames();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }
    /*
    ....
    */
    private void importRegionNames() {
        ArrayList<String> regions = new ArrayList<>();

        // Read Regions from the regions.txt file and place them in the regions arraylist
        try(BufferedReader reader = new BufferedReader(new FileReader("resources/regions.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                regions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Insert all regions from the arraylist
        try {
            Connection conn = establishConnectionToDatabase();
            if (conn != null) {
                String sql = "INSERT INTO regions (regionName) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                for(String region : regions) {
                    stmt.setString(1, region);
                    stmt.executeUpdate();
                }
                conn.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }



    /*
    ....
    */
    public void importOrders(String sourcePath) {
        System.out.println("Importing Orders...");
        ReadFile readFileObject = new ReadFile();

        // Grab orders from "orderFile" method and put them into "orderList"
        ArrayList<Order> orderList = readFileObject.orderFile(sourcePath);
        try {
            Connection conn = establishConnectionToDatabase();

            if (conn != null) {
                // For every order, put the order in the database
                for (Order order : orderList) {
                    createOrder(order);
                }
                System.out.println("Orders imported.");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ....
    */
    public void importWares(String sourcePath) {
        System.out.println("Importing Wares...");
        ReadFile readFileObject = new ReadFile();

        // Grab wares from "wareTypes" method and put them into "wareList"
        ArrayList<Ware> wareList = readFileObject.wareTypes(sourcePath);

        try {
            Connection conn = establishConnectionToDatabase();

            if (conn != null) {

                // For every ware, put the ware in the database
                for (Ware ware : wareList) {
                    createWare(ware);
                }
                System.out.println("Wares imported.");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
