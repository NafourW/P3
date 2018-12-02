package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.model.Order;

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
                    Order order = new Order(orders.getInt(1), orders.getInt(2),
                            orders.getString(3), orders.getString(4),
                            orders.getString(5), orders.getDate(6).toLocalDate(),
                            orders.getString(7), orders.getInt(8),
                            orders.getInt(9), orders.getBoolean(10),
                            orders.getString(11), orders.getString(12),
                            orders.getString(13), orders.getBoolean(14),
                            orders.getString(15), orders.getBoolean(16),
                            orders.getString(17));
                    orderList.add(order);
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    /*
    Export everything from the tour table.
    Print them in the terminal
    */
    public void exportTours() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet tours = stmt.executeQuery("SELECT * FROM tours");

                while (tours.next()) {
                    System.out.println(tours.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
