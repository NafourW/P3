package dk.aau.cs.ds308e;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ReadFile {//Class that reads CSV files
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //String directory = scanner.nextLine().toString();

        String directory = "resources/ordrer_tilvalg.csv";

        ReadFile test = new ReadFile();

        test.orderFile(directory);

        String line = "Dig;";
        String line2 = "Jeg;har";

        System.out.println(Arrays.toString(line.split(";")));
        System.out.println(Arrays.toString(line2.split(";")));
    }

    void xlsxToCSV(File inputFile, File outputFile){
    }

    void orderFile(String directory){
        String inputFile = directory;

        String line = "";

        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            while ((line = br.readLine()) != null){
                String[] Order = line.split(cvsSplitBy);

                if(line.trim().indexOf('M') == 0){
                    continue; //Issue
                } else {
                    /*
                    System.out.println("Order [" +
                            "Plukrute: " + Order[1] +
                            ", Ordre: " + Order[2] +
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
                            */
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    void orderItems(String directory){
        String inputFile = directory;

        String line = "";

        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            while ((line = br.readLine()) != null){
                String[] Items = line.split(cvsSplitBy);

                System.out.println("Item [" + Items[0]);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
