package dk.aau.cs.ds308e.gui.controllers;

import dk.aau.cs.ds308e.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController {
    @FXML
    private void toursButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("TourList");
    }

    @FXML
    private void ordersButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void settingsButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("Settings");
    }
}
