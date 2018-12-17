package dk.aau.cs.ds308e18;

import dk.aau.cs.ds308e18.function.tourgen.GPS;
import dk.aau.cs.ds308e18.gui.GUI;
import dk.aau.cs.ds308e18.io.database.DatabaseExport;
import dk.aau.cs.ds308e18.io.database.DatabaseImport;
import dk.aau.cs.ds308e18.io.database.DatabaseSetup;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //Used by view-controllers to access GUI methods
    public static GUI gui;

    //Used for interacting with the database
    public static DatabaseImport dbImport;
    public static DatabaseExport dbExport;
    public static DatabaseSetup dbSetup;
    public static GPS gps = new GPS();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws IOException{
        //Initialize database
        dbSetup = new DatabaseSetup();
        dbImport = new DatabaseImport();
        dbExport = new DatabaseExport();

        //Initialize GUI
        gui = new GUI();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //GUI needs a reference to the main window
        gui.setWindow(primaryStage);

        //Load stylesheet
        gui.initStyle();

        //Setup main window
        primaryStage.setTitle(gui.getLocalString("label_title_app"));

        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(700);

        primaryStage.setWidth(1500);
        primaryStage.setHeight(860);

        //Display main menu
        gui.changeView("MainMenu");

        primaryStage.show();
    }
}
