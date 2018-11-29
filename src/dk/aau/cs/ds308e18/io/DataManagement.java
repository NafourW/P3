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
                        "consignor INT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
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
                        "supplier INT," +
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
                        "liftAlone INT," +
                        "liftingTools INT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
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
        Connection conn = establishConnectionToDatabase();
        try {
            if (conn != null) {
                String sql = "INSERT INTO regions (regionName) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                for(String region : regions) {
                    stmt.setString(1, region);
                    stmt.executeUpdate();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ....
    */
    public ArrayList<String> exportRegionNames() {
        ArrayList<String> regionList = new ArrayList<>();
        Connection conn = establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet regions = stmt.executeQuery("SELECT regionName FROM regions");

                while (regions.next()) {
                    regionList.add(regions.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regionList;
    }

    /*
    Insert an order into the order table.
    */
    private void createOrder(Order order) {
        try {
            Connection conn = establishConnectionToDatabase();
            if(conn != null) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (" +
                        "pluckRoute, id, orderReference, expeditionStatus, customerName, orderDate, address, " +
                        "zipCode, receipt, pickup, warehouse, category, fleetOwner, printed, " +
                        "route, FV, project) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                //TODO Fix det her med en hjælpe funktion
                stmt.setString(1, String.valueOf(order.getPluckRoute()));
                stmt.setString(2, String.valueOf(order.getID()));
                stmt.setString(3, String.valueOf(order.getOrderReference()));
                stmt.setString(4, String.valueOf(order.getExpeditionStatus()));
                stmt.setString(5, String.valueOf(order.getCustomerName()));
                stmt.setString(6, String.valueOf(order.getDate()));
                stmt.setString(7, String.valueOf(order.getAddress()));
                stmt.setString(8, String.valueOf(order.getZipCode()));
                stmt.setString(9, String.valueOf(order.getReceipt()));
                stmt.setString(10, String.valueOf(order.isPickup()));
                stmt.setString(11, String.valueOf(order.getWarehouse()));
                stmt.setString(12, String.valueOf(order.getCategory()));
                stmt.setString(13, String.valueOf(order.getFleetOwner()));
                stmt.setString(14, String.valueOf(order.isPrinted()));
                stmt.setString(15, String.valueOf(order.getRegion()));
                stmt.setString(16, String.valueOf(order.isFV()));
                stmt.setString(17, String.valueOf(order.getProject()));

                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Insert a tour into the database.
    */
    public void createTour(Tour tour) {
        Connection conn = establishConnectionToDatabase();
        String sql = "INSERT INTO tours (tourDate, packingDate, id, region, regionDetail, " +
                "driver, status, consignor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tour.getTourDate());
                stmt.setString(2, tour.getPackingDate());
                stmt.setString(3, tour.getID().toString());
                stmt.setString(4, tour.getRegion());
                stmt.setString(5, tour.getRegionDetail());
                stmt.setString(6, tour.getDriver());
                stmt.setString(7, tour.getStatus());
                stmt.setString(8, tour.getConsignor().toString());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ....
    */
    public void createWare(Ware ware) {
        Connection conn = establishConnectionToDatabase();
        String sql = "INSERT INTO wares (supplier, wareNumber, height, depth, grossHeight, " +
                "grossDepth, grossWidth, width, wareName, searchName, wareGroup, wareType, " +
                "liftAlone, liftingTools) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, String.valueOf(ware.getSupplier()));
                stmt.setString(2, String.valueOf(ware.getWareNumber()));
                stmt.setString(3, String.valueOf(ware.getHeight()));
                stmt.setString(4, String.valueOf(ware.getDepth()));
                stmt.setString(5, String.valueOf(ware.getGrossHeight()));
                stmt.setString(6, String.valueOf(ware.getGrossDepth()));
                stmt.setString(7, String.valueOf(ware.getGrossWidth()));
                stmt.setString(8, String.valueOf(ware.getWidth()));
                stmt.setString(9, String.valueOf(ware.getWareName()));
                stmt.setString(10, String.valueOf(ware.getSearchName()));
                stmt.setString(11, String.valueOf(ware.getWareGroup()));
                stmt.setString(12, String.valueOf(ware.getWareType()));
                stmt.setString(13, String.valueOf(ware.isLiftAlone()));
                stmt.setString(14, String.valueOf(ware.isLiftingTools()));

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ....
    */
    public void importOrders() {
        System.out.println("Importing Orders...");
        ReadFile readFileObject = new ReadFile();

        // Grab orders from "orderFile" method and put them into "orderList"
        ArrayList<Order> orderList = readFileObject.orderFile();

        Connection conn = establishConnectionToDatabase();

        if (conn != null) {

            // For every order, put the order in the database
            for (Order order : orderList) {
                createOrder(order);
            }
            System.out.println("Orders imported.");
        }
    }

    /*
    ....
    */
    public void importWares() {
        System.out.println("Importing Wares...");
        ReadFile readFileObject = new ReadFile();

        // Grab wares from "wareTypes" method and put them into "wareList"
        ArrayList<Ware> wareList = readFileObject.wareTypes();

        Connection conn = establishConnectionToDatabase();

        if (conn != null) {

            // For every ware, put the ware in the database
            for (Ware ware : wareList) {
                createWare(ware);
            }
            System.out.println("Wares imported.");
        }
    }

    /*
    Export everything from the order table.
    Print them in the terminal
    */
    public ArrayList<Order> exportOrders() {
        ArrayList<Order> orderList = new ArrayList<>();
        Connection conn = establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orders = stmt.executeQuery("SELECT * FROM orders");

                // As long as there is a "next row" in the table, create an order based on that row
                while (orders.next()) {
                    //TODO Hjælp den her funktion
                    Order order = new Order(orders.getInt(1), orders.getInt(2),
                            orders.getString(3), orders.getString(4),
                            orders.getString(5), orders.getDate(6).toLocalDate(),
                            orders.getString(7), orders.getInt(8),
                            orders.getInt(9), orders.getBoolean(10),
                            orders.getString(11), orders.getString(12),
                            orders.getString(13), orders.getBoolean(14),
                            orders.getString(15), orders.getBoolean(16),
                            orders.getString(17));
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    /*
    Export everything from the tour table.
    Print them in the terminal
    */
    private void exportTours() {
        Connection conn = establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet tours = stmt.executeQuery("SELECT * FROM tours");

                while (tours.next()) {
                    System.out.println(tours.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
