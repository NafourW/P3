package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.TourGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class TourGeneratorController {

    @FXML private VBox root;

    @FXML private ChoiceBox<String> regionChoiceBox;
    @FXML private CheckBox allRegionsCheckBox;
    @FXML private DatePicker datePicker;
    @FXML private CheckBox allDatesCheckBox;

    private ObservableList<String> regions = FXCollections.observableArrayList();

    private TourGenerator tourGen;

    @FXML
    public void initialize() {
        tourGen = new TourGenerator();

        regions.addAll("Jylland", "Fyn", "Sjælland");
        regionChoiceBox.setItems(regions);
        regionChoiceBox.getSelectionModel().selectFirst();

        allRegionsCheckBox.setSelected(true);
        allRegionsCheckBoxAction(null);

        datePicker.setValue(LocalDate.now());

        allDatesCheckBox.setSelected(true);
        allDatesCheckBoxAction(null);
    }

    @FXML
    private void allRegionsCheckBoxAction(ActionEvent event) {
        regionChoiceBox.setDisable(allRegionsCheckBox.isSelected());
    }

    @FXML
    private void allDatesCheckBoxAction(ActionEvent event) {
        datePicker.setDisable(allDatesCheckBox.isSelected());
    }

    @FXML
    private void generateToursButtonAction(ActionEvent event) {
        boolean confirmed = Main.gui.showYesNoDialog("label_tourgen_confirmation_title", "message_tourgen_confirmation");

        if (confirmed) {
            tourGen.setRegion((allRegionsCheckBox.isSelected()) ? null : regionChoiceBox.getValue());
            tourGen.setDate((allDatesCheckBox.isSelected()) ? null :datePicker.getValue());
            tourGen.generateTours();
            close();
        }
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
