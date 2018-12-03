package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.WareManagement;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Ware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class WareListController {

    @FXML private TableView<Ware> wareListTable;

    @FXML private TableColumn<Ware, String> WareNumber;
    @FXML private TableColumn<Ware, String> WareType;
    @FXML private TableColumn<Ware, String> WareName;
    @FXML private TableColumn<Ware, String> SearchName;
    @FXML private TableColumn<Ware, String> Supplier;
    @FXML private TableColumn<Ware, Integer> GrossHeight;
    @FXML private TableColumn<Ware, Integer> GrossDepth;
    @FXML private TableColumn<Ware, Integer> GrossWidth;
    @FXML private TableColumn<Ware, Integer> WareGroup;
    @FXML private TableColumn<Ware, Boolean> LiftAlone;
    @FXML private TableColumn<Ware, Boolean> LiftingTools;
    @FXML private TableColumn<Ware, Float> MoveTime;

    private TableManager<Ware> wareListManager;

    private Ware selectedWare;

    @FXML
    public void initialize() {
        wareListManager = new TableManager<>(wareListTable);
        wareListManager.setMultiSelectEnabled(true);
        wareListManager.setupColumns();;

        for (Ware ware : WareManagement.getWares()) {
            wareListManager.addItem(ware);
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException {
        //if from order
        //Main.gui.changeView("EditOrder");
        //else
        Main.gui.changeView("MainMenu");
    }
}
