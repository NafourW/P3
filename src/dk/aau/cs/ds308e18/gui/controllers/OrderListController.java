package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;

public class OrderListController {

    @FXML private Button editOrderButton;

    @FXML private TableView<Order> orderListTable;

    //Columns
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

        refreshOrderList();
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
        Main.gui.openWindow("TourGenerator", "label_tourgen_title");
        refreshOrderList();
    }

    @FXML
    private void createOrderButtonAction(ActionEvent event) throws IOException{
        selectedOrder = new Order();
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
