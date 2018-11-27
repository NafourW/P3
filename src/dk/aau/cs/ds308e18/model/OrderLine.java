package dk.aau.cs.ds308e18.model;

public class OrderLine {
    String order;
    String wareNumber;
    String wareName;
    int labels;
    int delivered;
    String individual;
    boolean preparing;
    String individualNumber;
    String model;
    String name;

    public OrderLine(String order, String wareNumber, String wareName, int labels, int delivered, String individual,
                     boolean preparing, String individualNumber, String model, String name) {
        this.order = order;
        this.wareNumber = wareNumber;
        this.wareName = wareName;
        this.labels = labels;
        this.delivered = delivered;
        this.individual = individual;
        this.preparing = preparing;
        this.individualNumber = individualNumber;
        this.model = model;
        this.name = name;
    }
}
