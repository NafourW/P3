package dk.aau.cs.ds308e18.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/*
Class that handles a TableView which shows items with type S
*/
public class TableManager<S> {
    TableView table;

    public TableManager(TableView<S> table) {
        this.table = table;
    }

    /*
    Makes the TableColumn automatically retrieve data from the table item
    using the .get[x]() method from S where [x] is replaced with the getter String
    */
    public void setupColumn(TableColumn col, String getter) {
        col.setCellValueFactory(new PropertyValueFactory<>(getter));
    }

    /*
    Makes the TableColumns automatically retrieve data from the table item
    Uses the .get[x]() method from the item's class where [x] is replaced with the column id
    */
    public void setupColumns() {
        ObservableList<TableColumn> columns = table.getColumns();
        for (int i = 0; i < table.getColumns().size(); i++){
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(columns.get(i).getId()));
        }
    }

    public void addItem(S item) {
        table.getItems().add(item);
    }

    public void addItems(ArrayList<S> items) {
        table.getItems().addAll(items);
    }

    public void removeItem(S item) {
        table.getItems().remove(item);
    }

    public void removeItem(int index) {
        table.getItems().remove(index);
    }

    public void clearItems() {
        table.getItems().clear();
    }

    public void clearSelection() {
        table.getSelectionModel().clearSelection();
    }

    public void setMultiSelectEnabled(boolean enabled) {
        if (enabled)
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        else
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
