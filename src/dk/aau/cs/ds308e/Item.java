package dk.aau.cs.ds308e;

public class Item {

    String name;
    String model;

    int amount;

    float width;
    float length;
    float height;
    float weight;

    float timeRequired;

    boolean ToolRequired;

    boolean CarriableByOne;

    public Item(String name, String model, int amount, float width, float length, float height, float weight, float timeRequired, boolean toolRequired, boolean carriableByOne) {
        this.name = name;
        this.model = model;
        this.amount = amount;
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.timeRequired = timeRequired;
        ToolRequired = toolRequired;
        CarriableByOne = carriableByOne;
    }





}
