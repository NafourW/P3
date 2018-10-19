import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class GUI extends javafx.application.Application {
    private static Stage PrimaryStage;
    private static Stage FileDirectoryStage;
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

        /*
        HBox top = new HBox(5);

        Button ImportFile = new Button("Import");
        ImportFile.setOnAction(event -> {

        });

        top.getChildren().addAll(ImportFile);
        */

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Import");
        add.setOnAction(event -> {
            FileChooser importFile = new FileChooser();
            importFile.setTitle("Open Resource File");
            importFile.showOpenDialog(FileDirectoryStage);
        });

        menuFile.getItems().addAll(add);
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile,menuEdit,menuView);

        VBox topBorder = new VBox(5);
        topBorder.getChildren().addAll(menuBar);


        border.setTop(topBorder);
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
