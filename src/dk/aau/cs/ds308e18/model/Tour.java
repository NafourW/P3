package dk.aau.cs.ds308e18.model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Tour {

    ArrayList<Order> orders;

    private LocalDate tourDate;
    private LocalDate packingDate;
    private Integer id;
    private String region;
    private String regionDetail;
    private String driver;
    private String status;
    private Boolean consignor;

    String directory = "resources/GenerateTour.csv";
    float breakTime;

    public Tour() {
        id = 0;
        tourDate = LocalDate.now();
        packingDate = LocalDate.now();
        orders = new ArrayList<>();
    }

    public Tour(LocalDate date) {
        id = 0;
        tourDate = date;
        packingDate = date;
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    void CreateCSVFile(String tourName){
        File csvFile = new File(tourName + ".csv");
        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*

    void generateTour(String directory) throws IOException {//Only generate one tour plan

        FileWriter writer = new FileWriter(directory);

        List<Order> orders = Arrays.asList(
                new Order(),
                new Order(),
                new Order()
        );


        CSVUtils.writeLine(writer, Arrays.asList("", "", ""));

        for (Order o : orders){
            List<String> list = new ArrayList<>();

            list.add(o.);
            list.add(o.);
            list.add(o.);


            CSVUtils.writeLine(writer, list);
        }

        writer.flush();
        writer.close();

    }
    */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public LocalDate getTourDate() {
        return tourDate;
    }

    public LocalDate getPackingDate() {
        return packingDate;
    }

    public String getDriver() {
        return driver;
    }

    public String getRegion() {
        return region;
    }

    public Integer getID() {
        return id;
    }

    public Boolean getConsignor() {
        return consignor;
    }

    public String getStatus() {
        return status;
    }

    public String getRegionDetail() {
        return regionDetail;
    }
}
