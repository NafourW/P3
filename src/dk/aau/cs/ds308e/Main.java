package dk.aau.cs.ds308e;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale locale = new Locale("en","US");
//      Locale locale = new Locale("da","DA");
        ResourceBundle localStrings = ResourceBundle.getBundle("InternationalizedStrings", locale);

        loader = new FXMLLoader(getClass().getResource("/views/MainMenu.fxml"));
        loader.setResources(localStrings);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle(localStrings.getString("label_title_app"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(620);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
