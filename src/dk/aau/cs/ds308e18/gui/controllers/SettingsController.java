package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

    @FXML private TextField sourceField;
    @FXML private TextField destinationField;

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
        Main.dbImport.importOrders(sourcePath);
        Main.dbImport.importWares(sourcePath);
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
    private void exportDataButtonAction(ActionEvent event) {

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
