package dk.aau.cs.ds308e18.function;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;

import java.time.LocalDate;
import java.util.ArrayList;

public class TourGenerator {
    public enum planningMethod{
        leastTime,
        shortestDistance,
        mostEconomic
    }

    public static final int MAX_ORDERS_PER_TOUR = 20;

    public static ArrayList<Tour> generateTours(ArrayList<Order> orders){
        ArrayList<Tour> generatedTours = new ArrayList<>();

        //temporary variables
        LocalDate date = null;
        Tour tour = null;

        //iterate through each order
        for (Order o : orders) {
            //check if this is an unused date
            if (!o.getDate().equals(date)) {
                //use the order's date
                date = o.getDate();

                boolean dateAlreadyUsed = false;
                boolean orderLimitExceeded = false;

                //iterate through each tour
                for (Tour t : generatedTours) {
                    //check if this date was already used by a previous tour
                    if (date.equals(t.getTourDate()))
                        dateAlreadyUsed = true;

                    //check if tour has reached the max orders limit
                    if (t.getOrders().size() >= MAX_ORDERS_PER_TOUR)
                        orderLimitExceeded = true;

                    //if the date is used by another tour, and that tour has not reached the order limit,
                    //use the previous tour
                    if (dateAlreadyUsed && !orderLimitExceeded)
                        tour = t;
                }

                //if no tour has used this date (or max orders has been reached)
                //make a new tour
                if (!dateAlreadyUsed || orderLimitExceeded) {
                    tour = new Tour(date);
                    generatedTours.add(tour);
                }
            }
            //add the order to the tour
            tour.addOrder(o);
        }

        return generatedTours;
    }
}
