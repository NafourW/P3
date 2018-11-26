package dk.aau.cs.ds308e18.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tour {

    ArrayList<Order> orders;

    String name;

    String Region;

    String RegionDetail;

    String driver;

    String directory = "resources/GenerateTour.csv";

    float tourDate;

    float packingDate;

    float breakTime;

    public Tour() {
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
}
