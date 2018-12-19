package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
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
The Order menu, where all of the unassigned orders can be managed.
*/
public class OrderListController {

    @FXML private Button editOrderButton;

    @FXML private ImageView loadingImage;

    //ORDER LIST table and columns
    @FXML private TableView<Order> orderListTable;
    @FXML private TableColumn<Order, Integer> OrderID;
    @FXML private TableColumn<Order, Integer> ID;
    @FXML private TableColumn<Order, LocalDate> Date;
    @FXML private TableColumn<Order, Integer> WeekNumber;
    @FXML private TableColumn<Order, String> Region;
    @FXML private TableColumn<Order, String> Address;
    @FXML private TableColumn<Order, Integer> ZipCode;
    @FXML private TableColumn<Order, String> CustomerName;
    @FXML private TableColumn<Order, Integer> OrderLineAmount;
    @FXML private TableColumn<Order, Integer> PluckRoute;
    @FXML private TableColumn<Order, String> OrderReference;
    @FXML private TableColumn<Order, Integer> Receipt;
    @FXML private TableColumn<Order, String> Warehouse;
    @FXML private TableColumn<Order, String> LocalizedCategoryString;
    @FXML private TableColumn<Order, String> FleetOwner;
    @FXML private TableColumn<Order, Boolean> Printed;
    @FXML private TableColumn<Order, String> Project;
    @FXML private TableColumn<Order, Integer> TotalTime;
    @FXML private TableColumn<Order, Boolean> TotalLiftAlone;
    @FXML private TableColumn<Order, Boolean> TotalLiftingTools;

    private TableManager<Order> orderListManager;

    private Order selectedOrder;

    @FXML
    public void initialize() {
        orderListManager = new TableManager<>(orderListTable);
        orderListManager.setMultiSelectEnabled(true);
        orderListManager.setupColumns();

        editOrderButton.setDisable(true);

        //setup onOrderSelected method
        orderListTable.getSelectionModel().selectedItemProperty().addListener(this::onOrderSelected);

        //load table inside scene for quicker scene swap
        loadOrderTransition();
    }

    /*
    Pauses the application for a very short time,
    so that the contents inside the table are loaded AFTER the view is loaded,
    which let's us show a loading icon in the mean time.
    If we don't do this, the application will freeze BEFORE the view is loaded,
    without any loading indication, and makes it feel unresponsive.
    */
    private void loadOrderTransition() {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.09));
        pauseTransition.setOnFinished(event -> {
            //load the orders into the order list
            refreshOrderList();

            //after the table contents have been loaded, we disable the loading icon
            loadingImage.setImage(null);
            loadingImage.setDisable(true);
        });

        pauseTransition.play();
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

        //enable/disable edit order button,
        //depending on whether an order is selected
        editOrderButton.setDisable(selectedOrder == null);
    }

    private void refreshOrderList(){
        orderListManager.clearItems();
        orderListManager.addItems(OrderManagement.getUnassignedOrders());
    }

    @FXML
    private void tourListButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("TourList");
    }

    @FXML
    private void generateToursButtonAction(ActionEvent event) throws IOException {
        //Open the tour generator window
        Main.gui.openWindow("TourGenerator", "label_tourgen_title");

        //Refresh the tour list after the window has closed
        refreshOrderList();
    }

    @FXML
    private void createOrderButtonAction(ActionEvent event) throws IOException{
        //Create new order object
        selectedOrder = new Order();

        //Open edit order view with the new order selected
        Main.gui.changeView("EditOrder", selectedOrder, true);
    }

    @FXML
    private void editOrderButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("EditOrder", selectedOrder, false);
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }
}
