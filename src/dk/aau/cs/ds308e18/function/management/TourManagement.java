package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.model.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourManagement {

    /*
    Insert a tour into the database.
    */
    public static void createTour(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();
        String sql = "INSERT INTO tours (tourDate, packingDate, id, region, regionDetail, " +
                "driver, status, consignor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, String.valueOf(tour.getTourDate()));
                stmt.setString(2, String.valueOf(tour.getPackingDate()));
                stmt.setString(3, String.valueOf(tour.getID()));
                stmt.setString(4, String.valueOf(tour.getRegion()));
                stmt.setString(5, String.valueOf(tour.getRegionDetail()));
                stmt.setString(6, String.valueOf(tour.getDriver()));
                stmt.setString(7, String.valueOf(tour.getStatus()));
                stmt.setString(8, String.valueOf(tour.getConsignor()));

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Tour> getTours(){
        return Main.dbExport.exportTours();
    }
}
