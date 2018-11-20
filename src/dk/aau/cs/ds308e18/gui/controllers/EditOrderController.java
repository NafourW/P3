package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EditOrderController {
    @FXML
    private void viewWareListButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("WareList");
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws Exception{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void addWareToOrderButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void removeWareButtonAction(ActionEvent event) throws Exception{

    }
}
