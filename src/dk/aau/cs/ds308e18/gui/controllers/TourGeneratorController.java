package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.tourgen.TourGenerator;
import dk.aau.cs.ds308e18.function.tourgen.TourGeneratorSettings;
import dk.aau.cs.ds308e18.io.database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/*
The Tour Generator window, where the tour generation can be configured
*/
public class TourGeneratorController {

    @FXML private VBox root;

    @FXML private Button cancelButton;
    @FXML private Button generateToursButton;

    @FXML private ChoiceBox<TourGeneratorSettings.planningMethod> planningChoiceBox;
    @FXML private ChoiceBox<String> regionChoiceBox;
    @FXML private CheckBox allRegionsCheckBox;
    @FXML private DatePicker datePicker;
    @FXML private CheckBox allDatesCheckBox;
    @FXML private CheckBox forceOrdersCheckBox;

    @FXML private TextField breakTimeField;
    @FXML private TextField workTimeField;

    private ObservableList<String> regions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        planningChoiceBox.getItems().setAll(TourGeneratorSettings.planningMethod.values());
        planningChoiceBox.setValue(TourGeneratorSettings.planningMethod.leastTime);
        planningChoiceBox.setDisable(true);

        regions.addAll(Database.dbExport.exportRegionNames());
        regionChoiceBox.setItems(regions);
        regionChoiceBox.getSelectionModel().selectFirst();

        allRegionsCheckBox.setSelected(true);
        allRegionsCheckBoxAction(null);

        datePicker.setValue(LocalDate.now());

        allDatesCheckBox.setSelected(true);
        allDatesCheckBoxAction(null);

        workTimeField.setText(String.valueOf(TourGenerator.DEFAULT_WORK_TIME));
        breakTimeField.setText(String.valueOf(TourGenerator.DEFAULT_BREAK_TIME));
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
    private void generateToursButtonAction(ActionEvent event) throws IOException {
        boolean confirmed = Main.gui.showYesNoDialog("label_tourgen_confirmation_title", "message_tourgen_confirmation");

        if (confirmed) {
            setInteractionsDisabled(true);

            //Get settings
            TourGeneratorSettings settings = new TourGeneratorSettings();
            settings.method = planningChoiceBox.getValue();
            settings.region = (allRegionsCheckBox.isSelected()) ? null : regionChoiceBox.getValue();
            settings.date = (allDatesCheckBox.isSelected()) ? null : datePicker.getValue().toString();
            settings.workTime = Integer.valueOf(workTimeField.getText());
            settings.breakTime = Integer.valueOf(breakTimeField.getText());
            settings.forceOrdersOnTour = forceOrdersCheckBox.isSelected();

            //Open progress window and pass settings to it
            Main.gui.openWindowWithObject("GeneratorProgress", "Generating", settings);

            close();
        }
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        close();
    }

    private void setInteractionsDisabled(boolean disabled){
        planningChoiceBox.setDisable(disabled);
        regionChoiceBox.setDisable(disabled);
        allRegionsCheckBox.setDisable(disabled);
        datePicker.setDisable(disabled);
        allDatesCheckBox.setDisable(disabled);
        forceOrdersCheckBox.setDisable(disabled);
        cancelButton.setDisable(disabled);
        generateToursButton.setDisable(disabled);
    }

    private void close() {
        Stage window = (Stage) root.getScene().getWindow();
        Main.gui.closeWindow(window);
    }
}
