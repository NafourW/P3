package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
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
    @FXML private TableColumn selectedColumn;
    @FXML private TableColumn<Order, Integer> idColumn;
    @FXML private TableColumn<Order, LocalDate> dateColumn;
    @FXML private TableColumn<Order, LocalDate> weekColumn;
    @FXML private TableColumn<Order, String> regionColumn;
    @FXML private TableColumn<Order, String> addressColumn;
    @FXML private TableColumn<Order, Integer> zipCodeColumn;
    @FXML private TableColumn<Order, String> customerNameColumn;
    @FXML private TableColumn<Order, Integer> pluckRouteColumn;
    @FXML private TableColumn<Order, String> orderReferenceColumn;
    @FXML private TableColumn<Order, Integer> receiptColumn;
    @FXML private TableColumn<Order, String> warehouseColumn;
    @FXML private TableColumn<Order, String> categoryColumn;
    @FXML private TableColumn<Order, String> fleetOwnerColumn;
    @FXML private TableColumn<Order, Boolean> printedColumn;
    @FXML private TableColumn<Order, String> projectColumn;

    private TableManager<Order> orderListManager;

    private Order selectedOrder;

    @FXML
    public void initialize() {
        orderListManager = new TableManager<>(orderListTable);
        orderListManager.setMultiSelectEnabled(true);
        orderListManager.setupColumn(idColumn, "ID");
        orderListManager.setupColumn(dateColumn, "Date");
        orderListManager.setupColumn(weekColumn, "WeekNumber");
        orderListManager.setupColumn(regionColumn, "Region");
        orderListManager.setupColumn(addressColumn, "Address");
        orderListManager.setupColumn(zipCodeColumn, "ZipCode");
        orderListManager.setupColumn(customerNameColumn, "CustomerName");
        orderListManager.setupColumn(pluckRouteColumn, "PluckRoute");
        orderListManager.setupColumn(orderReferenceColumn, "OrderReference");
        orderListManager.setupColumn(receiptColumn, "Receipt");
        orderListManager.setupColumn(warehouseColumn, "Warehouse");
        orderListManager.setupColumn(categoryColumn, "Category");
        orderListManager.setupColumn(fleetOwnerColumn, "FleetOwner");
        orderListManager.setupColumn(printedColumn, "Printed");
        orderListManager.setupColumn(projectColumn, "Project");

        editOrderButton.setDisable(true);

        //setup onOrderSelected method
        orderListTable.getSelectionModel().selectedItemProperty().addListener(this::onOrderSelected);

        for (Order order : Main.db.exportOrders()) {
            orderListManager.addItem(order);
        }
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

    @FXML
    private void tourListButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("TourList");
    }

    @FXML
    private void generateToursButtonAction(ActionEvent event) throws IOException {
        Main.gui.openWindow("TourGenerator", "label_tourgen_title");
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
