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
    public void ExportTourList(ArrayList<Tour> tours, String path) throws IOException {

        String exportPath = path + "/Ture.csv";
        FileWriter writer = new FileWriter(exportPath);

        //write the attributes of data (first row of the table)
        CSVUtils.writeLine(writer, Arrays.asList("Turdato", "Tur", "Rute", "RuteBeskrivelse", "Chauffør", "Pakkedato",
                "Turstatus", "Consignor - egen vogn"));

        //Go through each tour in the list of tours
        for (Tour tour : tours) {

            //append the information of the tour to the file
            CSVUtils.writeLine(writer, Arrays.asList(tour.getTourDate().toString(), String.valueOf(tour.getTourID()),
                    tour.getRegion(), tour.getDriver(), tour.getPackingDate().toString(),
                    tour.getStatus(), tour.getConsignor().toString()));

        }
    }

    public void ExportTourOrderList(Tour tour, String path) throws Exception{
        /* The method above has to run first before running this method.
         * This method reads the file made by the method from above and go through the tours within the file.
         * For each tour in the file, a new file is created with the corresponding id for the tour and the orders
         * withing the tour in the system is written into the new file.
         *
         */

            String orderExportPath = path + "/" + tour.getTourID() + ".csv"; //create a file for these orders

            FileWriter writer1 = new FileWriter(orderExportPath); //ready to append the file

            //write first row of the table (attritubes)
            CSVUtils.writeLine(writer1, Arrays.asList("M", "Plukrute", "Ordre", "Returordre reference",
                    "Ekspeditionsstatus", "Leveringsnavn", "Ønsket modtagelsesdato", "Følgeseddel", "Plukrute2",
                    "Gadenavn", "Postnummer", "Følgeseddel3", "Afhentning", "Afhentningsdato", "Leverungs Uge",
                    "Pakkedato", "Lagersted", "Ordrekategori", "Flådeejer", "Udskrevet", "Projekt"));

            //go through each order within the tour
            for (Order order: tour.getOrders()){

                //write the information of the order in the list of orders
                CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(order.getPluckRoute()), order.getID(),
                        order.getOrderReference(), order.getExpeditionStatus(), order.getCustomerName(),
                        order.getDate().toString(), String.valueOf(order.getReceipt()), String.valueOf(order.getPluckRoute()),
                        order.getAddress(), String.valueOf(order.getZipCode()), String.valueOf(order.getReceipt()),
                        order.getCategory(), order.getDate().toString(), String.valueOf(order.getWeekNumber()),
                        order.getDate().toString(), order.getWarehouse(), order.getCategory(),
                        order.getFleetOwner(), String.valueOf(order.isPrinted()), order.getProject()));
            }
            //ending appending to the file of list of orders
            writer1.flush();
            writer1.close();
        }
        //ending appending to the file of list of tours
        writer.flush();
        writer.close();
    }
}
