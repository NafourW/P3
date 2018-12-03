package dk.aau.cs.ds308e18.io.database.management;

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
    public void createTour(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();
        String sql = "INSERT INTO tours (tourDate, packingDate, id, region, regionDetail, " +
                "driver, status, consignor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tour.getTourDate());
                stmt.setString(2, tour.getPackingDate());
                stmt.setString(3, tour.getID().toString());
                stmt.setString(4, tour.getRegion());
                stmt.setString(5, tour.getRegionDetail());
                stmt.setString(6, tour.getDriver());
                stmt.setString(7, tour.getStatus());
                stmt.setString(8, tour.getConsignor().toString());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Tour> getTours(){
        return Main.dbExport.exportTours();
    }
}
