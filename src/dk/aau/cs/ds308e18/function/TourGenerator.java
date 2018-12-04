package dk.aau.cs.ds308e18.function;

import dk.aau.cs.ds308e18.function.management.OrderManagement;
import dk.aau.cs.ds308e18.function.management.TourManagement;
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
        ArrayList<Tour> filledTours = new ArrayList<>();

        //currently focused tour
        Tour tour = null;

        //iterate through each order
        for (Order o : orders) {
            //get the order's date
            LocalDate orderDate = o.getDate();

            boolean dateAlreadyUsed = false;

            //iterate through each tour
            for (Tour t : generatedTours) {
                //check if this date was already used by a previous tour
                if (orderDate.equals(t.getTourDate()))
                    dateAlreadyUsed = true;

                //if the date is used by another tour, and that tour has not reached the order limit,
                if (dateAlreadyUsed) {
                    //use the previous tour
                    tour = t;
                    break;
                }
            }

            //if no tour has used this date,
            if (!dateAlreadyUsed) {
                //make a new tour
                tour = new Tour();
                tour.setTourDate(orderDate);
                //add it to the list of generated tours
                generatedTours.add(tour);
            }

            //add the order to the tour
            tour.addOrder(o);

            //if the tour is full, move it to the filled tours list
            if (tour.getOrders().size() >= MAX_ORDERS_PER_TOUR){
                generatedTours.remove(tour);
                filledTours.add(tour);
                tour = null;
            }
        }

        //add the filled tours to the list that is returned
        generatedTours.addAll(filledTours);

        //Update tour ids on orders
        for (Tour t : generatedTours) {
            TourManagement.createTour(t);
        }

        return generatedTours;
    }
}
