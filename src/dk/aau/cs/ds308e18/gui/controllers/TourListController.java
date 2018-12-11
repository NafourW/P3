package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.TourManagement;
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
import java.time.LocalDate;

public class TourListController {

    @FXML private Button editTourButton;
    @FXML private Button deleteTourButton;
    @FXML private Button printTourButton;
    @FXML private Button releaseTourButton;

    @FXML private TableView<Tour> tourListTable;
    @FXML private TableColumn<Tour, Integer> TourID;
    @FXML private TableColumn<Tour, LocalDate> TourDate;
    @FXML private TableColumn<Tour, String> Region;
    @FXML private TableColumn<Tour, String> Driver;
    @FXML private TableColumn<Tour, Boolean> Status;
    @FXML private TableColumn<Tour, Boolean> Consignor;
    @FXML private TableColumn<Tour, Integer> OrderAmount;

    @FXML private TableView<Order> tourOrdersTable;
    @FXML private TableColumn<Tour, Integer> OrderID;
    @FXML private TableColumn<Tour, LocalDate> Date;
    @FXML private TableColumn<Tour, String> CustomerName;
    @FXML private TableColumn<Tour, String> Address;
    @FXML private TableColumn<Tour, Integer> ZipCode;

    private TableManager<Tour> tourListManager;
    private TableManager<Order> tourOrdersManager;

    private Tour selectedTour = null;

    @FXML
    private void initialize(){
        //setup tables
        tourListManager = new TableManager<>(tourListTable);
        tourListManager.setupColumns();

        tourOrdersManager = new TableManager<>(tourOrdersTable);
        tourOrdersManager.setupColumns();

        //disable tour buttons
        setTourButtonsDisabled(true);

        //setup onTourSelected method
        tourListTable.getSelectionModel().selectedItemProperty().addListener(this::onTourSelected);

        refreshTourList();
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
            for (Order order : selectedTour.getOrders()) {
                tourOrdersManager.addItem(order);
            }
        }
    }

    private void refreshTourList() {
        tourListManager.clearItems();
        tourListManager.addItems(TourManagement.getTours());

        onTourSelected(null, selectedTour,null);
        tourListManager.clearSelection();
    }

    @FXML
    private void orderListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void generateToursButtonAction(ActionEvent event) throws IOException {
        Main.gui.openWindow("TourGenerator", "label_tourgen_title");
        refreshTourList();
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }

    @FXML
    private void createEmptyTourButtonAction(ActionEvent event) {
        Tour tour = new Tour();
        tourListTable.getItems().add(tour);
        TourManagement.createTour(tour);
    }

    @FXML
    private void editTourButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("EditTour", selectedTour, false);
    }

    @FXML
    private void deleteTourButtonAction(ActionEvent event) {
        TourManagement.removeTour(selectedTour);
        tourListManager.removeItem(selectedTour);

        if (tourListManager.getItems().size() < 1) {
            onTourSelected(null, selectedTour,null);
            tourListManager.clearSelection();
        }
    }

    @FXML
    private void printTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void releaseTourButtonAction(ActionEvent event) {

    }
}
