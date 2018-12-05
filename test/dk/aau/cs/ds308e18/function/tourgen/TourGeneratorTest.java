package dk.aau.cs.ds308e18.function.tourgen;

import dk.aau.cs.ds308e18.io.database.DatabaseSetup;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;

public class TourGeneratorTest {

    @BeforeAll @AfterAll
    static void RefreshDatabaseBefore() {
        DatabaseSetup databaseSetup = new DatabaseSetup();
        databaseSetup.reloadDatabase();
    }
    
    @Test
    void TestToursHaveAtLeastMinimumOrders(){
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            if (t.getOrders().size() < TourGenerator.MIN_ORDERS_PER_TOUR)
                fail("Tour size below minimum (" + t.getOrders().size() + "/" + TourGenerator.MIN_ORDERS_PER_TOUR + ")");
        }
    }

    @Test
    void TestOrdersPerTourDoesNotExceedLimit(){
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            if (t.getOrders().size() > TourGenerator.MAX_ORDERS_PER_TOUR)
                fail("Tour has too many orders (" + t.getOrders().size() + "/" + TourGenerator.MAX_ORDERS_PER_TOUR + ")");
        }
    }

    @Test
    void TestTourRegionsMatchOrderRegions() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setRegion("København");
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            for (Order o : t.getOrders()) {
                if (!o.getRegion().equals(t.getRegion()))
                    fail("Regions don't match: " + t.getRegion() + " - " + o.getRegion());
            }
        }
    }

    @Test
    void TestTourDatesMatchOrderDates() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setRegion("København");
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            for (Order o : t.getOrders()) {
                if (!o.getDate().equals(t.getTourDate()))
                    fail("Dates don't match: " + t.getTourDate() + " - " + o.getDate());
            }
        }
    }

    @Test
    void TestAllOrdersAssignedToTour() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;
        settings.forceOrdersOnTour = true;

        for (int i = 0; i < TourGenerator.MAX_ORDERS_PER_TOUR + 1; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setRegion("København");
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, settings);

        for (Order o : orders) {
            boolean hasBeenAssigned = false;
            for (Tour t : tours) {
                if (t.getOrders().contains(o)) {
                    hasBeenAssigned = true;
                }
            }
            if (!hasBeenAssigned)
                fail("Order has not been assigned to a tour");
        }
    }
}
