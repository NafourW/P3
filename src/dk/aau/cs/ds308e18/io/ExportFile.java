package dk.aau.cs.ds308e18.io;

import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExportFile {
    public void ExportTourList(String path) throws Exception{

        ArrayList<Tour> tours = TourManagement.getTours();

        String exportPath = path + "/Ture.csv";
        FileWriter writer = new FileWriter(exportPath);

        CSVUtils.writeLine(writer, Arrays.asList("Turdato", "Tur", "Rute", "RuteBeskrivelse", "Chauffør", "Pakkedato",
                "Turstatus", "Consignor - egen vogn"));

        //Go through
        for (Tour tour: tours){
            CSVUtils.writeLine(writer, Arrays.asList(tour.getTourDate().toString(), String.valueOf(tour.getTourID()),
                    tour.getRegion(), tour.getDriver(), tour.getPackingDate().toString(),
                    tour.getStatus(), tour.getConsignor().toString()));
        }

        writer.flush();
        writer.close();
    }

    public void ExportTourOrderList(String path) throws Exception{
        /* The method above has to run first before running this method.
         * This method reads the file made by the method from above and go through the tours within the file.
         * For each tour in the file, a new file is created with the corresponding id for the tour and the orders
         * withing the tour in the system is written into the new file.
         *
         */

        String toursFile = path + "/Ture.csv";

        String line = "";

        String csvSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(toursFile))){

            while ((line = br.readLine()) != null){

                String[] Tour = line.split(csvSplitBy, -1);

                if (Tour[0].contains("Turdato")){
                    continue;
                }

                String exportPath = path + "/" + Tour[1] + ".csv";

                FileWriter writer = new FileWriter(exportPath);

                CSVUtils.writeLine(writer, Arrays.asList("M", "Plukrute", "Ordre", "Returordre reference",
                        "Ekspeditionsstatus", "Leveringsnavn", "Ønsket modtagelsesdato", "Følgeseddel", "Plukrute2",
                        "Gadenavn", "Postnummer", "Følgeseddel3", "Afhentning", "Afhentningsdato", "Leverungs Uge",
                        "Pakkedato", "Lagersted", "Ordrekategori", "Flådeejer", "Udskrevet", "Projekt"));

                ArrayList<Order> orders = new ArrayList<>();


                for (Order order : orders){
                    CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(order.getPluckRoute()), order.getID(),
                            order.getOrderReference(), order.getExpeditionStatus(), order.getCustomerName(),
                            order.getDate().toString(), String.valueOf(order.getReceipt()), String.valueOf(order.getPluckRoute()),
                            order.getAddress(), String.valueOf(order.getZipCode()), String.valueOf(order.getReceipt()),
                            order.getCategory(), order.getDate().toString(), String.valueOf(order.getWeekNumber()),
                            order.getDate().toString(), order.getWarehouse(), order.getCategory(),
                            order.getFleetOwner(), String.valueOf(order.isPrinted()), order.getProject()));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
