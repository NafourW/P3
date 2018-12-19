package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.io.database.Database;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Ware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WareManagement {
    /*
    Insert a ware into the ware table in the Database..
    */
    public static void createWare(Ware ware) {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {

            if (conn != null) {
                String sql = "INSERT INTO warelist (supplier, wareNumber, height, depth, grossHeight, " +
                        "grossDepth, grossWidth, width, wareName, searchName, wareGroup, wareType, " +
                        "liftAlone, liftingTools, moveTime) VALUES (" + getWareValuesString(ware) + ")";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Returns a string with the order's values, formatted for an SQL statement
    */
    private static String getWareValuesString(Ware ware) {
        StringBuilder sb = new StringBuilder();

        sb.append("'").append(ware.getSupplier())         .append("', ")
                .append("'").append(ware.getWareNumber()) .append("', ")
                .append(ware.getHeight())                 .append(", ")
                .append(ware.getDepth())                  .append(", ")
                .append(ware.getGrossHeight())            .append(", ")
                .append(ware.getGrossDepth())             .append(", ")
                .append(ware.getGrossWidth())             .append(", ")
                .append(ware.getWidth())                  .append(", ")
                .append("'").append(ware.getWareName())   .append("', ")
                .append("'").append(ware.getSearchName()) .append("', ")
                .append(ware.getWareGroup())              .append(", ")
                .append("'").append(ware.getWareType())   .append("', ")
                .append("'").append(ware.isLiftAlone())   .append("', ")
                .append("'").append(ware.isLiftingTools()).append("',")
                .append(ware.getMoveTime());

        return sb.toString();
    }

    /*
    Returns liftAlone, liftingTools and totalMoveTime, based on the values from the wares
    in the list of orderlines that is given.
    */
    public static ArrayList<Integer> getWareMovingValues(ArrayList<OrderLine> orderLineList) {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<Integer> information = new ArrayList<>();

        int liftAlone = 1;
        int liftingTools = 0;
        int totalMoveTime = 0;

        try(Connection conn = dbConn.establishConnectionToDatabase()) {

            if (conn != null) {
                String sql = "SELECT liftAlone, liftingTools, moveTime FROM warelist " +
                        "WHERE wareNumber = ? OR wareNumber = ?  OR wareNumber = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                // For every orderline - get their liftAlone, liftingTools and moveTime
                for(OrderLine orderLine : orderLineList) {

                    // Set the WHERE criteria
                    stmt.setString(1, orderLine.getWareNumber());
                    stmt.setString(2, orderLine.getIndividual());
                    stmt.setString(3, orderLine.getIndividualNumber());

                    ResultSet resultSet = stmt.executeQuery();

                    // For every row in the resultSet...
                    while(resultSet.next()) {
                        // Set the liftAlone and liftingTools accordingly
                        if (resultSet.getString(1).equals("false"))
                            liftAlone = 0;

                        if (resultSet.getString(2).equals("true"))
                            liftingTools = 1;

                        // Add the moveTime of every row in the resultSet to the totalMoveTime
                        totalMoveTime += resultSet.getInt(3);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the information to the arraylist and return them
        information.add(liftAlone);
        information.add(liftingTools);
        information.add(totalMoveTime);

        return information;
    }

    // Export all wares from the Database and return them as an arraylist of wares.
    public static ArrayList<Ware> getWares(){
        return Database.dbExport.exportWares();
    }
}
