package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

public class EditTourController implements ISelectionController {

    @FXML private Button removeOrderButton;
    @FXML private Button moveOrderToTourButton;

    @FXML private TableView<Order> tourOrdersTable;
    @FXML private TableColumn<Tour, Integer> OrderID;
    @FXML private TableColumn<Tour, LocalDate> Date;
    @FXML private TableColumn<Tour, String> CustomerName;
    @FXML private TableColumn<Tour, String> Address;
    @FXML private TableColumn<Tour, Integer> ZipCode;
    @FXML private TableColumn<Tour, String> Region;
    @FXML private TableColumn<Tour, Integer> TotalTime;

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private TextField driverTextField;
    @FXML private CheckBox consignorCheckBox;

    private TableManager<Order> tourOrdersManager;
    private ObservableList<String> regions = FXCollections.observableArrayList();

    private Tour selectedTour;
    private Order selectedOrder;

    @FXML
    private void initialize(){
        removeOrderButton.setDisable(true);
        moveOrderToTourButton.setDisable(true);

        regions.addAll(Main.dbExport.exportRegionNames());
        regionComboBox.setItems(regions);

        //setup tables
        tourOrdersManager = new TableManager<>(tourOrdersTable);
        tourOrdersManager.setupColumns();

        //disable remove/movetotour buttons
        setOrderButtonsDisabled(true);

        //setup onOrderSelected method
        tourOrdersTable.getSelectionModel().selectedItemProperty().addListener(this::onOrderSelected);
    }

    private void transferFieldsToTour() {
        selectedTour.setTourDate(datePicker.getValue());
        selectedTour.setRegion(regionComboBox.getValue());
        selectedTour.setDriver(driverTextField.getText());
        selectedTour.setConsignor(consignorCheckBox.isSelected());

        TourManagement.overrideTour(selectedTour);
    }

    private void refreshOrderList() {
        tourOrdersManager.clearItems();

        for (Order order : selectedTour.getOrders())
            tourOrdersManager.addItem(order);
    }

    /*
    Called when an order is selected in the order list
    */
    private void onOrderSelected(ObservableValue<? extends Order> obs, Order oldSelection, Order newSelection) {
        //the selected item is assigned to selectedOrder
        selectedOrder = newSelection;

        //if the same thing was selected: do nothing
        if (oldSelection == newSelection)
            return;

        //enable/disable order buttons,
        //depending on whether an order is selected
        setOrderButtonsDisabled((selectedOrder == null));
    }

    private void setOrderButtonsDisabled(boolean disabled) {
        removeOrderButton.setDisable(disabled);
        moveOrderToTourButton.setDisable(disabled);
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) throws IOException{
        transferFieldsToTour();
        Main.gui.changeView("AddOrderToTour", selectedTour, false);
    }

    @FXML
    private void removeOrderButtonAction(ActionEvent event) {
        OrderManagement.setTourID(0, selectedOrder.getOrderID());
        selectedTour.removeOrder(selectedOrder);
        refreshOrderList();
    }

    @FXML
    private void moveOrderToTourButtonAction(ActionEvent event) throws IOException{
        transferFieldsToTour();
        Main.gui.changeView("MoveOrderToTour", selectedOrder, false);
    }

    @FXML
    private void doneButtonAction(ActionEvent event) throws IOException {
        transferFieldsToTour();
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
