package dk.aau.cs.ds308e18.model;

import java.util.ArrayList;

public class Order {
    ArrayList<Ware> wares;

    /*
    String adress;
    String moreInfo;
    long orderNumber;
    long zipCode;
    long receiptNumber;
    float receiveDate;
    float pickupDate;
    float timeRequired;
    */

    private long pluckRoute;
    private int id;
    private String orderReference;
    private String expeditionStatus;
    private String customerName;
    private String date;
    private String address;
    private String region;
    private long zipCode;
    private long receipt;
    private boolean pickup;
    private String warehouse;
    private String category;
    private String fleetOwner;
    private boolean printed;
    private String route;
    private boolean FV;
    private String project;

    public Order() {
        pluckRoute = 0;
        id = 0;
        orderReference = "";
        expeditionStatus = "";
        customerName = "";
        date = "";
        address = "";
        zipCode = 0;
        receipt = 0;
        pickup = false;
        warehouse = "";
        category = "";
        fleetOwner = "";
        printed = false;
        route = "";
        FV = false;
        project = "";
    }

    public Order(long pluckRoute, int id, String orderReference, String expeditionStatus, String customerName,
                 String date, String address, long zipCode, long receipt, boolean pickup,
                 String warehouse, String category, String fleetOwner,
                 boolean printed, String route, boolean FV, String project) {
        this.pluckRoute = pluckRoute;
        this.id = id;
        this.orderReference = orderReference;
        this.expeditionStatus= expeditionStatus;
        this.customerName = customerName;
        this.date = date;
        this.address = address;
        this.zipCode = zipCode;
        this.receipt = receipt;
        this.pickup = pickup;
        this.warehouse = warehouse;
        this.category = category;
        this.fleetOwner = fleetOwner;
        this.printed = printed;
        this.route = route;
        this.FV = FV;
        this.project = project;
    }

    public int getID() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public long getZipCode() {
        return zipCode;
    }

    public long getPluckRoute() {
        return pluckRoute;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public String getExpeditionStatus() {
        return expeditionStatus;
    }

    public String getDate() {
        return date;
    }

    public int getWeekNumber() {
        //TODO: Convert date to week number
        return 0;
    }

    public long getReceipt() {
        return receipt;
    }

    public boolean isPickup() {
        return pickup;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public String getCategory() {
        return category;
    }

    public String getFleetOwner() {
        return fleetOwner;
    }

    public boolean isPrinted() {
        return printed;
    }

    public String getRoute() {
        return route;
    }

    public boolean isFV() {
        return FV;
    }

    public String getProject() {
        return project;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return pluckRoute + "," + id + "," + orderReference + "," + expeditionStatus + "," +
                customerName + "," + date + "," + address + "," + region + "," + zipCode + "," +
                receipt + "," + pickup + "," + warehouse + "," + category + "," + fleetOwner + "," +
                printed + "," + route + "," + FV + "," + project;
    }
}