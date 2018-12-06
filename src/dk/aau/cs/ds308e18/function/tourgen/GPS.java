package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// Graphopper API

public class GPS {

    private String vehicle = "car";
    private GHResponse rsp;

    GraphHopper hopper = new GraphHopper().forServer();


    public void setRoute (GHPoint addrese1, GHPoint addresse2) {
        GHRequest req = new GHRequest(addrese1, addresse2). /* latFrom, lonFrom, latTo, lonTo */
                setWeighting("fastest").
                setVehicle(vehicle).
                setLocale(Locale.US);
        rsp = hopper.route(req);
    }

    public double getDistance() {
        return rsp.getDistance();
    }

    public long getMillis() {
        return rsp.getMillis();
    }
}
