package dk.aau.cs.ds308e18.io;
import java.sql.*;
import java.util.ArrayList;

import dk.aau.cs.ds308e18.model.Order;

public class DataManagement {

    /*
    Establish connection to the Local Database.
    This is needed for any form of Database management such as "SELECT" and "INSERT".
    For every method, it's needed to call this function for the SQL statements to work.

    The Local Database has to be up and running for this function to work.
    Without this function, the rest in the class won't work.
    */
    private Connection establishConnectionToDatabase() {
        String host = "jdbc:mysql://localhost:3306/vibocold_db";
        String uName = "root";
        String uPass = "";
        try {
            return DriverManager.getConnection(host, uName, uPass);
        } catch (SQLException e) {
            System.out.println("Couldn't establish a connection.\n" +
                    "Try restarting the Database");
            e.printStackTrace();
        }
        return null;
    }

    // Run this function to make sure a database and corresponding tables are created.
    private void databaseSetup() {
        createDatabase();
        createOrderTable();
    }

    /*
    Create a database called "vibocold_db".
    If it already exists, it'll print a response.
    */
    private void createDatabase() {
        String host = "jdbc:mysql://localhost:3306/";
        String uName = "root";
        String uPass = "";
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE vibocold_db");
        } catch(SQLException e) {
            System.out.println("The database already exists.");
        }
    }

    /*
    Create a table called "orders".
    If it already exists, it'll print a response.
    */
    private void createOrderTable() {
        Connection conn = establishConnectionToDatabase();
        try {
            if (conn != null) {
                String sql = "CREATE TABLE orders (" +
                        "plukrute INT, " +
                        "order1 VARCHAR(255), " +
                        "returordreReference VARCHAR(255), " +
                        "ekspeditionsStatus VARCHAR(255), " +
                        "navn VARCHAR(255), " +
                        "modtagelsesDato VARCHAR(255), " +
                        "gadeNavn VARCHAR(255), " +
                        "postNummer INT, " +
                        "receipt INT, " +
                        "afhentning VARCHAR(255), " +
                        "afhentningsDato VARCHAR(255), " +
                        "leveringsUge INT, " +
                        "lagerSted VARCHAR(255), " +
                        "ordreKategori VARCHAR(255), " +
                        "fleetOwner VARCHAR(255), " +
                        "udskrevet VARCHAR(255), " +
                        "rute VARCHAR(255), " +
                        "FV VARCHAR(255), " +
                        "projekt VARCHAR(255))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("The table already exists.");
        }
    }

    /*
    Insert an order into the order table.
    */
    private PreparedStatement createOrder(Order order) {
        try {
            Connection conn = establishConnectionToDatabase();
            if(conn != null) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (" +
                        "plukrute, order1, returordreReference, ekspeditionsStatus, navn, modtagelsesDato, gadeNavn, " +
                        "postNummer, receipt, afhentning, afhentningsDato, leveringsUge, lagerSted, ordreKategori, " +
                        "fleetOwner, udskrevet, rute, FV, projekt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                //TODO Fix det her med en hjælpe funktion
                stmt.setString(1, String.valueOf(order.plukrute));
                stmt.setString(2, String.valueOf(order.order));
                stmt.setString(3, String.valueOf(order.returordreReference));
                stmt.setString(4, String.valueOf(order.ekspeditionsStatus));
                stmt.setString(5, String.valueOf(order.navn));
                stmt.setString(6, String.valueOf(order.modtagelsesDato));
                stmt.setString(7, String.valueOf(order.gadeNavn));
                stmt.setString(8, String.valueOf(order.postNummer));
                stmt.setString(9, String.valueOf(order.receipt));
                stmt.setString(10, String.valueOf(order.afhentning));
                stmt.setString(11, String.valueOf(order.afhentningsDato));
                stmt.setString(12, String.valueOf(order.leveringsUge));
                stmt.setString(13, String.valueOf(order.lagerSted));
                stmt.setString(14, String.valueOf(order.ordreKategori));
                stmt.setString(15, String.valueOf(order.fleetOwner));
                stmt.setString(16, String.valueOf(order.udskrevet));
                stmt.setString(17, String.valueOf(order.rute));
                stmt.setString(18, String.valueOf(order.FV));
                stmt.setString(19, String.valueOf(order.projekt));

                return stmt;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    ....
    */
    private void importOrders() {
        ReadFile readFileObject = new ReadFile();
        ArrayList<Order> orderList = readFileObject.orderFile();
        Connection conn = establishConnectionToDatabase();

        try {
            if (conn != null) {
                for(Order order : orderList) {
                    PreparedStatement stmt;
                    stmt = createOrder(order);

                    if (stmt != null)
                        stmt.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Export everything from the order table.
    Print them in the terminal
    */
    private void exportOrders() {
        Connection conn = establishConnectionToDatabase();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet orders = stmt.executeQuery("SELECT * FROM orders");

                while (orders.next()) {
                    System.out.println(orders.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Export everything from the tour table.
    Print them in the terminal
    */
    private void exportTours() {
        Connection conn = establishConnectionToDatabase();
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

    public static void main(String[] args) {
        DataManagement db = new DataManagement();
        db.databaseSetup();
        db.importOrders();
    }
}
