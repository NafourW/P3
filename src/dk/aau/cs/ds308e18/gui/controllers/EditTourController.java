package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditTourController implements ISelectionController {

    @FXML private Button removeOrderButton;
    @FXML private Button moveOrderToTourButton;

    @FXML private TableView<Order> tourOrdersTable;
    @FXML private TableColumn<Tour, Integer> OrderID;
    @FXML private TableColumn<Tour, String> CustomerName;
    @FXML private TableColumn<Tour, String> Address;
    @FXML private TableColumn<Tour, Integer> ZipCode;

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private TextField driverTextField;
    @FXML private CheckBox consignorCheckBox;

    private TableManager<Order> tourOrdersManager;
    private ObservableList<String> regions = FXCollections.observableArrayList();

    private Tour selectedTour;

    @FXML
    private void initialize(){
        removeOrderButton.setDisable(true);
        moveOrderToTourButton.setDisable(true);

        regions.addAll(Main.dbExport.exportRegionNames());
        regionComboBox.setItems(regions);

        //setup tables
        tourOrdersManager = new TableManager<>(tourOrdersTable);
        tourOrdersManager.setupColumns();
    }

    private void transferFieldsToTour() {
        selectedTour.setTourDate(datePicker.getValue());
        selectedTour.setRegion(regionComboBox.getValue());
        selectedTour.setDriver(driverTextField.getText());
        selectedTour.setConsignor(consignorCheckBox.isSelected());
    }

    private void refreshOrderList() {
        tourOrdersManager.clearItems();

        for (Order order : selectedTour.getOrders())
            tourOrdersManager.addItem(order);
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("AddOrderToTour", selectedTour, false);
        refreshOrderList();
    }

    @FXML
    private void removeOrderButtonAction(ActionEvent event) {
        
    }

    @FXML
    private void moveOrderToTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void doneButtonAction(ActionEvent event) throws IOException {
        transferFieldsToTour();
        TourManagement.overrideTour(selectedTour);
        Main.gui.changeView("TourList");
    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        selectedTour = (Tour)obj;

        if (selectedTour != null) {
            datePicker.setValue(selectedTour.getTourDate());
            regionComboBox.getSelectionModel().select(selectedTour.getRegion());

            String driver = selectedTour.getDriver();
            if (driver != null)
                driverTextField.setText(driver);

            consignorCheckBox.setSelected(selectedTour.getConsignor());

            refreshOrderList();
        }
    }
}
