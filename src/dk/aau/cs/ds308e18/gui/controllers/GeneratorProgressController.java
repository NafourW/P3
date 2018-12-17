package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.tourgen.TourGenerator;
import dk.aau.cs.ds308e18.function.tourgen.TourGeneratorSettings;
import dk.aau.cs.ds308e18.gui.ISelectionController;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GeneratorProgressController implements ISelectionController {
    TourGeneratorSettings settings;

    @FXML private VBox root;
    @FXML private Label progressLabel;

    @FXML
    private void initialize(){
        Task<String> generateTourTask = new Task<String>() {
            @Override protected String call() throws Exception {
            //Get orders
            updateMessage("Fetching orders...");
            ArrayList<Order> orders = OrderManagement.getUnassignedOrdersFiltered(settings.region, settings.date);

            //Generate tours
            updateMessage("Generating tours...");
            TourGenerator tourGenerator = new TourGenerator(this::updateMessage);
            ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

            System.out.println("Generated " + tours.size() + " tours:");
            for (Tour tour : tours)
                System.out.println(tour.getTourDate() + " - " + tour.getOrders().size() + " orders");

            return "";
            }
        };

        generateTourTask.setOnSucceeded(e -> close());

        progressLabel.textProperty().bind(generateTourTask.messageProperty());

        Thread th = new Thread(generateTourTask);
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        settings = (TourGeneratorSettings)obj;
    }

    private void close() {
        Stage window = (Stage) root.getScene().getWindow();
        Main.gui.closeWindow(window);
    }
}
