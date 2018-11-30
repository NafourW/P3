package dk.aau.cs.ds308e18.io;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Ware;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ReadFile {//Class that reads CSV files

    public ArrayList<Order> orderFile(String sourcePath){
        // Directory for the file with orders
        String directory = sourcePath + "/ordrer_tilvalg.csv";

        ArrayList<Order> orderList = new ArrayList<>();

        // This string will be filled with read line
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

                int pluckRoute = Integer.valueOf(Order[1]);
                int id = Integer.valueOf(Order[2]);

                // Returordre reference = Order[3]
                // Ekspeditionsstatus = Order[4]
                // Navn = Order[5]
                // Ønsket modtagelsesdato = Order[6]
                // Gadenavn = Order[7]

                LocalDate date;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    date = LocalDate.parse(Order[6], formatter);
                }
                catch (DateTimeParseException exc) {
                    System.out.printf("date %s is not parsable!%n", Order[6]);
                    System.out.println(exc);
                    date = LocalDate.now();
                }

                int zipCode;
                if(Order[8].matches("[0-9]+") && Order[8].length() > 2)
                    zipCode = Integer.valueOf(Order[8]);
                else
                    zipCode = 0;

                int receipt; //Convert content inside the list to long
                if (!(Order[9].isEmpty()))
                    receipt = Integer.valueOf(Order[9]);
                else
                    receipt = 0;

                boolean pickup; //Convert content inside the list to boolean
                pickup = Order[10].toLowerCase().equals("ja");

                // Afhentningsdato = Order[11]
                // Leverings UGE = Order[12]
                // Lagersted = Order[13]
                // Ordrekategori = Order[14]
                // Flådeejer = Order[15]

                boolean printed; //Convert content inside the list to boolean
                printed = Order[16].toLowerCase().equals("ja");

                // Rute = Order[17]

                boolean FV; //Convert content inside the list to boolean
                FV = Order[18].toLowerCase().equals("ja");

                Order order = new Order(pluckRoute, id, Order[3], Order[4], Order[5], date,
                        Order[7], zipCode, receipt, pickup, Order[13], Order[14], Order[15],
                        printed, Order[17], FV, Order[19]);

                orderList.add(order);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return orderList;
    }

    public ArrayList<OrderLine> orderItems(String directory){//same function as "orderFile" method

        ArrayList<OrderLine> wareList = new ArrayList<>();

        String inputFile = directory;

        String line = "";

        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            while ((line = br.readLine()) != null){

                String[] Items = line.split(cvsSplitBy, -1);

                if(Items[0].matches("^[^\\d].*")){
                    continue;
                }

                //Order = Items[0]
                //Varenummer = Items[1]
                //Varenavn = Items[2]

                int labels; //Convert content inside the list to integer
                if(Items[3].matches("[0-9]+") && Items[3].length() > 2)
                    labels = Integer.valueOf(Items[3]);
                else
                    labels = 0;

                int delivered; //Convert content inside the list to integer
                if(Items[4].matches("[0-9]+") && Items[4].length() > 2)
                    delivered = Integer.valueOf(Items[4]);
                else
                    delivered = 0;

                //Inidivid = Items[5]

                boolean preparing;
                preparing = Items[6].toLowerCase().equals("ja");

                //Individ varenummer = Items[7]
                //Model = Items[8]
                //Navn = Items[9]

                OrderLine orderline = new OrderLine(Items[0], Items[1], Items[2], labels, delivered, Items[5],
                        preparing, Items[7], Items[8], Items[9]);

                wareList.add(orderline);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return  wareList;
    }

    public ArrayList<Ware> wareTypes(){

        ArrayList<Ware> wareTypes = new ArrayList<>();

        String inputFile = "resources/varedata.csv";

        String line = "";

        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            while ((line = br.readLine()) != null){

                String[] Type = line.split(cvsSplitBy, -1);

                if(Type[0].matches("^[^\\d].*")){
                    continue;
                }

                long supplier; //Convert content inside the list to integer
                if(Type[0].matches("[0-9]+") && Type[0].length() > 2)
                    supplier = Long.valueOf(Type[0]);
                else
                    supplier = 0;

                //Varenummer = Type[1]

                int height; //Convert content inside the list to integer
                if(Type[2].matches("[0-9]+") && Type[2].length() > 2)
                    height = Integer.valueOf(Type[2]);
                else
                    height = 0;

                int depth; //Convert content inside the list to integer
                if(Type[3].matches("[0-9]+") && Type[3].length() > 2)
                    depth = Integer.valueOf(Type[3]);
                else
                    depth = 0;

                int grossHeight; //Convert content inside the list to integer
                if(Type[4].matches("[0-9]+") && Type[4].length() > 2)
                    grossHeight = Integer.valueOf(Type[4]);
                else
                    grossHeight = 0;

                int grossDepth; //Convert content inside the list to integer
                if(Type[5].matches("[0-9]+") && Type[5].length() > 2)
                    grossDepth = Integer.valueOf(Type[5]);
                else
                    grossDepth = 0;

                int grossWidth; //Convert content inside the list to integer
                if(Type[6].matches("[0-9]+") && Type[6].length() > 2)
                    grossWidth = Integer.valueOf(Type[6]);
                else
                    grossWidth = 0;

                int width; //Convert content inside the list to integer
                if(Type[7].matches("[0-9]+") && Type[7].length() > 2)
                    width = Integer.valueOf(Type[7]);
                else
                    width = 0;

                //Varenavn = Type[8]
                //Søgenavn = Type[9]

                int wareGroup; //Convert content inside the list to integer
                if(Type[10].matches("[0-9]+") && Type[10].length() > 2)
                    wareGroup = Integer.valueOf(Type[10]);
                else
                    wareGroup = 0;

                //Varetype = Type[11]

                boolean liftAlone; //Convert content inside the list to boolean
                liftAlone = Type[12].toLowerCase().equals("ja");

                boolean liftingTools; //Convert content inside the list to boolean
                liftingTools = Type[13].toLowerCase().equals("ja");

                float moveTime; //Convert content inside the list to float
                if(Type[14].matches("[0-9]+") && Type[14].length() > 2)
                    moveTime = Float.valueOf(Type[14]);
                else
                    moveTime = 0;

                Ware ware = new Ware(supplier, Type[1], height, depth, grossHeight, grossDepth, grossWidth, width,
                        Type[8], Type[9], wareGroup, Type[11], liftAlone, liftingTools, moveTime);

                wareTypes.add(ware);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return wareTypes;
    }
}