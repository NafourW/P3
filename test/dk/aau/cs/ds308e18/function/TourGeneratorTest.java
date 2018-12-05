package dk.aau.cs.ds308e18.function;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;

public class TourGeneratorTest {
    @Test
    void TestToursHaveAtLeastMinimumOrders(){
        TourGenerator.planningMethod method = TourGenerator.planningMethod.leastTime;
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, method);

        for (Tour t : tours) {
            if (t.getOrders().size() < TourGenerator.MIN_ORDERS_PER_TOUR)
                fail("Tour size below minimum (" + t.getOrders().size() + "/" + TourGenerator.MIN_ORDERS_PER_TOUR + ")");
        }
    }

    @Test
    void TestOrdersPerTourDoesNotExceedLimit(){
        TourGenerator.planningMethod method = TourGenerator.planningMethod.leastTime;
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, method);

        for (Tour t : tours) {
            if (t.getOrders().size() > TourGenerator.MAX_ORDERS_PER_TOUR)
                fail("Tour has too many orders (" + t.getOrders().size() + "/" + TourGenerator.MAX_ORDERS_PER_TOUR + ")");
        }
    }

    @Test
    void TestTourRegionsMatchOrderRegions() {
        TourGenerator.planningMethod method = TourGenerator.planningMethod.leastTime;
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setRegion("København");
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, method);

        for (Tour t : tours) {
            for (Order o : t.getOrders()) {
                if (!o.getRegion().equals(t.getRegion()))
                    fail("Regions don't match: " + t.getRegion() + " - " + o.getRegion());
            }
        }
    }

    @Test
    void TestTourDatesMatchOrderDates() {
        TourGenerator.planningMethod method = TourGenerator.planningMethod.leastTime;
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setRegion("København");
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, method);

        for (Tour t : tours) {
            for (Order o : t.getOrders()) {
                if (!o.getDate().equals(t.getTourDate()))
                    fail("Dates don't match: " + t.getTourDate() + " - " + o.getDate());
            }
        }
    }

    /*  This test is no longer relevant,
        since all orders don't have to be assigned a tour.
    @Test
    void TestAllOrdersAssignedToTour() {
        TourGenerator.planningMethod method = TourGenerator.planningMethod.leastTime;
        ArrayList<Order> orders = new ArrayList<>();

        Order order = new Order();
        orders.add(order);

        ArrayList<Tour> tours = TourGenerator.generateTours(orders, method);

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
    */
}
