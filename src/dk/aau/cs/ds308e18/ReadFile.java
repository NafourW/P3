package dk.aau.cs.ds308e18;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ReadFile {//Class that reads CSV files
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //String directory = scanner.nextLine().toString();

        String directory = "resources/ordrer_tilvalg.csv"; //Directory for the file with orders

        String directory1 = "resources/T015785_0726018_ordrelinjer.csv"; //Directory for the file with wares within an order

        String directory2 = "resources/varedata.csv"; //Directory for the file with existing ware types

        ReadFile test = new ReadFile(); //test object

        test.orderFile(directory); //read file with orders

        test.orderItems(directory1); //read file with wares within an order

        System.out.println("\n\n");

        test.itemTypes(directory2); //read file with existing ware types
    }

    //convert xlsx file to csv file
    void xlsxToCSV(File inputFile, File outputFile){
    }

    void orderFile(String directory){
        String line = ""; //this string will be filled with read line

        String cvsSplitBy = ";"; //split the read line when encountering ';' which is used in the CSV file to
                                 //distinguish new information

        try (BufferedReader br = new BufferedReader(new FileReader(directory))){//create a buffered reader to
                                                                                //read the file
            while ((line = br.readLine()) != null){//while the line read by the buffered reader is not empty(null)

                String[] Order = line.split(cvsSplitBy,-1); //split the line with ';' and keeps the empty parts
                                                                 //and put the splitted parts into an array

                if (Order[0].contains("M")){//skips the first line, that only contains the names of categories
                    continue;
                }

                //print out the array with splitted parts
                System.out.println("Order [" +
                        "Plukrute: " + Order[1] +
                        ", Order: " + Order[2] +
                        ", Returordre reference: " + Order[3] +
                        ", Ekspeditionsstatus: " + Order[4] +
                        ", Navn: " + Order[5] +
                        ", Ønsket modtagelsesdato: " + Order[6] +
                        ", Gadenavn: " + Order[7] +
                        ", Postnummer: " + Order[8] +
                        ", Følgeseddel: " + Order[9] +
                        ", Afhentning: " + Order[10] +
                        ", Afhentningsdato: " + Order[11] +
                        ", Leverings UGE: " + Order[12] +
                        ", Lagersted: " + Order[13] +
                        ", Ordrekategori: " + Order[14] +
                        ", Flådeejer: " + Order[15] +
                        ", Udskrevet: " + Order[16] +
                        ", Rute: " + Order[17] +
                        ", FV: " + Order[18] +
                        ", Projekt: " + Order[19] +
                        "]");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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
                        "]");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}