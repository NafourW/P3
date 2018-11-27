package dk.aau.cs.ds308e18.gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableManager {
    TableView table;

    public TableManager(TableView table) {
        this.table = table;
    }

    public void setupColumn(TableColumn col, String getter) {
        col.setCellValueFactory(new PropertyValueFactory<>(getter));
    }

    public void addRow() {

    }

    public void removeRow(int index) {

    }
}
