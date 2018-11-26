package dk.aau.cs.ds308e18.function;

import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

public class TourGeneratorTest {
    @Test
    void TestAllOrdersAssignedToTour() {
        ArrayList<Order> orders = new ArrayList<>();
        TourGenerator tourGenerator = new TourGenerator();

        Order order = new Order(0,"","",
                "","","","",
                0,0,false,"",
                0,"","","",
                false,"",false,"");
        orders.add(order);

        ArrayList<Tour> tours = tourGenerator.generateTours(orders);

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
