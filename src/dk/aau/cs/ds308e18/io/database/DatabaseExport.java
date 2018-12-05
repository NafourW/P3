package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import dk.aau.cs.ds308e18.model.Ware;

import java.sql.*;
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
    */
    public ArrayList<Order> exportOrders() {
        ArrayList<Order> orderList = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orders = stmt.executeQuery("SELECT * FROM orders");

                orderList = createOrderObject(orders);
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
                    Ware ware = createWareFromResultSet(wares);
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

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet tours = stmt.executeQuery("SELECT * FROM tours");

                while (tours.next()) {
                    Tour tour = createTourFromResultSet(tours);

                    // Find all orders with that tourID and put them on the tour
                    ArrayList<Order> ordersOnTour = ordersOnTour(tour);
                    for(Order order : ordersOnTour) {
                        tour.addOrder(order);
                    }

                    tourList.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tourList;
    }

    /*
    Find all orders on a specific tour. Return a list of those orders.
    */
    private ArrayList<Order> ordersOnTour(Tour tour) {
        ArrayList<Order> ordersOnTourList = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM orders WHERE tourID = ?";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, tour.getTourID());
                
                ResultSet ordersOnTour = stmt.executeQuery();

                ordersOnTourList = createOrderObject(ordersOnTour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordersOnTourList;
    }

    /*
    Create a list of orders based on a ResultSet from a SQL Query.
    Return them.
    */
    private ArrayList<Order> createOrderObject(ResultSet orders) {
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            // As long as there is a "next row" in the table, create an order based on that row
            while (orders.next()) {
                Order order = createOrderFromResultSet(orders);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    /*
    Export all orders that are unassigned to a tour (tourID = 0).
    */
    public ArrayList<Order> exportUnassignedOrders() {
        ArrayList<Order> orderList = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orders = stmt.executeQuery("SELECT * FROM orders WHERE tourID = 0");

                orderList = createOrderObject(orders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    private Tour createTourFromResultSet(ResultSet resultSet) {
        Tour tour = new Tour();

        try {
            tour.setTourId  (resultSet.getInt   (1));
            tour.setTourDate(resultSet.getDate  (2).toLocalDate());
            tour.setRegion  (resultSet.getString(5));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tour;
    }

    private Order createOrderFromResultSet(ResultSet resultSet) {
        Order order = new Order();

        try {
            order.setOrderID         (resultSet.getInt    (1));
            order.setTourID          (resultSet.getInt    (2));
            order.setPluckRoute      (resultSet.getInt    (3));
            order.setID              (resultSet.getString (4));
            order.setOrderReference  (resultSet.getString (5));
            order.setExpeditionStatus(resultSet.getString (6));
            order.setCustomerName    (resultSet.getString (7));
            order.setDate            (resultSet.getDate   (8).toLocalDate());
            order.setAddress         (resultSet.getString (9));
            order.setZipCode         (resultSet.getInt    (10));
            order.setReceipt         (resultSet.getInt    (11));
            order.setPickup          (resultSet.getBoolean(12));
            order.setWarehouse       (resultSet.getString (13));
            order.setCategory        (resultSet.getString (14));
            order.setFleetOwner      (resultSet.getString (15));
            order.setPrinted         (resultSet.getBoolean(16));
            order.setRegion          (resultSet.getString (17));
            order.setFV              (resultSet.getBoolean(18));
            order.setProject         (resultSet.getString (19));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    private Ware createWareFromResultSet(ResultSet resultSet) {
        Ware ware = new Ware();

        try {
            ware.setSupplier    (resultSet.getString (2));
            ware.setWareNumber  (resultSet.getString (3));
            ware.setHeight      (resultSet.getInt    (4));
            ware.setDepth       (resultSet.getInt    (5));
            ware.setGrossHeight (resultSet.getInt    (6));
            ware.setGrossDepth  (resultSet.getInt    (7));
            ware.setGrossWidth  (resultSet.getInt    (8));
            ware.setWidth       (resultSet.getInt    (9));
            ware.setWareName    (resultSet.getString (10));
            ware.setSearchName  (resultSet.getString (11));
            ware.setWareGroup   (resultSet.getInt    (12));
            ware.setWareType    (resultSet.getString (13));
            ware.setLiftAlone   (resultSet.getBoolean(14));
            ware.setLiftingTools(resultSet.getBoolean(15));
            ware.setMoveTime    (resultSet.getFloat  (16));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return ware;
    }
}
