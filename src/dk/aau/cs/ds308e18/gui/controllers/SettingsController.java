package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.io.ExportFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.prefs.Preferences;

public class SettingsController {

    @FXML private VBox settingMenuID;

    @FXML private ComboBox<Locale> languageSelector;
    @FXML private Button importDataButton;
    @FXML private Button exportDataButton;
    @FXML private Button sourceBrowseButton;
    @FXML private Button destinationBrowseButton;
    @FXML private Button clearAllToursButton;
    @FXML private Button refreshDatabaseButton;
    @FXML private Button backButton;

    @FXML private TextField sourceField;
    @FXML private TextField destinationField;

    @FXML private Label importProgressLabel;
    @FXML private Label exportProgressLabel;

    private String sourcePath;
    private String destinationPath;

    private ObservableList<Locale> languages = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        //Add english and danish to language combobox
        languages.addAll(new Locale("en","US"), new Locale("da","DA"));
        languageSelector.setItems(languages);

        //Use previous values for import/export directories
        Preferences prefs = Preferences.userNodeForPackage(dk.aau.cs.ds308e18.Main.class);
        setSourcePath(prefs.get("dataImportSourceDirectory", ""));
        setDestinationPath(prefs.get("dataExportDestinationDirectory", ""));
    }

    private void setSourcePath(String path) {
        sourcePath = path;
        sourceField.setText(path);

        Preferences prefs = Preferences.userNodeForPackage(dk.aau.cs.ds308e18.Main.class);
        prefs.put("dataImportSourceDirectory", path);
    }

    private void setDestinationPath(String path) {
        destinationPath = path;
        destinationField.setText(path);

        Preferences prefs = Preferences.userNodeForPackage(dk.aau.cs.ds308e18.Main.class);
        prefs.put("dataExportDestinationDirectory", path);
    }

    private void setButtonsDisabled(boolean disabled) {
        languageSelector.setDisable(disabled);
        importDataButton.setDisable(disabled);
        exportDataButton.setDisable(disabled);
        sourceBrowseButton.setDisable(disabled);
        destinationBrowseButton.setDisable(disabled);
        clearAllToursButton.setDisable(disabled);
        refreshDatabaseButton.setDisable(disabled);
        backButton.setDisable(disabled);
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }

    @FXML
    private void languageComboBoxAction(ActionEvent event) throws IOException {
        //Get selected language from combobox
        Locale selectedLanguage = languageSelector.getSelectionModel().getSelectedItem();
        //Set selected language in GUI
        Main.gui.setLanguage(selectedLanguage);
        //Refresh the GUI with the new language
        Main.gui.refreshView();
    }

    @FXML
    private void importDataButtonAction(ActionEvent event) {
        Task<String> importTask = new Task<String>() {
            @Override protected String call() throws Exception {
                setButtonsDisabled(true);

                updateMessage("Importing Wares... ");
                Main.dbImport.importWares(sourcePath);

                updateMessage("Importing Orders... ");
                Main.dbImport.importOrders(sourcePath);

                updateMessage("Importing Orderlines... ");
                Main.dbImport.importOrderLines(sourcePath);

                updateMessage("Import done.");
                setButtonsDisabled(false);
                return "";
            }
        };

        importProgressLabel.textProperty().bind(importTask.messageProperty());

        Thread th = new Thread(importTask);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    private void sourceBrowseButtonAction(ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) settingMenuID.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null)
            setSourcePath(selectedDirectory.getAbsolutePath());
    }

    @FXML
    private void exportDataButtonAction(ActionEvent event) throws IOException{
        Task<String> exportTask = new Task<String>() {
            @Override protected String call() throws Exception {
                setButtonsDisabled(true);
                updateMessage("Exporting...");

                ExportFile exportFile = new ExportFile();
                try{
                    exportFile.ExportTourList(Main.dbExport.exportTours(), destinationPath);
                }
                catch (Exception e) {
                    System.out.println("export failed: " + e.toString());
                }

                updateMessage("Export done.");
                setButtonsDisabled(false);
                return "";
            }
        };

        exportProgressLabel.textProperty().bind(exportTask.messageProperty());

        Thread th = new Thread(exportTask);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    private void destinationBrowseButtonAction(ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) settingMenuID.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null)
            setDestinationPath(selectedDirectory.getAbsolutePath());
    }

    @FXML
    private void clearAllToursButtonAction(ActionEvent event) {
        boolean confirmed = Main.gui.showYesNoDialog("label_are_you_sure", "message_clear_tours_confirmation");
        //Delete all tours
        if (confirmed)
            TourManagement.deleteAllTours();
    }

    @FXML
    private void refreshDatabaseButtonAction(ActionEvent event) {
        boolean confirmed = Main.gui.showYesNoDialog("label_are_you_sure", "message_refresh_database_confirmation");
        // Reload database
        if(confirmed)
            Main.dbSetup.reloadDatabase();
    }
}
