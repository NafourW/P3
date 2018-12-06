package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GPSTest {

    @Test
    public void setRouteTest() {
        GPS gps = new GPS();

        GHPoint ghPointStart = new GHPoint(57.0122, 9.9919);
        GHPoint ghPointEnd = new GHPoint(57.04663, 9.91138);

        gps.setRoute(ghPointStart, ghPointEnd);

        Assertions.assertEquals(8700, gps.getDistance(), 200);
    }
}
