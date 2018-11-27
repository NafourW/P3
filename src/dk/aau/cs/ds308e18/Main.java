package dk.aau.cs.ds308e18;

import dk.aau.cs.ds308e18.gui.GUI;
import dk.aau.cs.ds308e18.io.DataManagement;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //Used by view-controllers to access GUI methods
    public static GUI gui;

    //Used for interacting with the database
    public static DataManagement db;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        //Initialize database
        db = new DataManagement();
        db.importOrders();

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
