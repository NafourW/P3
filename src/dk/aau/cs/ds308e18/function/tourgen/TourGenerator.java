package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.function.management.TourManagement;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourGenerator {

    public static final int MIN_ORDERS_PER_TOUR = 2;
    public static final int MAX_ORDERS_PER_TOUR = 20;

    public static final int DEFAULT_WORK_TIME = 480;
    public static final int DEFAULT_BREAK_TIME = 45;

    public static ArrayList<Tour> generateTours(ArrayList<Order> orders, TourGeneratorSettings settings) {
        ArrayList<Tour> initialTours = new ArrayList<>();
        ArrayList<Tour> processedTours = new ArrayList<>();

        /*
        STEP 1:
        LAV EN TUR FOR HVER DATO/REGION COMBO
        OG SMID ALLE ORDRER IND PÅ TURENE
        */

        //currently focused tour
        Tour tour = null;

        //iterate through each order
        for (Order o : orders) {
            //get the order's date
            int orderWeek = o.getWeekNumber();
            String orderRegion = o.getRegion();

            boolean assignedToPrevioustour = false;

            //iterate through each tour
            for (Tour t : initialTours) {
                //if this week was already used by a previous tour, and that tour has the same region,
                if (orderWeek == t.getWeekNumber() && orderRegion.equals(t.getRegion())) {
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
                tour.setTourDate(o.getDate());
                tour.setRegion(orderRegion);
                //add it to the list of generated tours
                initialTours.add(tour);
            }

            //add the order to the tour
            tour.addOrder(o);
        }

        /*
        STEP 2:
        FIND UD AF OM DER ER NOK TID PÅ HVER TUR
        OG SPLIT DEM, HVIS DER IKKE ER
        */

        for (Tour initialTour : initialTours) {
            //Process the initial tour, and add the results to the list of processed tours
            processedTours.addAll(processTour(firstOrder(initialTour), settings));
        }

        /*
        STEP 3:
        DISCARD ALLE TURE HVOR DER IKKE ER NOK ORDRER
        (MED MINDRE FORCE ER SLÅET TIL)
        */

        ArrayList<Tour> invalidTours = new ArrayList<>();

        for (Tour t : processedTours) {
            //If tour doesn't have any orders
            //Or if tour does not have enough orders, and we don't force all orders on tours
            if ((t.getOrders().size() < 1) ||
                    (t.getOrders().size() < MIN_ORDERS_PER_TOUR && !settings.forceOrdersOnTour)) {
                //Mark as invalid
                invalidTours.add(t);
            } else {
                //Update tour ids on orders
                TourManagement.createTour(t);
            }
        }

        //Remove all invalid tours from tour list
        for (Tour t : invalidTours) {
            processedTours.remove(t);
        }

        return processedTours;
    }

    private static ArrayList<Tour> processTour(Tour initialTour, TourGeneratorSettings settings) {
        GPS gps = new GPS();

        ArrayList<Tour> processedTours = new ArrayList<>();
        ArrayList<Order> ordersThatDoNotFit = new ArrayList<>();

        //Time available for driving and moving wares
        long availableTime = settings.workTime - settings.breakTime;

        //Total travel time before adding another order
        long totalTimeBefore = 0;

        //Total travel time after adding another order
        long totalTimeAfter = 0;

        GHPoint startPoint = new GHPoint(56.448789, 9.33946);
        GHPoint previousPoint = startPoint;

        //Go through all orders, and check if there is enough time for them
        for (Order o : initialTour.getOrders()) {
            long timeTravelTo = 0;
            long timeTravelBack = 0;

            try {
                timeTravelTo = gps.getMillis(previousPoint, o.getLatLon()) / 60000;
                timeTravelBack = gps.getMillis(o.getLatLon(), startPoint) / 60000;
            } catch (RuntimeException e) {
                System.out.println("Can't get millis for: " + o);
                System.out.println(o.getLatLon());
            }

            totalTimeAfter -= timeTravelTo + o.getTotalTime() + timeTravelBack;

            if (totalTimeAfter >= availableTime) {
                //Set timeBefore to the new value
                totalTimeBefore = totalTimeAfter;

            } else {
                ordersThatDoNotFit.add(o);
                //Reset timeAfter to the previous value
                totalTimeAfter = totalTimeBefore;
            }
        }

        //If there are orders that don't fit on the tour
        if (ordersThatDoNotFit.size() > 0) {
            //Create a new tour with the same date/region and add it to the processed tour list
            Tour newTour = new Tour();
            newTour.setTourDate(initialTour.getTourDate());
            newTour.setRegion(initialTour.getRegion());
            processedTours.add(newTour);

            //Go through all orders that do not fit on the tour, and put them on the new tour
            for (Order excessOrder : ordersThatDoNotFit) {
                initialTour.removeOrder(excessOrder);
                newTour.addOrder(excessOrder);
            }

            processedTours.addAll(processTour(initialTour, settings));
        }

        //Add the tour to the processed tour list
        processedTours.add(initialTour);

        return processedTours;
    }

    //Returns a sorted list of orders based on time bewtween them. Parameter outputList should be empty when initially called
    private static List<Order> sortOrdersByTime(Order currentOrder, List<Order> orderList, List<Order> outputList) {
        GPS gps = new GPS();
        long bestTime = Long.MAX_VALUE;
        int orderIndex = 0;
        int nextOrderIndex = 0;

        //Add the current order to list
        outputList.add(currentOrder);

        //For the current order find the one closest to it
        for (Order nextOrder : orderList) {
            //Check at den næste ordre ikke er tilføjet før
            if (outputList.contains(nextOrder)) {
                //Do nothing
            } else if (bestTime > gps.getMillis(currentOrder.getLatLon(), nextOrder.getLatLon())) {
                bestTime = gps.getMillis(currentOrder.getLatLon(), nextOrder.getLatLon());
                nextOrderIndex = orderIndex;
            }
            orderIndex++;
        }
        //Recursively call function to get full list of orders in sequence
        if (outputList.size() < orderList.size()) {
            sortOrdersByTime(orderList.get(nextOrderIndex), orderList, outputList);
        }
        return outputList;
    }

    //Package private
    //Finds the first order for a tour and then sorts the sequence of the orders
    static Tour firstOrder(Tour tour) {
        GPS gps = new GPS();
        GHPoint vibocold = new GHPoint(56.448789, 9.33946);
        long bestTime = Long.MAX_VALUE;
        int orderIndex = 0;
        int indexFirstOrder = 0;
        //List to contain the correct sequence of orders
        List<Order> outputList = new ArrayList<>();
        //List to temporary contain orders from the tour in order to pass them on after being cleared
        List<Order> tempOrderList = new ArrayList<>(tour.getOrders());

        //Find shortest time from vibocold to order
        for (Order order : tour.getOrders()) {
            if (bestTime > gps.getMillis(vibocold, order.getLatLon())) {
                bestTime = gps.getMillis(vibocold, order.getLatLon());
                indexFirstOrder = orderIndex;
            }
            orderIndex++;
        }

        //Re-add order in the corrct sequence
        tour.getOrders().clear();
        for (Order order : sortOrdersByTime(tempOrderList.get(indexFirstOrder), tempOrderList, outputList)) {
            tour.addOrder(order);
        }

        return tour;
    }
}
