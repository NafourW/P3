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

    public static final int DEFAULT_WORK_TIME = 480;
    public static final int DEFAULT_BREAK_TIME = 45;

    public static ArrayList<Tour> generateTours(ArrayList<Order> orders, TourGeneratorSettings settings){
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
            LocalDate orderDate = o.getDate();
            String orderRegion = o.getRegion();

            boolean assignedToPrevioustour = false;

            //iterate through each tour
            for (Tour t : initialTours) {
                //this date was already used by a previous tour, and that tour has the same date/region,
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
            processedTours.addAll(processTour(initialTour, settings));
        }

        /*
        STEP 3:
        DISCARD ALLE TURE HVOR DER IKKE ER NOK ORDRER
        (MED MINDRE FORCE ER SLÅET TIL)
        */

        ArrayList<Tour> invalidTours = new ArrayList<>();

        for (Tour t : processedTours) {
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
            processedTours.remove(t);
        }

        return processedTours;
    }

    private static ArrayList<Tour> processTour(Tour initialTour, TourGeneratorSettings settings){
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
                timeTravelTo   = gps.getMillis(previousPoint, o.getLatLon());
                timeTravelBack = gps.getMillis(o.getLatLon(), startPoint);
            }
            catch (RuntimeException e) {
                System.out.println("Can't get millis for: " + o);
                System.out.println(o.getLatLon());
            }

            totalTimeAfter += timeTravelTo + o.getTotalTime() + timeTravelBack;

            if (totalTimeAfter >= availableTime){
                //Set timeBefore to the new value
                totalTimeBefore = totalTimeAfter;

            }
            else {
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
}
