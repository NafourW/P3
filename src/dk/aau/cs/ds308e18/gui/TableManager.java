package dk.aau.cs.ds308e18.gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void addItem(S item) {
        table.getItems().add(item);
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
}
