package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
import dk.aau.cs.ds308e18.io.database.Database;
import dk.aau.cs.ds308e18.io.database.DatabaseExport;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;

import java.sql.*;
import java.util.ArrayList;

public class TourManagement {
    /*
    Insert a tour into the database.
    */
    public static void createTour(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();

        try (Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "INSERT INTO tours (tourDate, packingDate, id, region, " +
                        "driver, status, consignor, tourTime) VALUES (" + getTourValuesString(tour) + ")";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Assign the tourID created by the database to the tour object.
        tour.setTourID(assignTourID());

        //Assign the tourID of the tour to all orders under the tour.
        updateOrderTourID(tour);
    }

    /*
    Returns a string with the tour's values, formatted for an SQL statement
    */
    private static String getTourValuesString(Tour tour) {
        StringBuilder sb = new StringBuilder();

        sb.append("'").append(tour.getTourDate())                .append("', ")
                .append("'").append(tour.getPackingDate())       .append("', ")
                .append(tour.getID())                            .append(", ")
                .append("'").append(tour.getRegion())            .append("', ")
                .append("'").append(tour.getDriver())            .append("', ")
                .append("'").append(tour.getStatus())            .append("', ")
                .append("'").append(tour.getConsignor())         .append("', ")
                .append(tour.getTourTime());

        return sb.toString();
    }

    /*
    Override a tour in the database with a given tour (both should have the same tourID).
    */
    public static void overrideTour(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();
        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if(conn != null) {
                String sql = "UPDATE tours SET tourDate = ?, packingDate = ?, id = ?, region = ?, " +
                        "driver = ?, status = ?, consignor = ?, tourTime = ? WHERE tourID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, String.valueOf(tour.getTourDate()));
                stmt.setString(2, String.valueOf(tour.getPackingDate()));
                stmt.setInt(3, tour.getID());
                stmt.setString(4, tour.getRegion());
                stmt.setString(5, tour.getDriver());
                stmt.setString(6, String.valueOf(tour.getStatus()));
                stmt.setBoolean(7, tour.getConsignor());
                stmt.setInt(8, tour.getTourTime());
                stmt.setInt(9, tour.getTourID());

                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        //Assign the tourID of the tour to all orders under the tour.
        updateOrderTourID(tour);
    }

    /*
    Select and return the last tourID in the tour table given by createTour.
    (createTour automatically assigns a tourID to a tour but this number is not given to the tour object.
    That is what this method makes sure happens.)
    */
    private static int assignTourID() {
        DatabaseConnection dbConn = new DatabaseConnection();

        try (Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT tourID FROM tours ORDER BY tourID DESC LIMIT 1";
                Statement stmt = conn.createStatement();

                ResultSet resultSet = stmt.executeQuery(sql);

                // Return the tourID of the last tour in the Database.
                while(resultSet.next())
                    return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
    Update tourID for orders on the given tour to match them.
    */
    private static void updateOrderTourID(Tour tour) {

        // If there are any orders on the tour - update their tourID
        if(!(tour.getOrders().isEmpty())) {

            // Find orders on tour - set the tourID of the order to the tour's tourID
            ArrayList<Order> orderList = tour.getOrders();
            for(Order order : orderList) {
                OrderManagement.setTourID(tour.getTourID(), order.getOrderID());
            }
        }
    }

    /*
    Remove tour (from database).
    Sets all Order's orderIDs on the tour to 0 and delete the tour from the database.
    */
    public static void removeTour(Tour tour){

        // If there are any orders in the tour - set their tourID to 0
        if(!(tour.getOrders().isEmpty())) {
            for(Order order : tour.getOrders()) {
                OrderManagement.setTourID(0, order.getOrderID());
            }
        }

        // Delete the tour from the database
        deleteTourFromDatabase(tour);
    }

    /*
    Delete the specific tour from the database.
    */
    private static void deleteTourFromDatabase(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "DELETE FROM tours WHERE tourID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, tour.getTourID());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Create tour object from a given tourID.
    */
    public static Tour getTourFromTourID(int tourID) {
        return DatabaseExport.getTourFromTourID(tourID);
    }

    public static ArrayList<Tour> getTours(){
        return Database.dbExport.exportTours();
    }

    /*
    Execute removeTour on all tours in the database
    */
    public static void deleteAllTours() {

        // Export all tours and call removeTour on them.
        for (Tour tour : Database.dbExport.exportTours()) {
            removeTour(tour);
        }
    }
}
