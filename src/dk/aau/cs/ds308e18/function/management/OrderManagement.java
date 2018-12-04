package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderManagement {

    /*
    Insert an order into the order table.
    */
    public static void createOrder(Order order) {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if(conn != null) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (" +
                        "pluckRoute, id, orderReference, expeditionStatus, customerName, orderDate, address, " +
                        "zipCode, receipt, pickup, warehouse, category, fleetOwner, printed, " +
                        "route, FV, project, tourID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
                stmt.setString(18, String.valueOf(order.getTourID()));

                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Replace an order in the database with this order (both should have the same orderID)
    */
    public static void overrideOrder(Order order) {
        //TODO: hjælp
    }

    public static ArrayList<Order> getOrders() {
        return Main.dbExport.exportOrders();
    }

    public static ArrayList<Order> getUnassignedOrders() {
        return Main.dbExport.exportUnassignedOrders();
    }
}
