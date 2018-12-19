package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.io.database.DatabaseExport;
import dk.aau.cs.ds308e18.io.database.Database;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderLineManagement {
    /*
    Insert an orderline into the orderline table.
    */
    public static void createOrderLine(OrderLine orderLine) {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if(conn != null) {
                String sql = "INSERT INTO orderlines (orderID, orderReference, wareNumber, wareName, " +
                        "labels, delivered, individual, preparing, individualNumber, model, fullName) " +
                        "VALUES (" + getOrderLineValuesString(orderLine) + ")";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Returns a string with the orderline's values, formatted for an SQL statement
    */
    private static String getOrderLineValuesString(OrderLine orderLine) {
        StringBuilder sb = new StringBuilder();

        sb.append(orderLine.getOrderID())                           .append(", ")
                .append("'").append(orderLine.getOrder())           .append("', ")
                .append("'").append(orderLine.getWareNumber())      .append("', ")
                .append("'").append(orderLine.getWareName())        .append("', ")
                .append(orderLine.getLabels())                      .append(", ")
                .append(orderLine.getDelivered())                   .append(", ")
                .append("'").append(orderLine.getIndividual())      .append("', ")
                .append("'").append(orderLine.isPreparing())        .append("', ")
                .append("'").append(orderLine.getIndividualNumber()).append("', ")
                .append("'").append(orderLine.getModel())           .append("', ")
                .append("'").append(orderLine.getFullName())        .append("'");
        return sb.toString();
    }

    /*
    Get all the orderlines on an order.
    Returns an arraylist of all the orderlines on the order from the database.
    */
    public static ArrayList<OrderLine> getOrderLinesOnOrder(Order order) {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<OrderLine> orderLinesOnOrder = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM orderlines WHERE orderReference = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, order.getID());

                ResultSet resultSet = stmt.executeQuery();

                // Create orderLine objects based on the result set and add them to the arraylist.
                while(resultSet.next()) {
                    orderLinesOnOrder.add(DatabaseExport.createOrderLineFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderLinesOnOrder;
    }

    /*
    Return all OrderLines in the database in an arraylist.
    */
    public static ArrayList<OrderLine> getOrderLines() {
        return Database.dbExport.exportOrderLines();
    }

    /*
    Overrides orderline information on an order.
    Deletes them from the database and import the updated ones.
    */
    static void overrideOrderLine(Order order) {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "DELETE FROM orderlines WHERE orderReference = ?";

                /*
                Delete orderlines from database.
                */
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, order.getID());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        Import the updated orderlines to the database.
        */
        importUpdatedOrderLines(order);
    }

    /*
    Import updated orderlines from order object to database.
    */
    private static void importUpdatedOrderLines(Order order) {

        for(OrderLine orderLine : order.getOrderLines()) {

            // Set the orderReference to the given order because
            orderLine.setOrder(order.getID());
            createOrderLine(orderLine);
        }
    }
}
