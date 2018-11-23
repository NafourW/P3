package dk.aau.cs.ds308e18.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GUI {
    private Locale currentLocale;
    private ResourceBundle localStrings;

    private Stage window;
    private String currentView;

    public GUI(){
        initLanguage();
    }

    //Set the main window
    public void setWindow(Stage window){
        this.window = window;
        this.window.setScene(new Scene(new VBox()));
    }

    //Loads preferred language, or defaults to english
    private void initLanguage() {
        Preferences prefs = Preferences.userNodeForPackage(dk.aau.cs.ds308e18.Main.class);

        String defaultLanguage = "en_US";
        String languageValue = prefs.get("language", defaultLanguage);

        setLanguage(languageValue);
    }

    //Set the application language
    public void setLanguage(Locale locale) {
        currentLocale = locale;
        localStrings = ResourceBundle.getBundle("InternationalizedStrings", currentLocale);

        Preferences prefs = Preferences.userNodeForPackage(dk.aau.cs.ds308e18.Main.class);

        prefs.put("language", locale.toString());
    }

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        setLanguage(locale);
    }

    //Get a translated string from the resource bundle
    public String getLocalString(String key) {
        String value = localStrings.getString(key);
        if (!value.isEmpty())
            return value;
        else
            return key;
    }

    //Change the current application view by loading FXML and replacing the scene's root
    public void changeView(String view) throws IOException{
        currentView = view;
        Parent root = FXMLLoader.load(getClass().getResource("/views/" + currentView + ".fxml"), localStrings);
        window.getScene().setRoot(root);
    }

    //Loads the current view again
    public void refreshView() throws IOException{
        changeView(currentView);
    }

    //Opens a new non-resizable window with the given view
    public void openWindow(String view, String titleKey) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"), localStrings);

        Scene newScene = new Scene(root);
        Stage newWindow = new Stage();

        newWindow.setScene(newScene);
        newWindow.setResizable(false);
        newWindow.setTitle(getLocalString(titleKey));
        newWindow.show();
    }

    //Closes the window of a given stage
    public void closeWindow(Stage window)
    {
        window.close();
    }

    public boolean showYesNoDialog(String titleKey, String contentKey) {
        boolean answer = false;

        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle(getLocalString(titleKey));
        alert.setContentText(getLocalString(contentKey));

        ButtonType yes = new ButtonType(getLocalString("button_yes"), ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(getLocalString("button_no"), ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == yes)
            answer = true;
        else if (result.get() == no)
            answer = false;

        return answer;
    }
}
