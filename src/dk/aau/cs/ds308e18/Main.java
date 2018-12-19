package dk.aau.cs.ds308e18;

import dk.aau.cs.ds308e18.function.tourgen.GPS;
import dk.aau.cs.ds308e18.gui.GUI;
import dk.aau.cs.ds308e18.io.database.Database;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //Used for interacting with the database
    public static Database dbSetup;

    //Used for getting coordinates and time between them
    public static GPS gps;

    //Used by view-controllers to access GUI methods
    public static GUI gui;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws IOException{
        //Initialize database
        dbSetup = new Database();

        //Initialize GPS
        gps = new GPS();

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

        //Set minimum window size, so the GUI elements are never forced to overlap
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(700);

        //If dpi scaling is 125% or lower on the operating system,
        //use a bigger resolution for the main window
        Screen screen = Screen.getPrimary();
        double scale = screen.getOutputScaleX();
        if (scale <= 1.25) {
            primaryStage.setWidth(1500);
            primaryStage.setHeight(860);
        }

        //Display main menu
        gui.changeView("MainMenu");

        primaryStage.show();
    }
}
