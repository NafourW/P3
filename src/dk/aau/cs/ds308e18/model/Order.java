package dk.aau.cs.ds308e18.model;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class Order {
    private int id;
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

    ArrayList<Ware> wares;

    public Order() {
        id = 0;
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

        wares = new ArrayList<>();
    }

    public Order(int pluckRoute, int id, String orderReference, String expeditionStatus, String customerName,
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

        wares = new ArrayList<>();
    }

    public int getID() {
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

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }

    public int getZipCode() {
        return zipCode;
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

    public ArrayList<Ware> getWares() {
        return wares;
    }

    @Override
    public String toString() {
        return id + "," + receipt + "," + pluckRoute + "," + orderReference + "," +
                warehouse + "," + fleetOwner + "," + project + "," + category + "," +
                customerName + "," + region + "," + address + "," + zipCode + "," + date + "," +
                printed + "," + expeditionStatus + "," + pickup + "," + FV;
    }
}