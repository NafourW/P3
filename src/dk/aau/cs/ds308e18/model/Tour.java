package dk.aau.cs.ds308e18.model;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.tourgen.TourGenerator;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class Tour {

    public enum tourStatus{
        invalid,
        invalidEmpty,
        valid,
        validFull,
        validReleased
    }

    ArrayList<Order> orders;

    private LocalDate tourDate;
    private LocalDate packingDate;
    private String region;
    private String regionDetail;
    private String driver;
    private tourStatus status;
    private Boolean consignor;

    private int tourTime;

    //Used in our database to see which tour an order belongs to.
    private int tourID;

    //Used for Vibocold's tour IDs.
    private int id;

    public Tour() {
        id = 0;
        tourID = 0;
        tourDate = LocalDate.now();
        packingDate = LocalDate.now();
        orders = new ArrayList<>();
        region = "";
        regionDetail = "";
        driver = "";
        status = tourStatus.invalidEmpty;
        consignor = false;
    }

    public void addOrder(Order order) {
        orders.add(order);

        if (status == tourStatus.validReleased)
            return;

        if (orders.size() > 0)
            status = tourStatus.valid;

        if (orders.size() >= TourGenerator.MAX_ORDERS_PER_TOUR)
            status = tourStatus.validFull;
    }

    public void removeOrder(Order order) {
        orders.remove(order);

        if (status == tourStatus.validReleased)
            return;

        if (orders.size() < TourGenerator.MAX_ORDERS_PER_TOUR)
            status = tourStatus.valid;

        if (orders.size() < 1)
            status = tourStatus.invalidEmpty;
    }

    /*
    Used in our database to see which tour an order belongs to.
    */
    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    /*
    Used for Vibocold's tour IDs.
    */
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    //Used in GUI
    public int getOrderAmount() {
        return orders.size();
    }

    public LocalDate getTourDate() {
        return tourDate;
    }

    public void setTourDate(LocalDate tourDate) {
        this.tourDate = tourDate;
    }

    public LocalDate getPackingDate() {
        return packingDate;
    }

    public void setPackingDate(LocalDate packingDate) {
        this.packingDate = packingDate;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getConsignor() {
        return consignor;
    }

    public void setConsignor(Boolean consignor) {
        this.consignor = consignor;
    }

    public tourStatus getStatus() {
        return status;
    }
    public void setStatus(tourStatus status) {
        this.status = status;
    }

    public int getWeekNumber() {
        WeekFields weekFields = WeekFields.ISO;
        int weekNumber = tourDate.get(weekFields.weekOfWeekBasedYear());
        return weekNumber;
    }

    //Used in GUI
    public String getLocalizedStatusString() {
        return Main.gui.getLocalString("value_tour_status_" + status.toString());
    }
    public void setStatus(String status) {
        switch (status.toLowerCase()) {
            case "invalid - empty":
            case "ugyldig - tom":
                this.status = tourStatus.invalidEmpty;
                break;
            case "valid - full":
            case "gyldig - fuld":
                this.status = tourStatus.validFull;
                break;
            case "valid - released":
            case "gyldig - frigivet":
            case "frigivet":
                this.status = tourStatus.validReleased;
                break;
                case "invalid":
            case "ugyldig":
                this.status = tourStatus.invalid;
                break;
            case "valid":
            case "gyldig":
            case "fuldført":
                this.status = tourStatus.valid;
                break;
        }
    }

    public String getRegionDetail() {
        return regionDetail;
    }

    public int getTourTime() {
        return tourTime;
    }

    public void setTourTime(int tourTime) {
        this.tourTime = tourTime;
    }
}
