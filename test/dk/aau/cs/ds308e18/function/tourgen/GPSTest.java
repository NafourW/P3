package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GPSTest {
    GPS gps = new GPS();

    @Test
    public void setRouteTest() {

        GHPoint ghPointStart = new GHPoint(57.0122, 9.9919);
        GHPoint ghPointEnd = new GHPoint(57.04663, 9.91138);

        gps.setRoute(ghPointStart, ghPointEnd);

        Assertions.assertEquals(8700, gps.getDistance(), 200);
    }

    @Test
    public void GeocodeAddressTest() {
        GHPoint ghPoint = new GHPoint(57.012281, 9.991705); //Coordinates from OpenStreetMap
        Order order = new Order();
        order.setAddress("selma lagerløfs vej 300");

        order.setLatLon();

        Assertions.assertEquals(ghPoint.getLat(), order.getLatLon().getLat());
        Assertions.assertEquals(ghPoint.getLon(), order.getLatLon().getLon());

        }
    }
