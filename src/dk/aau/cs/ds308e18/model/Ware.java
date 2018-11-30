package dk.aau.cs.ds308e18.model;

public class Ware {

    private String supplier;
    private String wareNumber;
    private int height;
    private int depth;
    private int grossHeight;
    private int grossDepth;
    private int grossWidth;
    private int width;
    private String wareName;
    private String searchName;
    private int wareGroup;
    private String wareType;
    private boolean liftAlone;
    private boolean liftingTools;
    private float moveTime;

    public Ware(String supplier, String wareNumber, int height, int depth, int grossHeight, int grossDepth,
                int grossWidth, int width, String wareName, String searchName, int wareGroup, String wareType,
                boolean liftAlone, boolean liftingTools, float moveTime) {
        this.supplier = supplier;
        this.wareNumber = wareNumber;
        this.height = height;
        this.depth = depth;
        this.grossHeight = grossHeight;
        this.grossDepth = grossDepth;
        this.grossWidth = grossWidth;
        this.width = width;
        this.wareName = wareName;
        this.searchName = searchName;
        this.wareGroup = wareGroup;
        this.wareType = wareType;
        this.liftAlone = liftAlone;
        this.liftingTools = liftingTools;
        this.moveTime = moveTime;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getWareNumber() {
        return wareNumber;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getGrossHeight() {
        return grossHeight;
    }

    public int getGrossDepth() {
        return grossDepth;
    }

    public int getGrossWidth() {
        return grossWidth;
    }

    public int getWidth() {
        return width;
    }

    public String getWareName() {
        return wareName;
    }

    public String getSearchName() {
        return searchName;
    }

    public int getWareGroup() {
        return wareGroup;
    }

    public String getWareType() {
        return wareType;
    }

    public boolean isLiftAlone() {
        return liftAlone;
    }

    public boolean isLiftingTools() {
        return liftingTools;
    }

    public float getMoveTime() {
        return moveTime;
    }
}