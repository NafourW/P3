package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class TourListController {

    @FXML private Button editTourButton;
    @FXML private Button deleteTourButton;
    @FXML private Button printTourButton;
    @FXML private Button releaseTourButton;

    @FXML private TableView<Tour> tourListTable;
    @FXML private TableColumn<Tour, String> tourDateColumn;
    @FXML private TableColumn<Tour, Integer> tourIDColumn;
    @FXML private TableColumn<Tour, String> tourRegionColumn;
    @FXML private TableColumn<Tour, String> tourDriverColumn;
    @FXML private TableColumn<Tour, Boolean> tourStatusColumn;
    @FXML private TableColumn<Tour, Boolean> tourConsignorColumn;

    @FXML private TableView<Order> tourOrdersTable;
    @FXML private TableColumn<Tour, Integer> orderIDColumn;
    @FXML private TableColumn<Tour, String> orderNameColumn;
    @FXML private TableColumn<Tour, String> orderAddressColumn;
    @FXML private TableColumn<Tour, Integer> orderZipCodeColumn;

    private TableManager<Tour> tourListManager;
    private TableManager<Order> tourOrdersManager;

    private Tour selectedTour;

    @FXML
    private void initialize(){
        //setup tables
        tourListManager = new TableManager<>(tourListTable);
        tourListManager.setupColumn(tourDateColumn, "TourDate");
        tourListManager.setupColumn(tourIDColumn, "ID");
        tourListManager.setupColumn(tourRegionColumn, "Region");
        tourListManager.setupColumn(tourDriverColumn, "Driver");
        tourListManager.setupColumn(tourStatusColumn, "Status");
        tourListManager.setupColumn(tourConsignorColumn, "Consignor");

        tourOrdersManager = new TableManager<>(tourOrdersTable);
        tourOrdersManager.setupColumn(orderIDColumn, "ID");
        tourOrdersManager.setupColumn(orderNameColumn, "CustomerName");
        tourOrdersManager.setupColumn(orderAddressColumn, "Address");
        tourOrdersManager.setupColumn(orderZipCodeColumn, "ZipCode");

        //disable tour buttons
        setTourButtonsDisabled(true);

        //setup onTourSelected method
        tourListTable.getSelectionModel().selectedItemProperty().addListener(this::onTourSelected);

        //no tour currently selected
        selectedTour = null;
    }

    private void setTourButtonsDisabled(boolean disabled) {
        editTourButton.setDisable(disabled);
        deleteTourButton.setDisable(disabled);
        printTourButton.setDisable(disabled);
        releaseTourButton.setDisable(disabled);
    }

    /*
    Called when a tour is selected in the tour list
    */
    private void onTourSelected(ObservableValue<? extends Tour> obs, Tour oldSelection, Tour newSelection) {
        //the selected item is assigned to selectedTour
        selectedTour = newSelection;

        //if the same thing was selected: do nothing
        if (oldSelection == newSelection)
            return;

        //clear the order list
        tourOrdersManager.clearSelection();
        tourOrdersManager.clearItems();

        //enable/disable tour buttons,
        //depending on whether a tour is selected
        setTourButtonsDisabled((selectedTour == null));

        //if a tour is selected,
        //display its orders in the order list
        if (selectedTour != null){
            //TODO: get orders from tour
            Order order = new Order();
            tourOrdersManager.addItem(order);
        }
    }

    @FXML
    private void orderListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }

    @FXML
    private void createEmptyTourButtonAction(ActionEvent event) {
        Tour tour = new Tour();
        tourListTable.getItems().add(tour);
        //TODO: add tour to database
    }

    @FXML
    private void editTourButtonAction(ActionEvent event) throws IOException {
        //TODO: use selectedTour
        Main.gui.changeView("EditTour");
    }

    @FXML
    private void deleteTourButtonAction(ActionEvent event) {
        tourListManager.removeItem(selectedTour);
        onTourSelected(null, selectedTour,null);
        tourListManager.clearSelection();
        //TODO: remove tour from database
    }

    @FXML
    private void printTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void releaseTourButtonAction(ActionEvent event) {

    }
}
