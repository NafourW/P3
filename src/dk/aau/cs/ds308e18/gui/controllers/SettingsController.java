package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class SettingsController {

    @FXML
    private VBox settingMenuID;

    @FXML
    private ComboBox<Locale> languageSelector;

    private ObservableList<Locale> languages = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        //Add english and danish to language combobox
        languages.addAll(new Locale("en","US"), new Locale("da","DA"));
        languageSelector.setItems(languages);
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
        Main.db.importOrders();
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        File selectedDirectory = directoryChooser.showDialog(null);

        Stage stage = (Stage) settingMenuID.getScene().getWindow();

        if (selectedDirectory != null){
            selectedDirectory.getAbsolutePath();
        }
    }

    /*
    final Button browseButton = new Button("...");
    browseButton.setOnAction(
        (final ActionEvent e) -> {
        final DirectoryChooser directoryChooser
        new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            selectedDirectory.getAbsolutePath();
        }
    });
    */

    @FXML
    private void exportDataButtonAction(ActionEvent event) {

    }

    @FXML
    private void clearAllToursButtonAction(ActionEvent event) {
        Main.gui.showYesNoDialog("label_are_you_sure", "message_clear_tours_confirmation");
    }
}
