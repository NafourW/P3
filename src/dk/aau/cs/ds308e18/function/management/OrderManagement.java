package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
                        "route, FV, project, tourID, liftAlone, liftingTools, moveTime) VALUES (" + getOrderValuesString(order) + ")");

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

    private static String getOrderValuesString(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append(order.getPluckRoute())                  .append(", ")
          .append("'").append(order.getID())              .append("', ")
          .append("'").append(order.getOrderReference())  .append("', ")
          .append("'").append(order.getExpeditionStatus()).append("', ")
          .append("'").append(order.getCustomerName())    .append("', ")
          .append("'").append(order.getDate())            .append("', ")
          .append("'").append(order.getAddress())         .append("', ")
          .append(order.getZipCode())                     .append(", ")
          .append(order.getReceipt())                     .append(", ")
          .append(order.isPickup())                       .append(", ")
          .append("'").append(order.getWarehouse())       .append("', ")
          .append("'").append(order.getCategory())        .append("', ")
          .append("'").append(order.getFleetOwner())      .append("', ")
          .append(order.isPrinted())                      .append(", ")
          .append("'").append(order.getRegion())          .append("', ")
          .append(order.isFV())                           .append(", ")
          .append("'").append(order.getProject())         .append("', ")
          .append(order.getTourID())                      .append(",")
          .append("'").append(order.isTotalLiftAlone())   .append("',")
          .append("'").append(order.isTotalLiftingTools()).append("',")
          .append(order.getTotalTime());

        return sb.toString();
    }

    public static ArrayList<Order> getOrders() {
        return Main.dbExport.exportAllOrders();
    }

    public static ArrayList<Order> getUnassignedOrders() {
        return Main.dbExport.exportUnassignedOrders();
    }

    public static ArrayList<Order> getUnassignedOrdersFiltered(String region, String date) {
        return Main.dbExport.exportUnassignedOrdersFiltered(region, date);
    }
}
