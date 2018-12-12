package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.io.database.DatabaseExport;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Ware;

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
                while(resultSet.next()) {
                    orderLinesOnOrder.add(DatabaseExport.createOrderLineFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderLinesOnOrder;
    }

    public static ArrayList<OrderLine> getOrderLines() {
        return Main.dbExport.exportOrderLines();
    }


    /*
    Overrides orderline information on an order.
    */
    public static void overrideOrderLine(Order order) {
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
        Import updated orderlines from order object to database.
        */
        importUpdatedOrderLines(order);
    }

    private static void importUpdatedOrderLines(Order order) {
        for(OrderLine orderLine : order.getOrderLines()) {
            orderLine.setOrder(order.getID());
            createOrderLine(orderLine);
        }
    }


    public static int orderLoadTime(ArrayList<OrderLine> orderLines){

        int totalTime = 0;

        ArrayList<Ware> wares = WareManagement.getWares();

        for (OrderLine orderLine : orderLines){

            for (Ware ware : wares){
                /*
                 * Because of the way the test data was setup, we have to go through four different columns
                 * in order to find the corresponding ware type for the specific order line. Therefore, the
                 * four if statements are made.
                 */

                // if-statements checks if the ware in orderline has been found
                // in the list of existing ware types
                if (orderLine.getWareNumber().equals(ware.getWareName())){

                    orderLine.setMoveTime(ware.getMoveTime() * orderLine.getLabels());

                    totalTime += orderLine.getMoveTime();

                } else if (orderLine.getIndividual().equals(ware.getWareName())){

                    orderLine.setMoveTime(ware.getMoveTime() * orderLine.getLabels());

                    totalTime += orderLine.getMoveTime();

                } else if (orderLine.getIndividualNumber().equals(ware.getWareName())){

                    orderLine.setMoveTime(ware.getMoveTime() * orderLine.getLabels());

                    totalTime += orderLine.getMoveTime();

                } else if (orderLine.getModel().equals(ware.getWareName())){

                    orderLine.setMoveTime(ware.getMoveTime() * orderLine.getLabels());

                    totalTime += orderLine.getMoveTime();
                }
            }
        }
        return totalTime;
    }

    public static boolean isLiftAlone(ArrayList<OrderLine> orderLines){
        boolean LiftAlone = true;

        ArrayList<Ware> wares = WareManagement.getWares();

        for (OrderLine orderLine : orderLines){

            for (Ware ware : wares){

                if (orderLine.getWareNumber().equals(ware.getWareName())){

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                } else if (orderLine.getIndividual().equals(ware.getWareName())){

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                } else if (orderLine.getIndividualNumber().equals(ware.getWareName())){

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                } else if (orderLine.getModel().equals(ware.getWareName())){

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }
                }
            }
        }

        return  LiftAlone;
    }

    public static boolean isLiftEquipment(ArrayList<OrderLine> orderLines){
        boolean LiftEquipment = false;

        ArrayList<Ware> wares = WareManagement.getWares();

        for (OrderLine orderLine : orderLines){

            for (Ware ware : wares){

                if (orderLine.getWareNumber().equals(ware.getWareName())){

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }

                } else if (orderLine.getIndividual().equals(ware.getWareName())){

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }

                } else if (orderLine.getIndividualNumber().equals(ware.getWareName())){

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }

                } else if (orderLine.getModel().equals(ware.getWareName())){

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }
                }
            }
        }
        return LiftEquipment;
    }

    /*
        if (!orderLine.isLiftAlone()){
            LiftAlone = false;
        }

        if (orderLine.isLiftEquipment()){
            LiftEquipment = true;
        }
        */
}
