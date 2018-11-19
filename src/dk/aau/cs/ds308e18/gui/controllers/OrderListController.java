package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class OrderListController {
    @FXML
    private void tourListButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("TourList");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("MainMenu");
    }
}
