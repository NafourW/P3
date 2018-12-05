package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.TourGenerator;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
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
import java.util.ArrayList;

public class TourGeneratorController {

    @FXML private VBox root;

    @FXML private ChoiceBox<TourGenerator.planningMethod> planningChoiceBox;
    @FXML private ChoiceBox<String> regionChoiceBox;
    @FXML private CheckBox allRegionsCheckBox;
    @FXML private DatePicker datePicker;
    @FXML private CheckBox allDatesCheckBox;

    private ObservableList<String> regions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        planningChoiceBox.getItems().setAll(TourGenerator.planningMethod.values());
        planningChoiceBox.setValue(TourGenerator.planningMethod.leastTime);

        regions.addAll(Main.dbExport.exportRegionNames());
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

            String region = (allRegionsCheckBox.isSelected()) ? null : regionChoiceBox.getValue();
            LocalDate date = (allDatesCheckBox.isSelected()) ? null :datePicker.getValue();

            System.out.println("Fetching orders...");
            ArrayList<Order> orders = OrderManagement.getUnassignedOrders(region, date);

            System.out.println("Generating tours...");
            ArrayList<Tour> tours = TourGenerator.generateTours(orders, planningChoiceBox.getValue());

            System.out.println("Generated " + tours.size() + " tours:");
            for (Tour tour : tours)
                System.out.println(tour.getTourDate() + " - " + tour.getOrders().size() + " orders");

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
