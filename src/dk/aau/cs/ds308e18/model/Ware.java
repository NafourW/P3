package dk.aau.cs.ds308e18.model;

public class Ware {

    private long supplier;
    private String wareNumber;
    private float height;
    private float depth;
    private float grossHeight;
    private float grossDepth;
    private float grossWidth;
    private float width;
    private String wareName;
    private String searchName;
    private int wareGroup;
    private String wareType;
    private boolean liftAlone;
    private boolean liftingTools;
    private float moveTime;

    public Ware() {

    }

    public Ware(long supplier, String wareNumber, float height, float depth, float grossHeight, float grossDepth, float grossWidth, float width, String wareName, String searchName, int wareGroup, String wareType, boolean liftAlone, boolean liftingTools, float moveTime) {
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
}