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
    private String name;

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

    public String getOrder() { return order; }

    public String getWareNumber() { return wareNumber; }

    public String getWareName() { return wareName; }

    public int getLabels() { return labels; }

    public int getDelivered() { return delivered; }

    public String getIndividual() { return individual; }

    public boolean isPreparing() { return preparing; }

    public String getIndividualNumber() { return individualNumber; }

    public String getModel() { return model; }

    public String getName() { return name; }
}
