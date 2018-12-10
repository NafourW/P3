package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;

public class MoveOrderToTourController implements ISelectionController {

    @FXML private VBox root;

    @FXML private TableView<Tour> tourListTable;
    @FXML private TableColumn<Tour, String> TourDate;
    @FXML private TableColumn<Tour, Integer> TourID;
    @FXML private TableColumn<Tour, String> Region;
    @FXML private TableColumn<Tour, String> Driver;
    @FXML private TableColumn<Tour, Boolean> Status;
    @FXML private TableColumn<Tour, Boolean> Consignor;
    @FXML private TableColumn<Tour, Integer> OrderAmount;

    private TableManager<Tour> tourListManager;

    Tour selectedTour;

    Order selectedOrder;

    @FXML
    public void initialize() {
        tourListManager = new TableManager<>(tourListTable);
        tourListManager.setupColumns();

        //setup onTourSelected method
        tourListTable.getSelectionModel().selectedItemProperty().addListener(this::onTourSelected);

        tourListManager.addItems(TourManagement.getTours());
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
    }

    @FXML
    private void moveButtonAction(ActionEvent event) throws IOException {
        OrderManagement.setTourID(selectedTour.getTourID(), selectedOrder.getOrderID());

        //Hacky stuff
        //Tour previousTour = (Tour)Main.gui.getPreviousSelection();
        //Main.gui.changeView("EditTour", TourManagement.getTourFromTourID(previousTour.getTourID()), false);
        Main.gui.changeView("TourList");
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        Main.gui.goToPreviousView();
    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        selectedOrder = (Order)obj;
    }
}
