package dk.aau.cs.ds308e18.model;

public class OrderLine {
    private String order;
    private String wareNumber;
    private String wareName;
    private int labels;
    private int delivered;
    private String individual;
    private boolean preparing;
    private String individualNumber;
    private String model;
    private String fullName;

    //Used in our database to see which order an orderline belongs to.
    private int orderID;

    public OrderLine() {
        this.order = "";
        this.wareNumber = "";
        this.wareName = "";
        this.labels = 0;
        this.delivered = 0;
        this.individual = "";
        this.preparing = false;
        this.individualNumber = "";
        this.model = "";
        this.fullName = "";
        this.orderID = 0;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setWareNumber(String wareNumber) {
        this.wareNumber = wareNumber;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public void setLabels(int labels) {
        this.labels = labels;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public void setPreparing(boolean preparing) {
        this.preparing = preparing;
    }

    public void setIndividualNumber(String individualNumber) {
        this.individualNumber = individualNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrder() { return order; }

    public String getWareNumber() { return wareNumber; }

    public String getWareName() { return wareName; }

    public int getLabels() { return labels; }

    public int getDelivered() { return delivered; }

    public String getIndividual() { return individual; }

    public boolean isPreparing() { return preparing; }

    public String getIndividualNumber() { return individualNumber; }

    public String getModel() { return model; }

    public String getFullName() { return fullName; }

    /*
    Used in our database to see which order an orderline belongs to.
    */
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID() {
        this.orderID = orderID;
    }
}
