package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.function.management.OrderLineManagement;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.WareManagement;
import dk.aau.cs.ds308e18.io.files.ReadFile;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Ware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseImport {

    // Import wares, orders and orderlines.
    public void importAll(String sourcePath){
        importWares(sourcePath);
        importOrders(sourcePath);
        importOrderLines(sourcePath);
    }

    /*
    Read regions.txt and add the regions to an arraylist.
    Import region names from the arraylist and add them to the Database.
    */
    void importRegionNames() {
        DatabaseConnection dbConn = new DatabaseConnection();
        ReadFile readFile = new ReadFile();

        // Grab regions from file and put them in a list.
        ArrayList<String> regions = readFile.getRegionsFromFile();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "INSERT INTO regions (regionName) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(sql);

                // For every region, put the region in the database.
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
    Use ReadFile class to read order files and store them in an arraylist of orders.
    Import orders in arraylist to the Database.
    */
    public void importOrders(String sourcePath) {
        System.out.println("Importing Orders...");

        DatabaseConnection dbConn = new DatabaseConnection();
        ReadFile readFile = new ReadFile();

        // Grab orders from file and put them into a list
        ArrayList<Order> orderList = readFile.getOrdersFromFile(sourcePath);

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                // For every order, put the order in the database
                for (Order order : orderList) {
                    OrderManagement.createOrder(order);
                }
                System.out.println("Orders imported.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Use ReadFile class to read orderline files and store them in an arraylist of orderlines.
    Import the orderlines in the Database.
    Override the orderline's orderID with the corresponding order's ID in the Database.
    */
    public void importOrderLines(String sourcePath) {
        System.out.println("Importing Order lines...");

        ReadFile readFile = new ReadFile();
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {

                // The database already has all orders stored, therefore we use OrderManagement to retrieve orders.
                for (Order order : OrderManagement.getOrders()) {

                    // Read orderlines from files that correspond to this order
                    ArrayList<OrderLine> orderLineList = readFile.getOrderLinesFromFile(order, sourcePath);

                    // Insert the orderlines into the database
                    for (OrderLine orderLine : orderLineList) {

                        // Add orderline to order object.
                        order.addOrderLine(orderLine);

                        // Add orderline to database.
                        OrderLineManagement.createOrderLine(orderLine);
                    }

                    // Get the ware moving values from the wares that belong to the order
                    ArrayList<Integer> orderResults = WareManagement.getWareMovingValues(orderLineList);

                    // Use the values to set total values for the orders
                    order.setTotalLiftAlone(orderResults.get(0) > 0);
                    order.setTotalLiftingTools(orderResults.get(1) > 0);
                    order.setTotalTime(orderResults.get(2));

                    //Update the order and orderline in the database
                    OrderManagement.overrideOrder(order);
                }
                System.out.println("Order lines imported.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Use ReadFile class to read ware files and store them in an arraylist of wares.
    Import the wares in the Database.
    */
    public void importWares(String sourcePath) {
        System.out.println("Importing Wares...");

        DatabaseConnection dbConn = new DatabaseConnection();
        ReadFile readFile = new ReadFile();

        // Grab wares from "getWaresFromFile" method and put them into "wareList"
        ArrayList<Ware> wareList = readFile.getWaresFromFile(sourcePath);

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {

                // For every ware, put the ware in the database
                for (Ware ware : wareList) {
                    WareManagement.createWare(ware);
                }
                System.out.println("Wares imported.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
