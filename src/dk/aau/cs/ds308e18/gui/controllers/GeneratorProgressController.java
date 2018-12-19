package dk.aau.cs.ds308e18.gui.controllers;

import dk.aau.cs.ds308e18.Main;
import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.TourManagement;
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

/*
The Tour Generator progress window, which shows what the tour generator is currently doing.
This is also where the actual tour generation method is called from.
*/
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

            for (Tour tour : tours) {
                //Fix weird bug where tours already have a tourID,
                //even though that shouldn't be able to happen
                if (tour.getTourID() == 0) {
                    //Add tour to database
                    TourManagement.createTour(tour);
                }
            }

            return "";
            }
        };

        generateTourTask.setOnSucceeded(e -> close());

        progressLabel.textProperty().bind(generateTourTask.messageProperty());

        Thread th = new Thread(generateTourTask);
        th.setDaemon(true);
        th.start();
    }

    /*
    From the ISelectionController interface
    */
    @Override
    public void setSelectedObject(Object obj, boolean isNew) {
        settings = (TourGeneratorSettings)obj;
    }

    private void close() {
        Stage window = (Stage) root.getScene().getWindow();
        Main.gui.closeWindow(window);
    }
}
