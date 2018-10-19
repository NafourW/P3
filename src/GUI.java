import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class GUI extends javafx.application.Application {
    private static Stage PrimaryStage;
    private static boolean answer;

    public void start(Stage primaryStage) throws IOException {
        BorderPane border = new BorderPane();
        PrimaryStage = primaryStage;
        primaryStage.setTitle("Vibocold P3");

        Scene scene = new Scene(border,800, 600);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });
        primaryStage.show();
    }

    private void closeProgram() {
        Boolean answer = GUI.display("Vibocold P3", "Are you sure you want to exit?");
        if (answer) {
            PrimaryStage.close();
        }
    }

    private static boolean display(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            answer = true;
        } else if (result.get() == no) {
            answer = false;
        }
        return answer;
    }

}
