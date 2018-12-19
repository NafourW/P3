package dk.aau.cs.ds308e18.io.database;

import dk.aau.cs.ds308e18.function.management.OrderLineManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Tour;
import dk.aau.cs.ds308e18.model.Ware;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseExport {

    /*
    Export regions from the region table.
    Return the regions as an arraylist of Strings.
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
    Export orders from the order table.
    extraParameters is added at the end of the query to select orders with a specification.
    Used by other export order methods.
    */
    private ArrayList<Order> exportOrders(String extraParameters) {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<Order> orderList = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM orders " + extraParameters;

                ResultSet orders = stmt.executeQuery(sql);

                // Create an order based on each row in the result set.
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
    If region or date are not null, the orders get filtered and the method
    returns orders with the given region and/or date.
    */
    public ArrayList<Order> exportUnassignedOrdersFiltered(String region, String date) {
        String qWhere  = (region != null || date != null) ? "WHERE tourID = 0 AND " : "";
        String qAnd    = (region != null && date != null) ? " AND " : "";

        String qRegion = (region != null) ? "route = "     + "'" + region + "'" : "";
        String qDate   = (date != null)   ? "orderDate = " + "'" + date   + "'" : "";

        return exportOrders(qWhere + qRegion + qAnd + qDate);
    }

    /*
    Export orderlines from the Database.
    Return them in an arraylist of orderlines.
    */
    public ArrayList<OrderLine> exportOrderLines() {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<OrderLine> orderLineList = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orderLines = stmt.executeQuery("SELECT * FROM orderlines");

                // Create an orderline based on each row in the result set.
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

    /*
    Export wares from the Database.
    Return them in an arraylist of wares.
    */
    public ArrayList<Ware> exportWares() {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<Ware> wareList = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet wares = stmt.executeQuery("SELECT * FROM warelist");

                // Create an ware based on each row in the result set.
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
    Export tours from the Database.
    Place orders on all the tours.
    Return them in an arraylist of tours.
    */
    public ArrayList<Tour> exportTours() {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<Tour> tourList = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet tours = stmt.executeQuery("SELECT * FROM tours");

                // Create a tour based on each row in the result set.
                while (tours.next()) {
                    Tour tour = createTourFromResultSet(tours);

                    // Find all orders with that tourID and put them on the tour object
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
    Create tour object from a given tourID.
    */
    public static Tour getTourFromTourID(int tourID){
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM tours WHERE tourID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, tourID);

                ResultSet resultSet = stmt.executeQuery();

                /*
                The resultSet starts from 0 which contains nothing.
                That's why rs.next() is called before creating the tour.
                */
                // Create a tour based on each row in the result set.
                while(resultSet.next()) {
                    Tour tour = createTourFromResultSet(resultSet);

                    // Find all orders with that tourID and put them on the tour
                    ArrayList<Order> ordersOnTour = ordersOnTour(tour);
                    for(Order order : ordersOnTour) {
                        tour.addOrder(order);
                    }

                    return tour;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
    Find all orders on a specific tour. Return a list of those orders.
    */
    private static ArrayList<Order> ordersOnTour(Tour tour) {
        DatabaseConnection dbConn = new DatabaseConnection();
        ArrayList<Order> ordersOnTourList = new ArrayList<>();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM orders WHERE tourID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, tour.getTourID());
                
                ResultSet resultSet = stmt.executeQuery();

                // As long as there is a "next row" in the table, create an order based on that row
                while (resultSet.next()) {
                    Order order = DatabaseExport.createOrderFromResultSet(resultSet);
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
    private static Tour createTourFromResultSet(ResultSet resultSet) {
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
            tour.setTourTime    (resultSet.getInt    (9));
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
    private static Order createOrderFromResultSet(ResultSet resultSet) {
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

            order.setLatLon           (exportLatLonFromAddress(order.getAddress()));

            // Get orderlines from a given order.
            ArrayList<OrderLine> orderLines = OrderLineManagement.getOrderLinesOnOrder(order);
            for (OrderLine orderLine : orderLines)

                // Add orderline to order object.
                order.addOrderLine(orderLine);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    /*
    Export latitude and longitude from a given address from Address table.
    */
    private static double[] exportLatLonFromAddress(String address) {
        double[] latLon = new double[2];
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM addresses WHERE address = ?";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, address);

                ResultSet resultSet = stmt.executeQuery();

                // Put the exported values in the array.
                while (resultSet.next()) {
                    latLon[0] = resultSet.getDouble(2);
                    latLon[1] = resultSet.getDouble(3);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return latLon;
    }

    /*
    Create a ware based on a ResultSet from a SQL Query.
    Return it.
    */
    private static Ware createWareFromResultSet(ResultSet resultSet) {
        Ware ware = new Ware();

        try {
            ware.setSupplier    (resultSet.getString (1));
            ware.setWareNumber  (resultSet.getString (2));
            ware.setHeight      (resultSet.getInt    (3));
            ware.setDepth       (resultSet.getInt    (4));
            ware.setGrossHeight (resultSet.getInt    (5));
            ware.setGrossDepth  (resultSet.getInt    (6));
            ware.setGrossWidth  (resultSet.getInt    (7));
            ware.setWidth       (resultSet.getInt    (8));
            ware.setWareName    (resultSet.getString (9));
            ware.setSearchName  (resultSet.getString (10));
            ware.setWareGroup   (resultSet.getInt    (11));
            ware.setWareType    (resultSet.getString (12));
            ware.setLiftAlone   (resultSet.getBoolean(13));
            ware.setLiftingTools(resultSet.getBoolean(14));
            ware.setMoveTime    (resultSet.getFloat  (15));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return ware;
    }

    /*
    Create an orderline based on a ResultSet from a SQL Query.
    Return it.
    */
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

    /*
    Return amount of rows by a given name of a table.
    */
    public static int getAmount(String tableName) {
        DatabaseConnection dbConn = new DatabaseConnection();

        try(Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                String sql = "SELECT COUNT(*) FROM " + tableName;
                PreparedStatement stmt = conn.prepareStatement(sql);

                ResultSet resultSet = stmt.executeQuery();

                // Return the number given by the SQL Query.
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
