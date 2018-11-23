package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private void toursButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("TourList");
    }

    @FXML
    private void ordersButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void waresButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("WareList");
    }

    @FXML
    private void settingsButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("Settings");
    }
}
