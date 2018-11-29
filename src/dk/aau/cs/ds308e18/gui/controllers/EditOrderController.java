package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Ware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EditOrderController implements ISelectionController {

    @FXML private HBox buttonBar;
    @FXML private VBox buttonBarLeft;

    @FXML private Button removeWareButton;
    @FXML private Button cancelOrderButton;
    @FXML private Button addOrderButton;
    @FXML private Button doneButton;

    @FXML private TextField customerNameField;
    @FXML private TextField addressField;
    @FXML private TextField zipCodeField;

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> regionComboBox;
    private ObservableList<String> regions = FXCollections.observableArrayList();
    @FXML private ComboBox orderCategoryComboBox;

    @FXML private TextArea commentsArea;

    @FXML private ComboBox wareTypeComboBox;
    @FXML private ComboBox searchNameComboBox;
    @FXML private TextField wareNameField;
    @FXML private TextField supplierField;

    @FXML private TextField wareNumberField;
    @FXML private TextField lengthField;
    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private TextField weightField;

    @FXML private TextField amountField;
    @FXML private TextField priceField;

    @FXML private TableView<Ware> wareTable;

    private Order selectedOrder;

    @FXML
    public void initialize() {
        removeWareButton.setDisable(true);

        regions.addAll(Main.db.exportRegionNames());
        regionComboBox.setItems(regions);
    }

    private void populateFields(){
        customerNameField.setText(selectedOrder.getCustomerName());
        addressField.setText(selectedOrder.getAddress());
        zipCodeField.setText(String.valueOf(selectedOrder.getZipCode()));

        datePicker.setValue(selectedOrder.getDate());
        regionComboBox.getSelectionModel().select(selectedOrder.getRegion());
        //orderCategoryComboBox;

        commentsArea.setText("");

        //wareTypeComboBox;
        //searchNameComboBox;
        wareNameField.setText("");
        supplierField.setText("");

        wareNumberField.setText("");
        lengthField.setText("");
        widthField.setText("");
        heightField.setText("");
        weightField.setText("");

        amountField.setText("");
        priceField.setText("");

        wareTable.getItems().addAll(selectedOrder.getWares());
    }

    @FXML
    private void  cancelOrderButtonAction(ActionEvent event){

    }

    @FXML
    private void viewWareListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("WareList");
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) throws IOException {
        //TODO: Add order
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void doneButtonAction(ActionEvent event) throws IOException {
        //TODO: save changes to order
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void addWareToOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void removeWareButtonAction(ActionEvent event) {

    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        selectedOrder = (Order)obj;
        populateFields();
        if (isNew) {
            buttonBarLeft.getChildren().remove(cancelOrderButton);
            buttonBar.getChildren().remove(doneButton);
        }
        else {
            buttonBar.getChildren().remove(addOrderButton);
        }
    }
}
