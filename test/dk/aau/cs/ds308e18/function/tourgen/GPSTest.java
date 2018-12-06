package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;

public class GPSTest {

    @Test
    public void GPSRouteTest() {
        GPS gps = new GPS();

        GHPoint ghPointStart = new GHPoint();
        GHPoint ghPointEnd = new GHPoint(2314414, 4134134);

        gps.setRoute();
    }
}
