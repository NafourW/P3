package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Ware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class EditOrderController implements ISelectionController {

    @FXML private Button cancelOrderButton;
    @FXML private Button removeWareButton;

    @FXML private TextField customerNameField;
    @FXML private TextField addressField;
    @FXML private TextField zipCodeField;

    @FXML private DatePicker datePicker;
    @FXML private ComboBox regionComboBox;
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
        cancelOrderButton.setDisable(true);
        removeWareButton.setDisable(true);
    }

    private void populateFields(){
        customerNameField.setText(selectedOrder.getCustomerName());
        addressField.setText(selectedOrder.getAddress());
        //zipCodeField.setText(selectedOrder.getZipCode());

        //datePicker;
        //regionComboBox;
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

        //wareTable;
    }

    @FXML
    private void viewWareListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("WareList");
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void addWareToOrderButtonAction(ActionEvent event) {

    }

    @FXML
    private void removeWareButtonAction(ActionEvent event) {

    }

    @Override
    public void setSelectedObject(Object obj) {
        selectedOrder = (Order)obj;

        populateFields();
    }
}
