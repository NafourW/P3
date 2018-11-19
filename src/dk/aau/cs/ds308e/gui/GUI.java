package dk.aau.cs.ds308e.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class GUI {
    private Locale currentLocale;
    private ResourceBundle localStrings;

    private Stage window;
    private String currentView;

    private boolean first = true;

    public GUI(){
        //TODO: load previously selected language
        setLanguage(new Locale("en","US"));
    }

    //Set the main window
    public void setWindow(Stage window){
        this.window = window;
        this.window.setScene(new Scene(new VBox()));
    }

    //Set the application language
    public void setLanguage(Locale language) {
        currentLocale = language;
        localStrings = ResourceBundle.getBundle("InternationalizedStrings", currentLocale);
    }

    //Get a translated string from the resource bundle
    public String getLocalString(String key) {
        return localStrings.getString(key);
    }

    //Change the current application view by loading FXML and replacing the scene's root
    public void changeView(String view) throws Exception{
        currentView = view;
        Parent root = FXMLLoader.load(getClass().getResource("/views/" + currentView + ".fxml"), localStrings);
        window.getScene().setRoot(root);
    }

    //Loads the current view again
    public void refreshView() throws Exception{
        changeView(currentView);
    }
}
