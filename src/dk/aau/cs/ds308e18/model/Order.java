package dk.aau.cs.ds308e18.model;

import com.graphhopper.util.shapes.GHPoint;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.io.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

import static dk.aau.cs.ds308e18.Main.gps;

public class Order {
    public enum orderCategory{
        delivery,
        pickUp,
        newPurchase,
        service,
        exchange,
        spareParts,
    }

    private int receipt;
    private int pluckRoute;
    private String orderReference;
    private String warehouse;
    private String fleetOwner;
    private String project;
    private orderCategory category;

    private String customerName;
    private String region;
    private String address;
    private int zipCode;
    private LocalDate date;
    private GHPoint latLon;

    private boolean printed;
    private String expeditionStatus;

    private boolean pickup;
    private boolean FV;

    // Used in our database to see which tours,
    // orders and orderlines belong to each other.
    private int tourID;
    private int orderID;

    //Used for Vibocold's order IDs.
    private String id;

    private int totalTime;
    private boolean totalLiftAlone;
    private boolean totalLiftingTools;

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isTotalLiftAlone() {
        return totalLiftAlone;
    }

    public void setTotalLiftAlone(boolean totalLiftAlone) {
        this.totalLiftAlone = totalLiftAlone;
    }

    public boolean isTotalLiftingTools() {
        return totalLiftingTools;
    }

    public void setTotalLiftingTools(boolean totalLiftingTools) {
        this.totalLiftingTools = totalLiftingTools;
    }

    ArrayList<OrderLine> orderLines;

    public Order() {
        id = "";
        receipt = 0;
        pluckRoute = 0;
        orderReference = "";
        warehouse = "";
        fleetOwner = "";
        project = "";
        category = orderCategory.delivery;

        customerName = "";
        region = "";
        address = "";

        zipCode = 0;
        date = LocalDate.now();
        latLon = new GHPoint(0,0);

        printed = false;
        expeditionStatus = "";

        pickup = false;
        FV = false;

        orderID = 0;
        tourID = 0;

        orderLines = new ArrayList<>();
    }

    /*
    Used in our database to see which order an orderline belongs to.
    */
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /*
    Used in our database to see which tour an order belongs to.
    */
    public int getTourID() {return tourID;}

    public void setTourID(int tourID) {this.tourID = tourID;}

    /*
    Used for Vibocold's tour IDs.
    */
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public int getReceipt() {
        return receipt;
    }

    public void setReceipt(int receipt) {
        this.receipt = receipt;
    }

    public int getPluckRoute() {
        return pluckRoute;
    }

    public void setPluckRoute(int pluckRoute) {
        this.pluckRoute = pluckRoute;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getFleetOwner() {
        return fleetOwner;
    }

    public void setFleetOwner(String fleetOwner) {
        this.fleetOwner = fleetOwner;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public orderCategory getCategory() {
        return category;
    }

    public void setCategory(orderCategory category) {
        this.category = category;
    }

    public String getLocalizedCategoryString() {
        return Main.gui.getLocalString("value_order_category_" + category.toString());
    }

    public void setCategory(String category) {
        switch (category.toLowerCase()) {
            case "levering":
            case "delivery":
                this.category = orderCategory.delivery;
                break;
            case "afhentning":
            case "pickup":
                this.category = orderCategory.pickUp;
                break;
            case "nykøb":
            case "newpurchase":
                this.category = orderCategory.newPurchase;
                break;
            case "ombytning":
            case "exchange":
                this.category = orderCategory.exchange;
                break;
            case "reservedele":
            case "spareparts":
                this.category = orderCategory.spareParts;
                break;
            case "service":
                this.category = orderCategory.service;
                break;
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != this.address) {
            this.address = address;
        }
    }

    public int getOrderLineAmount() {
        return orderLines.size();
    }

    public void requestLatLonFromAddress() {
        this.latLon = gps.GeocodeAddress(address);
        saveLatLonInDataBase(address);
    }

    public void setLatLon(GHPoint latLon) {
        this.latLon = latLon;
        saveLatLonInDataBase(address);
    }

    //TODO This method shouldn't be here. Database related functions should be in IO or MANAGEMENT.
    public void saveLatLonInDataBase(String address) {
        String sql = "INSERT INTO addresses (address, latitude, longitude) VALUES (?, ?, ?)";
        DatabaseConnection dbConn = new DatabaseConnection();
        try (Connection conn = dbConn.establishConnectionToDatabase()) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, String.valueOf(address));
                stmt.setString(2, String.valueOf(this.latLon.lat));
                stmt.setString(3, String.valueOf(this.latLon.lon));


                stmt.executeUpdate();
            }
        }
        catch (MySQLIntegrityConstraintViolationException e) { // Hvis adressen eksistere i DataBasen fanges exception skyldet af at databasen er unique.
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWeekNumber() {
        WeekFields weekFields = WeekFields.ISO;
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        return weekNumber;
    }

    public boolean isPrinted() {
        return printed;
    }

    public void setPrinted(boolean printed) {
        this.printed = printed;
    }

    public String getExpeditionStatus() {
        return expeditionStatus;
    }

    public void setExpeditionStatus(String expeditionStatus) {
        this.expeditionStatus = expeditionStatus;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public boolean isFV() {
        return FV;
    }

    public void setFV(boolean FV) {
        this.FV = FV;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLine orderLine){
        orderLines.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
    }

    public GHPoint getLatLon() {
        return latLon;
    }

    @Override
    public String toString() {
        return id + "," + receipt + "," + pluckRoute + "," + orderReference + "," +
                warehouse + "," + fleetOwner + "," + project + "," + category + "," +
                customerName + "," + region + "," + address + "," + zipCode + "," + date + "," +
                printed + "," + expeditionStatus + "," + pickup + "," + FV;
    }
}