package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;

/*
The Tour menu, where all of the tours can be managed.
There are two tables, one for all of the tours, and one for the orders on the currently selected tour.
*/
public class TourListController {

    @FXML private Button editTourButton;
    @FXML private Button deleteTourButton;
    @FXML private Button printTourButton;
    @FXML private Button releaseTourButton;

    @FXML private ImageView loadingImage;

    //TOUR LIST table and columns
    @FXML private TableView<Tour> tourListTable;
    @FXML private TableColumn<Tour, Integer> TourID;
    @FXML private TableColumn<Tour, LocalDate> TourDate;
    @FXML private TableColumn<Tour, String> Region;
    @FXML private TableColumn<Tour, String> Driver;
    @FXML private TableColumn<Tour, String> LocalizedStatusString;
    @FXML private TableColumn<Tour, Boolean> Consignor;
    @FXML private TableColumn<Tour, Integer> OrderAmount;
    @FXML private TableColumn<Tour, Integer> TourTime;

    //TOUR ORDERS table and columns
    @FXML private TableView<Order> tourOrdersTable;
    @FXML private TableColumn<Tour, Integer> OrderID;
    @FXML private TableColumn<Tour, LocalDate> Date;
    @FXML private TableColumn<Tour, String> CustomerName;
    @FXML private TableColumn<Tour, String> Address;
    @FXML private TableColumn<Tour, Integer> ZipCode;
    @FXML private TableColumn<Tour, Integer> TotalTime;

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

        //load table inside scene for quicker scene swap
        loadTourTransition();
    }

    /*
    Pauses the application for a very short time,
    so that the contents inside the table are loaded AFTER the view is loaded,
    which let's us show a loading icon in the mean time.
    If we don't do this, the application will freeze BEFORE the view is loaded,
    without any loading indication, and makes it feel unresponsive.
    */
    private void loadTourTransition() {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.09));
        pauseTransition.setOnFinished(event -> {
            //load the tours into the tour list
            refreshTourList();

            //after the table contents have been loaded, we disable the loading icon
            loadingImage.setImage(null);
            loadingImage.setDisable(true);
        });

        pauseTransition.play();
    }

    /*
    Disables or enables the tour related buttons, depending on if a tour is selected
    */
    private void setTourButtonsDisabled(boolean disabled) {
        //disable these if no tour selected,
        //enable if tour selected
        deleteTourButton.setDisable(disabled);
        printTourButton.setDisable(disabled);

        //do the same for the following, but with extra critera

        //if the selected tour has been released, we shouldn't be able to edit it
        editTourButton.setDisable(disabled ||
                selectedTour.getStatus() == Tour.tourStatus.validReleased);

        //if the selected tour has been released, or if it doesn't have enough orders
        //we shouldn't be able to release it
        releaseTourButton.setDisable(disabled ||
                selectedTour.getOrders().size() < 1 ||
                selectedTour.getStatus() == Tour.tourStatus.validReleased);
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

    /*
    Clears the table, loads all tours into it, and clears selection
    */
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
        //Open the tour generator window
        Main.gui.openWindow("TourGenerator", "label_tourgen_title");

        //Refresh the tour list after the window has closed
        refreshTourList();
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }

    @FXML
    private void createEmptyTourButtonAction(ActionEvent event) {
        //Create new tour object
        Tour tour = new Tour();

        //Add tour to tour list
        tourListManager.addItem(tour);

        //Add tour to database
        TourManagement.createTour(tour);
    }

    @FXML
    private void editTourButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("EditTour", selectedTour, false);
    }

    @FXML
    private void deleteTourButtonAction(ActionEvent event) {
        //Show confirmation dialog
        boolean confirmed = Main.gui.showYesNoDialog("label_tourDel_confirmation_title", "message_tourDel_confirmation");

        if (confirmed){
            //Remove tour from database
            TourManagement.removeTour(selectedTour);

            //Remove tour from tour list
            tourListManager.removeItem(selectedTour);

            //If there are no more tours in the list, clear selection
            if (tourListManager.getItems().size() < 1) {
                onTourSelected(null, selectedTour,null);
                tourListManager.clearSelection();
            }
        }
    }

    @FXML
    private void printTourButtonAction(ActionEvent event) {
        //should print a tour plan for the selected tour
    }

    @FXML
    private void releaseTourButtonAction(ActionEvent event) {
        //Set tour status to released
        selectedTour.setStatus(Tour.tourStatus.validReleased);

        //Refresh tour list
        tourListManager.refresh();

        //Update tour status in database
        TourManagement.overrideTour(selectedTour);

        //Disable certain tour buttons, since tour is released
        setTourButtonsDisabled(false);
    }
}
