package dk.aau.cs.ds308e;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale locale = new Locale("en","US");
//      Locale locale = new Locale("da","DA");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("gui.fxml"));
        loader.setResources(ResourceBundle.getBundle("InternationalizedStrings", locale));

        Parent root = loader.load();// FXMLLoader.load(getClass().getResource("gui.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
