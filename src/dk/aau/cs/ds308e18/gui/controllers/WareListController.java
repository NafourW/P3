package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class WareListController {
    @FXML
    private void backButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("EditOrder");
    }
}
