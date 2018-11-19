package dk.aau.cs.ds308e;

import dk.aau.cs.ds308e.gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static GUI gui;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        gui = new GUI();
        gui.setLanguage("en", "US");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle(gui.getLocalString("label_title_app"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(620);

        gui.setWindow(primaryStage);
        gui.changeView("MainMenu");
    }

}
