package dk.aau.cs.ds308e18.function.management;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;
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

        //Assign the tourID created by the database to the tour object.
        tour.setTourId(assignTourID());

        //Assign the tourID of the tour to all orders under the tour.
        updateOrderTourID(tour);
    }

    /*
    Replace a tour in the database with this tour (both should have the same tourID)
    */
    public static void overrideTour(Tour tour) {
        //TODO: hjælp
    }

    /*
    Select and return the last tourID in the tour table given by createTour.
    (createTour automatically assigns a tourID to a tour but this number is not given to the tour object.
    That is what this method makes sure happens.)
    */
    private static int assignTourID() {
        DatabaseConnection dbConn = new DatabaseConnection();
        String sql = "SELECT tourID FROM tours ORDER BY tourID DESC LIMIT 1";

        try (Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next())
                    return rs.getInt(1);
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
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {

                // If there are any orders on the tour - update their tourID
                if(!(tour.getOrders().isEmpty())) {
                    ArrayList<Order> orderList = tour.getOrders();
                    for(Order order : orderList) {
                        updateTourID(tour.getTourID(), order.getOrderID());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Helping method.
    Excutes the actul update for each order passed from the previous method.
    */
    private static void updateTourID(int tourID, int orderID) {
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
    Remove tour (from database).
    Sets all Order's orderIDs on the tour to 0 and delete the tour from the database.
    */
    public static void removeTour(Tour tour){
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "UPDATE orders SET tourID = 0 WHERE orderID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                // If there are any orders in the tour - set their tourID to 0
                if(!(tour.getOrders().isEmpty())) {
                    for(Order order : tour.getOrders()) {
                        updateTourID(0, order.getOrderID());
                    }
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static ArrayList<Tour> getTours(){
        return Main.dbExport.exportTours();
    }

    /*
    Execute removeTour on all tours in the database
    */
    public static void deleteAllTours() {
        for (Tour tour : Main.dbExport.exportTours()) {
            removeTour(tour);
        }
    }
}
