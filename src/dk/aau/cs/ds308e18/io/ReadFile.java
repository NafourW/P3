package dk.aau.cs.ds308e18.io;

import dk.aau.cs.ds308e18.model.Order;

import java.io.*;
import java.util.ArrayList;

public class ReadFile {//Class that reads CSV files
    public static void main(String[] args) {
        String directory1 = "resources/T015785_0726018_ordrelinjer.csv"; //Directory for the file with wares within an order

        String directory2 = "resources/varedata.csv"; //Directory for the file with existing ware types

        ReadFile test = new ReadFile(); //test object
        //test.orderFile(); //read file with orders

        /*

        test.orderItems(directory1); //read file with wares within an order

        System.out.println("\n\n");

        test.itemTypes(directory2); //read file with existing ware types
        */

        test.itemTypes(directory2);
    }

    //convert xlsx file to csv file
    void xlsxToCSV(File inputFile, File outputFile){
    }

    public ArrayList<Order> orderFile(){
        //Directory for the file with orders
        String directory = "resources/ordrer_tilvalg.csv";

        ArrayList<Order> orderList = new ArrayList<>();

        //This string will be filled with read line
        String line = "";

        // Split the read line when encountering ';' which is used in the CSV file to distinguish new information
        String cvsSplitBy = ";";

        //create a buffered reader to read the file
        try (BufferedReader br = new BufferedReader(new FileReader(directory))){

            // While the line read by the buffered reader is not empty(null)
            while ((line = br.readLine()) != null){

                //Split the line with ';' and keeps the empty parts
                //and put the splitted parts into an array
                String[] Order = line.split(cvsSplitBy,-1);

                // Skips the first line, that only contains the names of categories
                if (Order[0].contains("M"))
                    continue;

                long pluckRoute = Long.valueOf(Order[1]);
                int id = Integer.valueOf(Order[2]);

                // Returordre reference = Order[3]
                // Ekspeditionsstatus = Order[4]
                // Navn = Order[5]
                // Ønsket modtagelsesdato = Order[6]
                // Gadenavn = Order[7]

                long zipCode;
                if(Order[8].matches("[0-9]+") && Order[8].length() > 2)
                    zipCode = Long.valueOf(Order[8]);
                else
                    zipCode = 0;

                long receipt;
                if (!(Order[9].isEmpty()))
                    receipt = Long.valueOf(Order[9]);
                else
                    receipt = 0;

                boolean pickup;
                pickup = Order[10].toLowerCase().equals("ja");

                // Afhentningsdato = Order[11]
                // Leverings UGE = Order[12]
                // Lagersted = Order[13]
                // Ordrekategori = Order[14]
                // Flådeejer = Order[15]

                boolean printed;
                printed = Order[16].toLowerCase().equals("ja");

                // Rute = Order[17]

                boolean FV;
                FV = Order[18].toLowerCase().equals("ja");

                Order order = new Order(pluckRoute, id, Order[3], Order[4], Order[5], Order[6], Order[7],
                        zipCode, receipt, pickup, Order[13], Order[14], Order[15],
                        printed, Order[17], FV, Order[19]);

                orderList.add(order);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return orderList;
    }

    void orderItems(String directory){//same function as "orderFile" method
        String inputFile = directory;

        String line = "";

        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            while ((line = br.readLine()) != null){

                String[] Items = line.split(cvsSplitBy, -1);

                if(Items[0].matches("^[^\\d].*")){
                    continue;
                }
                System.out.print("\nWare [" +
                        "Order: " + Items[0] +
                        ", Varenummer: " + Items[1] +
                        ", Varenavn: " + Items[2] +
                        ", Labels: " + Items[3] +
                        ", Leveret: " + Items[4] +
                        ", Inidivid: " + Items[5] +
                        ", Klargøring: " + Items[6] +
                        ", Individ varenummer: " + Items[7] +
                        ", Model: " + Items[8] +
                        ", Navn: " + Items[9] +
                        "]");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    void itemTypes(String directory){
        String inputFile = directory;

        String line = "";

        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            while ((line = br.readLine()) != null){

                String[] Type = line.split(cvsSplitBy, -1);

                if(Type[0].matches("^[^\\d].*")){
                    continue;
                }
                System.out.print("\nItem [" +
                        "Leverandør:" + Type[0] +
                        ", Varenummer: " + Type[1] +
                        ", Højde: " + Type[2] +
                        ", Dybde: " + Type[3] +
                        ", Bruttohøjde: " + Type[4] +
                        ", Bruttodybde: " + Type[5] +
                        ", Bruttobredde: " + Type[6] +
                        ", Bredde: " + Type[7] +
                        ", Varenavn: " + Type[8] +
                        ", Søgenavn: " + Type[9] +
                        ", Varegruppe: " + Type[10] +
                        ", Varetype: " + Type[11] +
                        ", Løftes alene: " + Type[12] +
                        ", Løfter værktøj: " + Type[13] +
                        ", Flytte tid: " + Type [14] +
                        "]");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}