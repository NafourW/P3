package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class EditOrderController {
    @FXML
    private void viewWareListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("WareList");
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void addWareToOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void removeWareButtonAction(ActionEvent event) {

    }
}
