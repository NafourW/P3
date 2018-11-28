package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EditTourController implements ISelectionController {

    @FXML
    private Button removeOrderButton;

    private Tour selectedTour;

    @FXML
    private void initialize(){
        removeOrderButton.setDisable(true);
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void removeOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void moveOrderToTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void doneButtonAction(ActionEvent event) {

    }

    @Override
    public void setSelectedObject(Object obj) {
        selectedTour = (Tour)obj;
    }
}
