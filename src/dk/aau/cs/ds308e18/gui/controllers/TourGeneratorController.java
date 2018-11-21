package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TourGeneratorController {

    @FXML private VBox root;

    @FXML
    private void generateToursButtonAction(ActionEvent event) {
        boolean confirmed = Main.gui.showYesNoDialog("label_tourgen_confirmation_title", "message_tourgen_confirmation");

        if (confirmed)
            close();
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        close();
    }

    private void close() {
        Stage window = (Stage) root.getScene().getWindow();
        Main.gui.closeWindow(window);
    }
}
