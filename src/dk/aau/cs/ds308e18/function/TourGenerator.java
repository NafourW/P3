package dk.aau.cs.ds308e18.function;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;

import java.util.ArrayList;

public class TourGenerator {
    public enum planningMethod{
      distance,
      fuel
    }

    public ArrayList<Tour> generateTours(ArrayList<Order> orders){
        ArrayList<Tour> generatedTours = new ArrayList<>();

        Tour tour = new Tour();
        generatedTours.add(tour);

        for (Order o : orders)
            tour.addOrder(o);

        return generatedTours;
    }
}
