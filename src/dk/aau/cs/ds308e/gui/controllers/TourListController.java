package dk.aau.cs.ds308e.gui.controllers;

import dk.aau.cs.ds308e.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TourListController {
    @FXML
    private void orderListButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("MainMenu");
    }
}
