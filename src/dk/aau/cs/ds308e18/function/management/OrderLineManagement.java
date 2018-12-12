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
   Insert an order into the order table.
   */
    public static void createOrderLine(OrderLine orderLine) {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if(conn != null) {
                String sql = "INSERT INTO orderlines (orderID, orderReference, wareNumber, wareName, labels, delivered, individual, preparing, individualNumber, model, fullName) VALUES (" + getOrderLineValuesString(orderLine) + ")";
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


    public void orderLineInfo(ArrayList<OrderLine> orderLines){

        float totalTime = 0;
        boolean LiftAlone = true;
        boolean LiftEquipment = false;

        ArrayList<Ware> wares = WareManagement.getWares();

        for (OrderLine orderLine : orderLines){

            for (Ware ware : wares){
                /*
                 * Because of the way the test data was setup, we have to go through four different columns
                 * in order to find the corresponding ware type for the specific order line. Therefore, the
                 * four if statements are made.
                 */

                if (orderLine.getWareNumber().equals(ware.getWareName())){

                    infoToOrderLine(orderLine, ware); //using the method below this method

                    totalTime += orderLine.getMoveTime();

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }


                } else if (orderLine.getIndividual().equals(ware.getWareName())){

                    infoToOrderLine(orderLine, ware); //using the method below this method

                    totalTime += orderLine.getMoveTime();

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }


                } else if (orderLine.getIndividualNumber().equals(ware.getWareName())){

                    infoToOrderLine(orderLine, ware); //using the method below this method

                    totalTime += orderLine.getMoveTime();

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }

                } else if (orderLine.getModel().equals(ware.getWareName())){

                    infoToOrderLine(orderLine, ware); //using the method below this method

                    totalTime += orderLine.getMoveTime();

                    if (!orderLine.isLiftAlone()){
                        LiftAlone = false;
                    }

                    if (orderLine.isLiftEquipment()){
                        LiftEquipment = true;
                    }
                }
            }
        }
    }

    //this method gets information from wares and add them to the order line
    private void infoToOrderLine(OrderLine orderLine, Ware ware){

        orderLine.setMoveTime(ware.getMoveTime() * orderLine.getLabels());

        orderLine.setLiftAlone(ware.isLiftAlone());

        orderLine.setLiftEquipment(ware.isLiftingTools());
    }


}
