package dk.aau.cs.ds308e18.io.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import dk.aau.cs.ds308e18.io.ReadFile;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.WareManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Ware;

public class DatabaseImport {

    //TODO ADD FINALLY TO ALL TRY-CATCH (CLOSE CONNECTION)
    //TODO Check Mac'en

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
        OrderManagement orderMan = new OrderManagement();

        ReadFile readFileObject = new ReadFile();

        // Grab orders from "orderFile" method and put them into "orderList"
        ArrayList<Order> orderList = readFileObject.orderFile(sourcePath);
        try {
            Connection conn = dbConn.establishConnectionToDatabase();

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

                    orderMan.createOrder(order);
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

        DatabaseConnection dbConn = new DatabaseConnection();
        WareManagement wareMan = new WareManagement();

        // Grab wares from "wareTypes" method and put them into "wareList"
        ArrayList<Ware> wareList = readFileObject.wareTypes(sourcePath);

        try {
            Connection conn = dbConn.establishConnectionToDatabase();

            if (conn != null) {

                // For every ware, put the ware in the database
                for (Ware ware : wareList) {
                    wareMan.createWare(ware);
                }
                System.out.println("Wares imported.");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
