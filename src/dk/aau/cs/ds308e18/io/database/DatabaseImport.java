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



    /*
    ....
    */
    public void importOrders(String sourcePath) {
        System.out.println("Importing Orders...");

        DatabaseConnection dbConn = new DatabaseConnection();

        ReadFile readFileObject = new ReadFile();

        // Grab orders from "orderFile" method and put them into "orderList"
        ArrayList<Order> orderList = readFileObject.orderFile(sourcePath);

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                // For every order, put the order in the database
                for (Order order : orderList) {

                    //for each order, get the corresponding order line with wares
                    ArrayList<OrderLine> orderLines = order.getOrderLines();

                    ArrayList<Integer> orderResults = WareManagement.getOrderlineWare(orderLines);

                    if (orderResults.get(0) > 0){
                        order.setTotalLiftAlone(true);
                    } else {
                        order.setTotalLiftAlone(false);
                    }

                    if (orderResults.get(1) > 0){
                        order.setTotalLiftingTools(true);
                    } else {
                        order.setTotalLiftingTools(false);
                    }

                    order.setTotalTime(orderResults.get(2));

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

        // Grab order lines from "orderLineTypes" method and put them into "orderLineList"
        ArrayList<OrderLine> orderLineList = readFileObject.orderLines(sourcePath);

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {

                // For every ware, put the ware in the database
                for (OrderLine orderLine : orderLineList) {
                    OrderLineManagement.createOrderLine(orderLine);
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

        // Grab wares from "wareTypes" method and put them into "wareList"
        ArrayList<Ware> wareList = readFileObject.wareTypes(sourcePath);

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
