package dk.aau.cs.ds308e18.function;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;

public class TourGeneratorTest {
    @Test
    void TestAllOrdersAssignedToTour() {
        ArrayList<Order> orders = new ArrayList<>();

        Order order = new Order();
        orders.add(order);

        ArrayList<Tour> tours = TourGenerator.generateTours(orders);

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

    @Test
    void TestOrdersPerTourDoesNotExceedLimit(){
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            orders.add(order);
        }

        ArrayList<Tour> tours = TourGenerator.generateTours(orders);

        for (Tour t : tours) {
            if (t.getOrders().size() > TourGenerator.MAX_ORDERS_PER_TOUR)
                fail("Tour has too many orders (" + t.getOrders().size() + "/" + TourGenerator.MAX_ORDERS_PER_TOUR + ")");
        }
    }

    @Test
    void TestToursHaveMoreThanOneOrder(){

    }
}
