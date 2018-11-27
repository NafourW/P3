package dk.aau.cs.ds308e18.io;
import java.sql.*;
import java.util.ArrayList;

import dk.aau.cs.ds308e18.model.Order;

import javax.xml.crypto.Data;

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
                        "id INT)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }

    /*
    Insert an order into the order table.
    */
    private PreparedStatement createOrder(Order order) {
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
                stmt.setString(15, String.valueOf(order.getRoute()));
                stmt.setString(16, String.valueOf(order.isFV()));
                stmt.setString(17, String.valueOf(order.getProject()));

                return stmt;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    ....
    */
    public void importOrders() {
        ReadFile readFileObject = new ReadFile();
        ArrayList<Order> orderList = readFileObject.orderFile();
        Connection conn = establishConnectionToDatabase();

        try {
            if (conn != null) {
                for(Order order : orderList) {
                    PreparedStatement stmt;
                    stmt = createOrder(order);

                    if (stmt != null)
                        stmt.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
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
                    Order order = new Order(orders.getLong(1), orders.getInt(2),
                            orders.getString(3), orders.getString(4),
                            orders.getString(5), orders.getString(6),
                            orders.getString(7), orders.getLong(8),
                            orders.getLong(9), orders.getBoolean(10),
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

    public static void main(String[] args) {
        DataManagement db = new DataManagement();
        db.importOrders();
        db.exportOrders();
    }
}
