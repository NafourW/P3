package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class EditOrderController implements ISelectionController {

    @FXML private HBox buttonBar;
    @FXML private VBox buttonBarLeft;

    @FXML private Label titleLabel;
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

    @FXML private TableView<OrderLine> orderLineTable;

    @FXML private TableColumn<OrderLine, String> Labels;
    @FXML private TableColumn<OrderLine, String> Delivered;
    @FXML private TableColumn<OrderLine, String> WareNumber;
    @FXML private TableColumn<OrderLine, String> WareName;
    @FXML private TableColumn<OrderLine, String> Individual;
    @FXML private TableColumn<OrderLine, String> IndividualNumber;
    @FXML private TableColumn<OrderLine, String> Model;
    @FXML private TableColumn<OrderLine, String> Name;
    @FXML private TableColumn<OrderLine, String> MoveTime;
    @FXML private TableColumn<OrderLine, String> LiftAlone;
    @FXML private TableColumn<OrderLine, String> LiftEquipment;

    private TableManager<OrderLine> orderLineTableManager;

    private Order selectedOrder;
    private OrderLine selectedOrderLine;

    @FXML
    public void initialize() {

        orderLineTableManager = new TableManager<>(orderLineTable);
        orderLineTableManager.setMultiSelectEnabled(true);
        orderLineTableManager.setupColumns();

        removeWareButton.setDisable(true);

        regions.addAll(Main.dbExport.exportRegionNames());
        regionComboBox.setItems(regions);

        //setup onOrderLineSelected method
        orderLineTable.getSelectionModel().selectedItemProperty().addListener(this::onOrderLineSelected);
    }

    private void populateOrderFields(){
        customerNameField.setText(selectedOrder.getCustomerName());
        addressField.setText(selectedOrder.getAddress());
        zipCodeField.setText(String.valueOf(selectedOrder.getZipCode()));

        datePicker.setValue(selectedOrder.getDate());
        regionComboBox.getSelectionModel().select(selectedOrder.getRegion());
        //orderCategoryComboBox;

        commentsArea.setText("");

        refreshOrderLineList();
    }

    private void refreshOrderLineList() {
        orderLineTableManager.clearItems();
        orderLineTableManager.addItems(selectedOrder.getOrderLines());
    }

    private void populateWareFields(){
        //wareTypeComboBox;
        //searchNameComboBox;
        wareNameField.setText("");
        wareNumberField.setText("");
        amountField.setText("");
    }

    private void transferFieldsToOrder() {
        selectedOrder.setCustomerName(customerNameField.getText());
        selectedOrder.setAddress(addressField.getText());
        selectedOrder.setZipCode(Integer.valueOf(zipCodeField.getText()));

        selectedOrder.setDate(datePicker.getValue());
        selectedOrder.setRegion(regionComboBox.getValue());
        //orderCategoryComboBox;

        commentsArea.getText();

        ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
        orderLines.addAll(orderLineTable.getItems());

        selectedOrder.setOrderLines(orderLines);
    }

    /*
    Called when an orderline is selected in the orderline list
    */
    private void onOrderLineSelected(ObservableValue<? extends OrderLine> obs, OrderLine oldSelection, OrderLine newSelection) {
        //the selected item is assigned to selectedOrderLine
        selectedOrderLine = newSelection;

        //if the same thing was selected: do nothing
        if (oldSelection == newSelection)
            return;

        //enable/disable remove button,
        //depending on whether an orderline is selected
        removeWareButton.setDisable((selectedOrderLine == null));
    }

    @FXML
    private void cancelOrderButtonAction(ActionEvent event) throws IOException{
        boolean confirmed = Main.gui.showYesNoDialog("label_orderDel_confirmation_title", "message_orderDel_confirmation");

        if (confirmed){
            OrderManagement.deleteOrderFromDatabase(selectedOrder);
            Main.gui.changeView("OrderList");
        }
    }

    @FXML
    private void viewWareListButtonAction(ActionEvent event) throws IOException {
        Main.gui.changeView("WareList");
    }

    @FXML
    private void addOrderButtonAction(ActionEvent event) throws IOException {
        transferFieldsToOrder();
        OrderManagement.createOrder(selectedOrder);
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void doneButtonAction(ActionEvent event) throws IOException {
        transferFieldsToOrder();
        OrderManagement.overrideOrder(selectedOrder);
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("OrderList");
    }

    @FXML
    private void addWareToOrderButtonAction(ActionEvent event) {
        OrderLine newOrderLine = new OrderLine();

        newOrderLine.setOrder(selectedOrder.getID());
        newOrderLine.setWareNumber(wareNumberField.getText());
        newOrderLine.setWareName(wareNameField.getText());

        selectedOrder.addOrderLine(newOrderLine);
        refreshOrderLineList();
    }

    @FXML
    private void removeWareButtonAction(ActionEvent event) {
        selectedOrder.removeOrderLine(selectedOrderLine);
        refreshOrderLineList();
    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        selectedOrder = (Order)obj;
        populateOrderFields();
        populateWareFields();
        if (isNew) {
            buttonBarLeft.getChildren().remove(cancelOrderButton);
            buttonBar.getChildren().remove(doneButton);
        }
        else {
            buttonBar.getChildren().remove(addOrderButton);
            titleLabel.setText(Main.gui.getLocalString("label_title_edit_order"));
        }
    }
}
