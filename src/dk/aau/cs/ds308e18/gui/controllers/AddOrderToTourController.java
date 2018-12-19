package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;

public class AddOrderToTourController implements ISelectionController {

    @FXML private VBox root;

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

    Tour selectedTour;

    @FXML
    public void initialize() {
        orderListManager = new TableManager<>(orderListTable);
        orderListManager.setMultiSelectEnabled(true);
        orderListManager.setupColumns();

        orderListManager.addItems(OrderManagement.getUnassignedOrders());
    }

    @FXML
    private void doneButtonAction(ActionEvent event) throws IOException {

        for (Order o : orderListTable.getSelectionModel().getSelectedItems())
            selectedTour.addOrder(o);

        Main.gui.goToPreviousView();
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        Main.gui.goToPreviousView();
    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        selectedTour = (Tour)obj;
    }
}
