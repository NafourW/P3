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

    public Ware(){
        this.supplier = "";
        this.wareNumber = "";
        this.height = 0;
        this.depth = 0;
        this.grossHeight = 0;
        this.grossDepth = 0;
        this.grossWidth = 0;
        this.width = 0;
        this.wareName = "";
        this.searchName = "";
        this.wareGroup = 0;
        this.wareType = "";
        this.liftAlone = false;
        this.liftingTools = false;
        this.moveTime = 0;
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

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setWareNumber(String wareNumber) {
        this.wareNumber = wareNumber;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setGrossHeight(int grossHeight) {
        this.grossHeight = grossHeight;
    }

    public void setGrossDepth(int grossDepth) {
        this.grossDepth = grossDepth;
    }

    public void setGrossWidth(int grossWidth) {
        this.grossWidth = grossWidth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setWareGroup(int wareGroup) {
        this.wareGroup = wareGroup;
    }

    public void setWareType(String wareType) {
        this.wareType = wareType;
    }

    public void setLiftAlone(boolean liftAlone) {
        this.liftAlone = liftAlone;
    }

    public void setLiftingTools(boolean liftingTools) {
        this.liftingTools = liftingTools;
    }

    public void setMoveTime(float moveTime) {
        this.moveTime = moveTime;
    }

    @Override
    public String toString() {
        return supplier + "," + wareNumber + "," + height + "," + depth + "," +
                grossHeight + "," + grossDepth + "," + grossWidth + "," + width + "," +
                wareName + "," + searchName + "," + wareGroup + "," + wareType + "," +
                liftAlone + "," + liftingTools + "," + moveTime;
    }
}