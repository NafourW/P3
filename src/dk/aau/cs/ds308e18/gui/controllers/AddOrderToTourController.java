package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.tourgen.TourGenerator;
import dk.aau.cs.ds308e18.function.tourgen.TourGeneratorSettings;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddOrderToTourController implements ISelectionController {

    @FXML private VBox root;

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
    @FXML private TableColumn<Order, Integer> PluckRoute;
    @FXML private TableColumn<Order, String> OrderReference;
    @FXML private TableColumn<Order, Integer> Receipt;
    @FXML private TableColumn<Order, String> Warehouse;
    @FXML private TableColumn<Order, String> Category;
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

        //TODO: should the orders' tourIDs get changed here??
        for (Order o : orderListTable.getSelectionModel().getSelectedItems())
            selectedTour.addOrder(o);

        Main.gui.changeView("EditTour", selectedTour, false);
    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        selectedTour = (Tour)obj;
    }
}
