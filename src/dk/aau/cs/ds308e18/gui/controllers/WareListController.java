package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.WareManagement;
import dk.aau.cs.ds308e18.gui.TableManager;
import dk.aau.cs.ds308e18.model.Ware;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

/*
The Ware menu, where all of the wares can be viewed.
*/
public class WareListController {

    @FXML private ImageView loadingImage;

    //WARE LIST table and columns
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

    @FXML
    public void initialize() {
        wareListManager = new TableManager<>(wareListTable);
        wareListManager.setMultiSelectEnabled(true);
        wareListManager.setupColumns();

        wareListTable.setSelectionModel(null);

        //load table inside scene for quicker scene swap
        loadWareTransition();
    }

    /*
    Pauses the application for a very short time,
    so that the contents inside the table are loaded AFTER the view is loaded,
    which let's us show a loading icon in the mean time.
    If we don't do this, the application will freeze BEFORE the view is loaded,
    without any loading indication, and makes it feel unresponsive.
    */
    private void loadWareTransition() {
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.09));
        pauseTransition.setOnFinished(event -> {
            //load the wares into the ware list
            for (Ware ware : WareManagement.getWares())
                wareListManager.addItem(ware);

            //after the table contents have been loaded, we disable the loading icon
            loadingImage.setImage(null);
            loadingImage.setDisable(true);
        });

        pauseTransition.play();
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException {
        Main.gui.goToPreviousView();
    }
}
