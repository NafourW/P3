package dk.aau.cs.ds308e;

import dk.aau.cs.ds308e.gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static GUI gui; //Used by view-controllers to access GUI methods

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        //Initialize GUI
        gui = new GUI();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //GUI needs a reference to the main window
        gui.setWindow(primaryStage);

        //Setup main window
        primaryStage.setTitle(gui.getLocalString("label_title_app"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(620);

        //Display main menu
        gui.changeView("MainMenu");
    }
}
