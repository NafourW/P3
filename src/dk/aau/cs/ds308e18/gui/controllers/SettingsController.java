package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.Locale;

public class SettingsController {
    @FXML
    private ComboBox<Locale> languageSelector;

    private ObservableList<Locale> languages = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        languages.addAll(new Locale("en","US"), new Locale("da","DA"));
        languageSelector.setItems(languages);
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        Main.gui.changeView("MainMenu");
    }

    @FXML
    private void languageComboBoxAction(ActionEvent event) throws IOException {
        Locale selectedLanguage = languageSelector.getSelectionModel().getSelectedItem();
        Main.gui.setLanguage(selectedLanguage);
        Main.gui.refreshView();
    }

    @FXML
    private void clearAllToursButtonAction(ActionEvent event) {
        Main.gui.showYesNoDialog("label_are_you_sure", "message_clear_tours_confirmation");
    }
}
