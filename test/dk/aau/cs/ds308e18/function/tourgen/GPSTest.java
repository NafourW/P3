package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GPSTest {
    private GPS gps = new GPS();


    @Test
    public void setRouteTest() {
        GHPoint ghPointStart = new GHPoint(57.0467, 9.9114); //Coordinates from OpenStreetMap
        GHPoint ghPointEnd = new GHPoint(57.0120, 9.9930);

        Assertions.assertEquals(8500, gps.getDistance(ghPointStart, ghPointEnd), 200); //Expected distance from OpenStreetMap, 200 m delta
        Assertions.assertEquals(780000, gps.getMillis(ghPointStart, ghPointEnd), 300000); //Expected time from OpenStreetMap, 5 min delta
    }

    @Test
    public void setRouteTest2() {

        GHPoint ghPointStart = new GHPoint(56.4490, 9.3658); // Coordinates from OpenStreetMap
        GHPoint ghPointEnd = new GHPoint(55.4719, 9.4813);

        Assertions.assertEquals(120000, gps.getDistance(ghPointStart, ghPointEnd), 200); //Expected distance from OpenStreetMap, 200 m delta
        Assertions.assertEquals(5760000, gps.getMillis(ghPointStart, ghPointEnd), 300000); //Expected time from OpenStreetMap, 5 min delta
    }

    @Test
    public void GeocodeAddressTest() {
        GHPoint ghPoint = new GHPoint(57.012281, 9.991705); //Coordinates from OpenStreetMap
        Order order = new Order();
        order.setAddress("selma lagerløfs vej 300");
        order.requestLatLonFromAddress();

        Assertions.assertEquals(ghPoint.getLat(), order.getLatLon().getLat());
        Assertions.assertEquals(ghPoint.getLon(), order.getLatLon().getLon());
    }
}
