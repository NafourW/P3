package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.io.ExportFile;
import dk.aau.cs.ds308e18.io.database.DatabaseExport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML private TextField ghKeyTextField;

    @FXML private TextField sourceField;
    @FXML private TextField destinationField;

    @FXML private Label importProgressLabel;
    @FXML private Label exportProgressLabel;

    @FXML private CheckBox darkModeCheckBox;

    @FXML private Label statsLabel;

    private String sourcePath;
    private String destinationPath;

    private ObservableList<Locale> languages = FXCollections.observableArrayList();

    private Preferences prefs;

    @FXML
    public void initialize() {
        //Add english and danish to language combobox
        languages.addAll(new Locale("en","US"), new Locale("da","DA"));
        languageSelector.setItems(languages);

        //Use previous values for import/export directories
        prefs = Preferences.userNodeForPackage(dk.aau.cs.ds308e18.Main.class);
        setSourcePath(prefs.get("dataImportSourceDirectory", ""));
        setDestinationPath(prefs.get("dataExportDestinationDirectory", ""));

        ghKeyTextField.setText(prefs.get("graphhopperKey", ""));

        ghKeyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setGHKey(newValue);
            }
        });

        darkModeCheckBox.setSelected(Main.gui.isDarkMode());

        updateStatsLabel();
    }

    private void setSourcePath(String path) {
        sourcePath = path;
        sourceField.setText(path);

        prefs.put("dataImportSourceDirectory", path);
    }

    private void setDestinationPath(String path) {
        destinationPath = path;
        destinationField.setText(path);

        prefs.put("dataExportDestinationDirectory", path);
    }

    private void setGHKey(String key) {
        prefs.put("graphhopperKey", key);
    }

    private void setButtonsDisabled(boolean disabled) {
        languageSelector.setDisable(disabled);
        ghKeyTextField.setDisable(disabled);
        importDataButton.setDisable(disabled);
        exportDataButton.setDisable(disabled);
        sourceBrowseButton.setDisable(disabled);
        destinationBrowseButton.setDisable(disabled);
        clearAllToursButton.setDisable(disabled);
        refreshDatabaseButton.setDisable(disabled);
        backButton.setDisable(disabled);
    }

    private void updateStatsLabel() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Main.gui.getLocalString("label_settings_stats_tours"))
                .append(" ")
                .append(DatabaseExport.getAmount("tours"))
                .append("     ")
                .append(Main.gui.getLocalString("label_settings_stats_orders"))
                .append(" ")
                .append(DatabaseExport.getAmount("orders"))
                .append("     ")
                .append(Main.gui.getLocalString("label_settings_stats_orderlines"))
                .append(" ")
                .append(DatabaseExport.getAmount("orderlines"))
                .append("     ")
                .append(Main.gui.getLocalString("label_settings_stats_wares"))
                .append(" ")
                .append(DatabaseExport.getAmount("warelist"));

        statsLabel.setText(stringBuilder.toString());
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
    private void importDataButtonAction(ActionEvent event){
        Task<String> importTask = new Task<String>() {
            @Override protected String call() throws Exception {
            setButtonsDisabled(true);

            updateMessage(Main.gui.getLocalString("message_progress_importing_wares"));
            Main.dbImport.importWares(sourcePath);

            updateMessage(Main.gui.getLocalString("message_progress_importing_orders"));
            Main.dbImport.importOrders(sourcePath);

            updateMessage(Main.gui.getLocalString("message_progress_importing_orderlines"));
            Main.dbImport.importOrderLines(sourcePath);

            updateMessage(Main.gui.getLocalString("message_progress_import_done"));
            setButtonsDisabled(false);
            return "";
            }
        };

        importTask.setOnSucceeded(e -> updateStatsLabel());

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
                updateMessage(Main.gui.getLocalString("message_progress_exporting"));

                ExportFile exportFile = new ExportFile();
                exportFile.ExportData(destinationPath);

                updateMessage(Main.gui.getLocalString("message_progress_export_done"));
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
        if (confirmed) {
            TourManagement.deleteAllTours();
            updateStatsLabel();
        }
    }

    @FXML
    private void refreshDatabaseButtonAction(ActionEvent event) {
        boolean confirmed = Main.gui.showYesNoDialog("label_are_you_sure", "message_refresh_database_confirmation");
        // Reload database
        if(confirmed) {
            Main.dbSetup.reloadDatabase();
            updateStatsLabel();
        }
    }

    @FXML
    private void darkModeCheckBoxAction(ActionEvent event) {
        Main.gui.setDarkMode(darkModeCheckBox.isSelected());

        prefs.putBoolean("darkMode", darkModeCheckBox.isSelected());
    }
}
