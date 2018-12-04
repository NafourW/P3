package dk.aau.cs.ds308e18.model;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class Order {
    private String id;
    private int receipt;
    private int pluckRoute;
    private String orderReference;
    private String warehouse;
    private String fleetOwner;
    private String project;
    private String category;

    private String customerName;
    private String region;
    private String address;
    private int zipCode;
    private LocalDate date;

    private boolean printed;
    private String expeditionStatus;

    private boolean pickup;
    private boolean FV;

    private int orderID;
    private int tourID;

    ArrayList<OrderLine> orderLines;

    public Order() {
        id = null;
        receipt = 0;
        pluckRoute = 0;
        orderReference = "";
        warehouse = "";
        fleetOwner = "";
        project = "";
        category = "";

        customerName = "";
        region = "";
        address = "";
        zipCode = 0;
        date = LocalDate.now();

        printed = false;
        expeditionStatus = "";

        pickup = false;
        FV = false;

        orderID = 0;
        tourID = 0;

        orderLines = new ArrayList<>();
    }

    public Order(int pluckRoute, String id, String orderReference, String expeditionStatus, String customerName,
                 LocalDate date, String address, int zipCode, int receipt, boolean pickup,
                 String warehouse, String category, String fleetOwner,
                 boolean printed, String region, boolean FV, String project) {
        this.id = id;
        this.receipt = receipt;
        this.pluckRoute = pluckRoute;
        this.orderReference = orderReference;
        this.warehouse = warehouse;
        this.fleetOwner = fleetOwner;
        this.project = project;
        this.category = category;

        this.customerName = customerName;
        this.region = region;
        this.address = address;
        this.zipCode = zipCode;
        this.date = date;

        this.printed = printed;
        this.expeditionStatus= expeditionStatus;

        this.pickup = pickup;
        this.FV = FV;

        orderLines = new ArrayList<>();
    }

    public int getOrderID() {
        return orderID;
    }

    public String getID() {
        return id;
    }

    public int getReceipt() {
        return receipt;
    }

    public int getPluckRoute() {
        return pluckRoute;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public String getFleetOwner() {
        return fleetOwner;
    }

    public String getProject() {
        return project;
    }

    public String getCategory() {
        return category;
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
        this.address = address;
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

    public String getExpeditionStatus() {
        return expeditionStatus;
    }

    public boolean isPickup() {
        return pickup;
    }

    public boolean isFV() {
        return FV;
    }

    public int getTourID() {return tourID;}

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setTourID(int tourID) {this.tourID = tourID;}

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLine orderLine){
        orderLines.add(orderLine);
    }

    @Override
    public String toString() {
        return id + "," + receipt + "," + pluckRoute + "," + orderReference + "," +
                warehouse + "," + fleetOwner + "," + project + "," + category + "," +
                customerName + "," + region + "," + address + "," + zipCode + "," + date + "," +
                printed + "," + expeditionStatus + "," + pickup + "," + FV;
    }
}