package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.OrderLine;
import dk.aau.cs.ds308e18.model.Tour;

import java.time.LocalDate;
import java.util.ArrayList;

public class TourGenerator {

    public static final int MIN_ORDERS_PER_TOUR = 2;
    public static final int MAX_ORDERS_PER_TOUR = 20;

    public static ArrayList<Tour> generateTours(ArrayList<Order> orders, TourGeneratorSettings settings){
        ArrayList<Tour> generatedTours = new ArrayList<>();
        ArrayList<Tour> filledTours = new ArrayList<>();

        //currently focused tour
        Tour tour = null;

        long availableTime = settings.workTime - settings.breakTime;

        //Total travel time before adding another order
        long totalTimeBefore = 0;

        //Total travel time after adding another order
        long totalTimeAfter = 0;

        //iterate through each order
        for (Order o : orders) {
            //get the order's date
            LocalDate orderDate = o.getDate();
            String orderRegion = o.getRegion();

            boolean assignedToPrevioustour = false;

            //iterate through each tour
            for (Tour t : generatedTours) {
                //this date was already used by a previous tour, and that tour has the same region,
                if (orderDate.equals(t.getTourDate()) && orderRegion.equals(t.getRegion())) {
                    //use the previous tour
                    tour = t;
                    assignedToPrevioustour = true;
                    break;
                }
            }

            //if the order was not assigned to a previous tour,
            if (!assignedToPrevioustour) {
                //make a new tour
                tour = new Tour();
                tour.setTourDate(orderDate);
                tour.setRegion(orderRegion);
                //add it to the list of generated tours
                generatedTours.add(tour);
            }

            if (totalTimeAfter >= availableTime){

                long overTime = totalTimeAfter - availableTime;
                long spareTime = availableTime - totalTimeBefore;

                if (overTime >= spareTime){
                    tour.addOrder(o);
                }
            } else {
                continue;
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

        ArrayList<Tour> invalidTours = new ArrayList<>();

        for (Tour t : generatedTours) {
            //If tour does not have enough orders, and we don't force all orders on tours
            if (t.getOrders().size() < MIN_ORDERS_PER_TOUR && !settings.forceOrdersOnTour) {
                //Mark as invalid
                invalidTours.add(t);
            }
            else{
                //Update tour ids on orders
                TourManagement.createTour(t);
            }
        }

        //Remove all invalid tours from tour list
        for (Tour t : invalidTours) {
            generatedTours.remove(t);
        }

        return generatedTours;
    }

    public static long orderTime(boolean isFromStart, GHPoint start, Order location1, Order location2){

        GPS gps = new GPS();

        long time = 0;

        if (isFromStart/*if it's the beginning of the tour*/){
            long timeTravelTo = gps.getMillis(start, location1.getLatLon());

            long timeTravelBack = gps.getMillis(location1.getLatLon(), start);

            time = timeTravelTo + location1.getTotalTime() + timeTravelBack;

        } else if (!isFromStart){
            long timeTravelTo = gps.getMillis(location1.getLatLon(), location2.getLatLon());

            long timeTravelBack = gps.getMillis(location2.getLatLon(), start);

            time = timeTravelTo + location2.getTotalTime() + timeTravelBack;
        }
        return time;
    }
}
