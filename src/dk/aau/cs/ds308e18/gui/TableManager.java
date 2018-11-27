package dk.aau.cs.ds308e18.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableManager<S> {
    TableView table;

    public TableManager(TableView<S> table) {
        this.table = table;
    }

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
