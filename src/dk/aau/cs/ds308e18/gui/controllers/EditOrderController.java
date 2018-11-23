package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class EditOrderController {

    @FXML
    private Button cancelOrderButton;

    @FXML
    public void initialize() {
        cancelOrderButton.setDisable(true);
    }
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
