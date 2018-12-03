package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import dk.aau.cs.ds308e18.model.Ware;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseExport {

    /*
    ....
    */
    public ArrayList<String> exportRegionNames() {
        ArrayList<String> regionList = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet regions = stmt.executeQuery("SELECT regionName FROM regions");

                while (regions.next()) {
                    regionList.add(regions.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regionList;
    }

    /*
    Export everything from the order table.
    Print them in the terminal
    */
    public ArrayList<Order> exportOrders() {
        ArrayList<Order> orderList = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orders = stmt.executeQuery("SELECT * FROM orders");

                // As long as there is a "next row" in the table, create an order based on that row
                while (orders.next()) {
                    //TODO Hjælp den her funktion
                    Order order = new Order(orders.getInt(3), orders.getInt(4),
                            orders.getString(5), orders.getString(6),
                            orders.getString(7), orders.getDate(8).toLocalDate(),
                            orders.getString(9), orders.getInt(10),
                            orders.getInt(11), orders.getBoolean(12),
                            orders.getString(13), orders.getString(14),
                            orders.getString(15), orders.getBoolean(16),
                            orders.getString(17), orders.getBoolean(18),
                            orders.getString(19));
                    order.setOrderID(orders.getInt(1));
                    order.setTourID(orders.getInt(2));
                    orderList.add(order);
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public ArrayList<Ware> exportWares() {
        ArrayList<Ware> wareList = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet wares = stmt.executeQuery("SELECT * FROM warelist");

                // As long as there is a "next row" in the table, create an order based on that row
                while (wares.next()) {
                    //TODO Hjælp den her funktion
                    Ware ware = new Ware(wares.getString(1), wares.getString(2),
                            wares.getInt(3), wares.getInt(4), wares.getInt(5),
                            wares.getInt(6), wares.getInt(7), wares.getInt(8),
                            wares.getString(9), wares.getString(10), wares.getInt(11),
                            wares.getString(12), wares.getBoolean(13), wares.getBoolean(14),
                            wares.getFloat(15));
                    wareList.add(ware);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wareList;
    }

    /*
    Export everything from the tour table.
    Print them in the terminal
    */
    public ArrayList<Tour> exportTours() {
        ArrayList<Tour> tourList = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet tours = stmt.executeQuery("SELECT * FROM tours");

                while (tours.next()) {
                    System.out.println(tours.getString(1));
                    Tour tour = new Tour();
                    tourList.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tourList;
    }
}
