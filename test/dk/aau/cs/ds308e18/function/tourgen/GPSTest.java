package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GPSTest {
    GPS gps = new GPS();

    @Test
    public void GPSRouteTest() {

        GHPoint ghPointStart = new GHPoint();
        GHPoint ghPointEnd = new GHPoint(2314414, 4134134);

        gps.setRoute(ghPointStart, ghPointEnd);
    }

    @Test
    public void GeocodeAddressTest() {
        GHPoint ghPoint = new GHPoint(57.012281, 9.991705);
        Order order = new Order();
        order.setAddress("selma lagerløfs vej 300");

        order.setLatLon();

        Assertions.assertEquals(order.getLatLon().getLat(), ghPoint.getLat(), 0.003);
        Assertions.assertEquals(order.getLatLon().getLon(), ghPoint.getLon(), 0.003);

        }
    }
