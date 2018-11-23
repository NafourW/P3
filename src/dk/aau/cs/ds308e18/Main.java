package dk.aau.cs.ds308e18;

import dk.aau.cs.ds308e18.gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    public static GUI gui; //Used by view-controllers to access GUI methods

    //TODO: Don't do this
    public static ArrayList<String> regions = new ArrayList<String>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        //TODO: I repeat: Don't do this
        regions.add("Øer");                 //01, 05, 06, 07, 14
        regions.add("Nord Jylland");        //01
        regions.add("Midt Nord Jylland");   //01, 02, 03
        regions.add("Nord Vest Jylland");   //01, 03, 15
        regions.add("Djursland");           //02
        regions.add("Øst Jylland");         //02, 05
        regions.add("Mors Thy");            //03
        regions.add("Vest Jylland");        //04, 06, 15
        regions.add("Syd Øst Jylland");     //05, 15
        regions.add("Sønderjylland");       //06
        regions.add("Midt Nord Fyn");       //07
        regions.add("Syd Fyn");             //07
        regions.add("København");           //08, 09, 10, 11
        regions.add("Nord Sjælland");       //11, 12
        regions.add("Vest Sjælland");       //12, 13, 14
        regions.add("Syd Sjælland");        //14
        regions.add("Bornholm");            //16

        //Initialize GUI
        gui = new GUI();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //GUI needs a reference to the main window
        gui.setWindow(primaryStage);

        //Setup main window
        primaryStage.setTitle(gui.getLocalString("label_title_app"));
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(700);

        //Display main menu
        gui.changeView("MainMenu");
        primaryStage.show();
    }
}
