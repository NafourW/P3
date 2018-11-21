package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class OrderListController {
    @FXML
    private void tourListButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("TourList");
    }

    @FXML
    private void generateToursButtonAction(ActionEvent event) throws IOException {
        Main.gui.openWindow("TourGenerator", "label_tourgen_title");
    }

    @FXML
    private void createOrderButtonAction(ActionEvent event) throws IOException{
        //1. create order
        //2. select order
        //3.
        Main.gui.changeView("EditOrder");
    }

    @FXML
    private void editOrderButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("EditOrder");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }
}
