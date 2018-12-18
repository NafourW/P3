package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.io.database.DatabaseSetup;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Ware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WareManagement {
    /*
    ....
    */
    public static void createWare(Ware ware) {
        DatabaseConnection dbConn = new DatabaseConnection();
        String sql = "INSERT INTO warelist (supplier, wareNumber, height, depth, grossHeight, " +
                "grossDepth, grossWidth, width, wareName, searchName, wareGroup, wareType, " +
                "liftAlone, liftingTools, moveTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = dbConn.establishConnectionToDatabase()) {

            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, String.valueOf(ware.getSupplier()));
                stmt.setString(2, String.valueOf(ware.getWareNumber()));
                stmt.setString(3, String.valueOf(ware.getHeight()));
                stmt.setString(4, String.valueOf(ware.getDepth()));
                stmt.setString(5, String.valueOf(ware.getGrossHeight()));
                stmt.setString(6, String.valueOf(ware.getGrossDepth()));
                stmt.setString(7, String.valueOf(ware.getGrossWidth()));
                stmt.setString(8, String.valueOf(ware.getWidth()));
                stmt.setString(9, String.valueOf(ware.getWareName()));
                stmt.setString(10, String.valueOf(ware.getSearchName()));
                stmt.setString(11, String.valueOf(ware.getWareGroup()));
                stmt.setString(12, String.valueOf(ware.getWareType()));
                stmt.setString(13, String.valueOf(ware.isLiftAlone()));
                stmt.setString(14, String.valueOf(ware.isLiftingTools()));
                stmt.setString(15, String.valueOf(ware.getMoveTime()));

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Returns liftAlone, liftingTools and totalMoveTime, based on the values from the wares
    in the list of orderlines that is given.
    */
    public static ArrayList<Integer> getWareMovingValues(ArrayList<OrderLine> orderLineList) {
        ArrayList<Integer> information = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();
        String sql = "SELECT liftAlone, liftingTools, moveTime FROM warelist WHERE wareNumber = ? OR wareNumber = ?  OR wareNumber = ?";

        int liftAlone = 1;
        int liftingTools = 0;
        int totalMoveTime = 0;

        try(Connection conn = dbConn.establishConnectionToDatabase()) {

            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);

                for(OrderLine orderLine : orderLineList) {
                    stmt.setString(1, orderLine.getWareNumber());
                    stmt.setString(2, orderLine.getIndividual());
                    stmt.setString(3, orderLine.getIndividualNumber());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()) {
                        if (rs.getString(1).equals("false"))
                            liftAlone = 0;

                        if (rs.getString(2).equals("true"))
                            liftingTools = 1;

                        totalMoveTime += Integer.valueOf(rs.getString(3));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        information.add(liftAlone);
        information.add(liftingTools);
        information.add(totalMoveTime);

        return information;
    }

    public static ArrayList<Ware> getWares(){
        return DatabaseSetup.dbExport.exportWares();
    }
}
