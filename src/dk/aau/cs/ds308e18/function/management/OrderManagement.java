package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.io.database.DatabaseExport;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if(conn != null) {
                String sql = "UPDATE orders SET pluckRoute = ?, id = ?, orderReference = ?, expeditionStatus = ?, " +
                        "customerName = ?, orderDate = ?, address = ?, zipCode = ?, receipt = ?, pickup = ?, warehouse = ? " +
                        "category = ?, fleetOwner = ?, printed = ?, route = ?, FV = ?, project = ?, liftAlone = ?, " +
                        "liftingTools = ?, moveTime = ? WHERE orderID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                //TODO HJÆÆÆÆÆÆLP DEN HER stmt.set...
                stmt.setInt(1, order.getPluckRoute());
                stmt.setString(2, order.getID());
                stmt.setString(3, order.getOrderReference());
                stmt.setString(4, order.getExpeditionStatus());
                stmt.setString(5, order.getCustomerName());
                stmt.setString(6, String.valueOf(order.getDate()));
                stmt.setString(7, order.getAddress());
                stmt.setInt(8, order.getZipCode());
                stmt.setInt(9, order.getReceipt());
                stmt.setString(10, String.valueOf(order.isPickup()));
                stmt.setString(11, order.getWarehouse());
                stmt.setString(12, order.getCategory());
                stmt.setString(14, order.getFleetOwner());
                stmt.setString(15, String.valueOf(order.isPrinted()));
                stmt.setString(16, order.getRegion());
                stmt.setString(17, String.valueOf(order.isFV()));
                stmt.setString(18, order.getProject());
                stmt.setString(19, String.valueOf(order.isTotalLiftAlone()));
                stmt.setString(20, String.valueOf(order.isTotalLiftingTools()));
                stmt.setInt(21, order.getTotalTime());
                stmt.setInt(22, order.getOrderID());

                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
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

    /*
    Set the tour ID for an order with a specific order ID
    */
    public static void setTourID(int tourID, int orderID) {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "UPDATE orders SET tourID = ? WHERE orderID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, tourID);
                stmt.setInt(2, orderID);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Get all orders on a specific tour through the tourID on a tour.
    Returns an arraylist of all orders.
    */
    public static ArrayList<Order> getOrdersOnTour(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<Order> ordersOnTour = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM orders WHERE tourID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, tour.getTourID());

                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()) {
                    ordersOnTour.add(DatabaseExport.createOrderFromResultSet(resultSet));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordersOnTour;
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
