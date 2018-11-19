package dk.aau.cs.ds308e.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class GUI {

    private Locale currentLocale;
    private ResourceBundle localStrings;

    private Stage window;

    public void setWindow(Stage window){
        this.window = window;
    }

    public void setLanguage(String language, String country) {
        currentLocale = new Locale(language,country);
        localStrings = ResourceBundle.getBundle("InternationalizedStrings", currentLocale);
    }

    public String getLocalString(String key) {
        return localStrings.getString(key);
    }

    public void changeView(String view) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"), localStrings);
        Scene scene = new Scene(root);

        window.setScene(scene);
        window.show();
    }
}
