package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class TourListController {

    @FXML private Button editTourButton;
    @FXML private Button deleteTourButton;
    @FXML private Button printTourButton;
    @FXML private Button releaseTourButton;

    @FXML private TableView<Tour> tourListTable;
    @FXML private TableView<Order> tourOrdersTable;

    @FXML private TableColumn<Tour, String> tourDateColumn;
    @FXML private TableColumn<Tour, Integer> tourIDColumn;
    @FXML private TableColumn<Tour, String> tourRegionColumn;
    @FXML private TableColumn<Tour, String> tourDriverColumn;
    @FXML private TableColumn<Tour, Boolean> tourStatusColumn;
    @FXML private TableColumn<Tour, Boolean> tourConsignorColumn;

    private TableManager tourListManager;
    private TableManager tourOrdersManager;

    @FXML
    private void initialize(){
        tourDateColumn.setCellValueFactory(new PropertyValueFactory<>("TourDate"));
        tourIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tourRegionColumn.setCellValueFactory(new PropertyValueFactory<>("Region"));
        tourDriverColumn.setCellValueFactory(new PropertyValueFactory<>("Driver"));
        tourStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tourConsignorColumn.setCellValueFactory(new PropertyValueFactory<>("Consignor"));

        tourListManager = new TableManager(tourListTable);
        tourOrdersManager = new TableManager(tourOrdersTable);

        editTourButton.setDisable(true);
        deleteTourButton.setDisable(true);
        printTourButton.setDisable(true);
        releaseTourButton.setDisable(true);
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
    }

    @FXML
    private void editTourButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("EditTour");
    }

    @FXML
    private void deleteTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void printTourButtonAction(ActionEvent event) {

    }

    @FXML
    private void releaseTourButtonAction(ActionEvent event) {

    }
}
