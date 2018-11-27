package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class OrderListController {

    @FXML private Button editOrderButton;

    @FXML private TableView orderListTable;
    @FXML private TableColumn selectedColumn;
    @FXML private TableColumn idColumn;
    @FXML private TableColumn dateColumn;
    @FXML private TableColumn regionColumn;
    @FXML private TableColumn addressColumn;
    @FXML private TableColumn zipCodeColumn;
    @FXML private TableColumn customerNameColumn;
    @FXML private TableColumn pluckRouteColumn;
    @FXML private TableColumn orderReferenceColumn;
    @FXML private TableColumn receiptColumn;
    @FXML private TableColumn warehouseColumn;
    @FXML private TableColumn categoryColumn;
    @FXML private TableColumn fleetOwnerColumn;
    @FXML private TableColumn printedColumn;
    @FXML private TableColumn projectColumn;

    private TableManager orderListManager;

    @FXML
    public void initialize() {
        orderListManager = new TableManager(orderListTable);
        orderListManager.setupColumn(idColumn, "ID");
        orderListManager.setupColumn(dateColumn, "Date");
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
        //1. create order
        //2. select order
        //3.
        Main.gui.changeView("EditOrder");
    }

    @FXML
    private void editOrderButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("EditOrder");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }
}
