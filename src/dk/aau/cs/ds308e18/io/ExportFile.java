package dk.aau.cs.ds308e18.io;

import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Tour;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ExportFile {
    public void ExportData(String path) {
        String toursPath = path + "/Ture.csv";
        try (BufferedWriter toursWriter = new BufferedWriter(new FileWriter(toursPath))) {
            //write the attributes of data (first row of the table)
            CSVUtils.writeLine(toursWriter, Arrays.asList("Turdato", "Tur", "Rute",
                    "RuteBeskrivelse", "Chauffør", "Pakkedato",
                    "Turstatus", "Consignor - egen vogn"));

            //Go through each tour in the list of tours
            for (Tour tour : TourManagement.getTours()) {
                //append the information of the tour to the file
                CSVUtils.writeLine(toursWriter, Arrays.asList(tour.getTourDate().toString(), "T" + tour.getTourID(),
                        tour.getRegion(), tour.getDriver(), tour.getPackingDate().toString(),
                        tour.getStatus().toString(), tour.getConsignor().toString()));

                String tourPath = path + "/T" + tour.getTourID() + ".csv";
                try (BufferedWriter tourWriter = new BufferedWriter(new FileWriter(tourPath))) {
                    //write first row of the table (attritubes)
                    CSVUtils.writeLine(tourWriter, Arrays.asList("M", "Plukrute", "Ordre", "Returordre reference",
                            "Ekspeditionsstatus", "Leveringsnavn", "Ønsket modtagelsesdato", "Følgeseddel", "Plukrute2",
                            "Gadenavn", "Postnummer", "Følgeseddel3", "Afhentning", "Afhentningsdato", "Leverungs Uge",
                            "Pakkedato", "Lagersted", "Ordrekategori", "Flådeejer", "Udskrevet", "Projekt"));

                    //go through each order within the tour
                    for (Order order: tour.getOrders()){
                        //write the information of the order in the list of orders
                        CSVUtils.writeLine(tourWriter, Arrays.asList("", String.valueOf(order.getPluckRoute()), order.getID(),
                                order.getOrderReference(), order.getExpeditionStatus(), order.getCustomerName(),
                                order.getDate().toString(), String.valueOf(order.getReceipt()), String.valueOf(order.getPluckRoute()),
                                order.getAddress(), String.valueOf(order.getZipCode()), String.valueOf(order.getReceipt()),
                                order.getCategory().toString(), order.getDate().toString(), String.valueOf(order.getWeekNumber()),
                                order.getDate().toString(), order.getWarehouse(), order.getCategory().toString(),
                                order.getFleetOwner(), String.valueOf(order.isPrinted()), order.getProject()));

                        String orderPath = path + "/" + order.getID() + "_ordrelinjer.csv";
                        try (BufferedWriter orderWriter = new BufferedWriter(new FileWriter(orderPath))) {
                            for (OrderLine orderLine : order.getOrderLines()) {
                                CSVUtils.writeLine(orderWriter, Arrays.asList(orderLine.getFullName(), String.valueOf(orderLine.getLabels())));
                            }
                        }
                        catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
