package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class TourListController {
    @FXML
    private void orderListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }

    @FXML
    private void createEmptyTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void editTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void deleteTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void printTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void releaseTourButtonAction(ActionEvent event) {

    }
}
