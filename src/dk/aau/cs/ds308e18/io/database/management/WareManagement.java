package dk.aau.cs.ds308e18.io.database.management;

import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.model.Ware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WareManagement {

    /*
    ....
    */
    public void createWare(Ware ware) {
        DatabaseConnection dbConn = new DatabaseConnection();
        String sql = "INSERT INTO wares (supplier, wareNumber, height, depth, grossHeight, " +
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
}
