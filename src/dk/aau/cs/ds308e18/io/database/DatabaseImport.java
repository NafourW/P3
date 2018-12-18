package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.function.management.OrderLineManagement;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.WareManagement;
import dk.aau.cs.ds308e18.io.ReadFile;
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

    /*
    ....
    */
    public void importRegionNames() {
        ArrayList<String> regions = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();

        // Read Regions from the regions.txt file and place them in the regions arraylist
        try(BufferedReader reader = new BufferedReader(new FileReader("resources/data/regions.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                regions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Insert all regions from the arraylist
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
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

    public void importAll(String sourcePath){
        importWares(sourcePath);
        importOrders(sourcePath);
        importOrderLines(sourcePath);
    }

    /*
    ....
    */
    public void importOrders(String sourcePath) {
        System.out.println("Importing Orders...");

        DatabaseConnection dbConn = new DatabaseConnection();

        ReadFile readFileObject = new ReadFile();

        // Grab orders from file and put them into a list
        ArrayList<Order> orderList = readFileObject.getOrdersFromFile(sourcePath);

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

    public void importOrderLines(String sourcePath) {
        System.out.println("Importing Order lines...");

        ReadFile readFileObject = new ReadFile();
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                for (Order order : OrderManagement.getOrders()) {
                    // Read orderlines from files that correspond to this order
                    ArrayList<OrderLine> orderLineList = readFileObject.getOrderLinesFromFile(order, sourcePath);

                    // Insert the orderlines into the database
                    for (OrderLine orderLine : orderLineList) {
                        order.addOrderLine(orderLine);
                        OrderLineManagement.createOrderLine(orderLine);
                    }

                    // Get the ware moving values from the wares that belong to the order
                    ArrayList<Integer> orderResults = WareManagement.getWareMovingValues(orderLineList);

                    // Use the values to set total values for the orders
                    order.setTotalLiftAlone(orderResults.get(0) > 0);
                    order.setTotalLiftingTools(orderResults.get(1) > 0);
                    order.setTotalTime(orderResults.get(2));

                    //Update the order in the database
                    OrderManagement.overrideOrder(order);
                }
                System.out.println("Order lines imported.");
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

        DatabaseConnection dbConn = new DatabaseConnection();

        // Grab wares from "getWaresFromFile" method and put them into "wareList"
        ArrayList<Ware> wareList = readFileObject.getWaresFromFile(sourcePath);

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
