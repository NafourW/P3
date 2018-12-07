package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;

public class GPSTest {
    GPS gps = new GPS();

    @Test
    public void setRouteTest() {


        GHPoint ghPointStart = new GHPoint(57.0467, 9.9114); //Coordinates from OpenStreetMap
        GHPoint ghPointEnd = new GHPoint(57.0120, 9.9930);

        gps.setRoute(ghPointStart, ghPointEnd);

        Assertions.assertEquals(8500, gps.getDistance(), 200); //Expected distance from OpenStreetMap, 200 m delta

        Time time = new Time(0,13,0);
        Assertions.assertEquals(time, gps.getMillis()); //Expected time from OpenStreetMap
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
