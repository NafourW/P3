package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.function.management.OrderLineManagement;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Tour;
import dk.aau.cs.ds308e18.model.Ware;

import javax.xml.crypto.Data;
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
    Export orders from the order table
    extraParameters is added at the end of the query
    */
    public ArrayList<Order> exportOrders(String extraParameters) {
        ArrayList<Order> orderList = new ArrayList<>();

        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM orders " + extraParameters;
                //System.out.println(sql);
                ResultSet orders = stmt.executeQuery(sql);

                // As long as there is a "next row" in the table, create an order based on that row
                while (orders.next()) {
                    Order order = createOrderFromResultSet(orders);
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    /*
    Export everything from the order table.
    */
    public ArrayList<Order> exportAllOrders() {
        return exportOrders("");
    }

    /*
    Export all orders that are unassigned to a tour (tourID = 0).
    */
    public ArrayList<Order> exportUnassignedOrders() {
        return exportOrders("WHERE tourID = 0");
    }

    /*
    Export all orders that are unassigned to a tour (tourID = 0).
    If region or date are not null, the orders get filtered.
    */
    public ArrayList<Order> exportUnassignedOrdersFiltered(String region, String date) {
        String qWhere  = (region != null || date != null) ? "WHERE tourID = 0 AND " : "";
        String qAnd    = (region != null && date != null) ? " AND " : "";

        String qRegion = (region != null) ? "route = "     + "'" + region + "'" : "";
        String qDate   = (date != null)   ? "orderDate = " + "'" + date   + "'" : "";

        return exportOrders(qWhere + qRegion + qAnd + qDate);
    }

    public ArrayList<OrderLine> exportOrderLines() {
        ArrayList<OrderLine> orderLineList = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orderLines = stmt.executeQuery("SELECT * FROM orderlines");

                // As long as there is a "next row" in the table, create an order based on that row
                while (orderLines.next()) {
                    OrderLine orderLine = createOrderLineFromResultSet(orderLines);
                    orderLineList.add(orderLine);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderLineList;


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
    public static ArrayList<Order> ordersOnTour(Tour tour) {
        ArrayList<Order> ordersOnTourList = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM orders WHERE tourID = ?";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, tour.getTourID());
                
                ResultSet ordersOnTour = stmt.executeQuery();

                // As long as there is a "next row" in the table, create an order based on that row
                while (ordersOnTour.next()) {
                    Order order = DatabaseExport.createOrderFromResultSet(ordersOnTour);
                    ordersOnTourList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordersOnTourList;
    }

    /*
    Create a tour based on a ResultSet from a SQL Query.
    Return it.
    */
    public static Tour createTourFromResultSet(ResultSet resultSet) {
        Tour tour = new Tour();

        try {
            tour.setTourID      (resultSet.getInt    (1));
            tour.setTourDate    (resultSet.getDate   (2).toLocalDate());
            tour.setPackingDate (resultSet.getDate   (3).toLocalDate());
            tour.setID          (resultSet.getInt    (4));
            tour.setRegion      (resultSet.getString (5));
            tour.setDriver      (resultSet.getString (6));
            tour.setStatus      (Tour.tourStatus.valueOf(resultSet.getString (7)));
            tour.setConsignor   (resultSet.getBoolean(8));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tour;
    }

    /*
    Create an order based on a ResultSet from a SQL Query.
    Return it.
    */
    public static Order createOrderFromResultSet(ResultSet resultSet) {
        Order order = new Order();

        try {
            order.setOrderID          (resultSet.getInt    (1));
            order.setTourID           (resultSet.getInt    (2));
            order.setPluckRoute       (resultSet.getInt    (3));
            order.setID               (resultSet.getString (4));
            order.setOrderReference   (resultSet.getString (5));
            order.setExpeditionStatus (resultSet.getString (6));
            order.setCustomerName     (resultSet.getString (7));
            order.setDate             (resultSet.getDate   (8).toLocalDate());
            order.setAddress          (resultSet.getString (9));
            //TODO: get latLon from database
            //order.setLatLon();
            order.setZipCode          (resultSet.getInt    (10));
            order.setReceipt          (resultSet.getInt    (11));
            order.setPickup           (resultSet.getBoolean(12));
            order.setWarehouse        (resultSet.getString (13));
            order.setCategory         (resultSet.getString (14));
            order.setFleetOwner       (resultSet.getString (15));
            order.setPrinted          (resultSet.getBoolean(16));
            order.setRegion           (resultSet.getString (17));
            order.setFV               (resultSet.getBoolean(18));
            order.setProject          (resultSet.getString (19));
            order.setTotalLiftAlone   (resultSet.getBoolean(20));
            order.setTotalLiftingTools(resultSet.getBoolean(21));
            order.setTotalTime        (resultSet.getInt    (22));

            ArrayList<OrderLine> orderLines = OrderLineManagement.getOrderLinesOnOrder(order);
            for (OrderLine orderLine : orderLines)
                order.addOrderLine(orderLine);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    /*
    Create a ware based on a ResultSet from a SQL Query.
    Return it.
    */
    public static Ware createWareFromResultSet(ResultSet resultSet) {
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

    public static OrderLine createOrderLineFromResultSet(ResultSet resultSet) {
        OrderLine orderLine = new OrderLine();

        try {
            orderLine.setWareNumber      (resultSet.getString (3));
            orderLine.setWareName        (resultSet.getString (4));
            orderLine.setLabels          (resultSet.getInt    (5));
            orderLine.setDelivered       (resultSet.getInt    (6));
            orderLine.setIndividual      (resultSet.getString (7));
            orderLine.setPreparing       (resultSet.getBoolean(8));
            orderLine.setIndividualNumber(resultSet.getString (9));
            orderLine.setModel           (resultSet.getString (10));
            orderLine.setFullName        (resultSet.getString (11));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return orderLine;
    }

    public static int getAmount(String parameter) {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT COUNT(*) FROM " + parameter;
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery();

                while(resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
