package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.model.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

// Graphopper API

public class GPS {
    //Strings for building the API URL

    private String vehicle = "car";
    private GHResponse rsp;
    //private PathWrapper path;

    private GraphHopper hopper = new GraphHopper().forServer();

    public GPS() {
        // Store and load graphhopper files
        hopper.setOSMFile("C:/Users/the_p/Desktop/graphhopper_real/europe_denmark.osm");
        hopper.setGraphHopperLocation("resources/graphhopper_map");
        hopper.setEncodingManager(new EncodingManager(vehicle));
        hopper.importOrLoad();
    }


    //Return Lattitude, Longtitude based on address from API
    public GHPoint GeocodeAddress(String address, int zipCode) {
        String key = "1095aac7-8a71-4c56-b725-eca17fdf1284";
        String linkGeocode = "https://graphhopper.com/api/1/geocode?q=";
        String linkEnd = "&locale=en&debug=true&key=";
        StringBuilder jsonBuild = new StringBuilder();
        String json = "";
        String tempAddress = address + " " + zipCode;
        double lat;
        double lon;

        //Remove spaces in address for the URL
        tempAddress = tempAddress.replaceAll("\\s", "%20");

        //Build link for API request
        StringBuilder sb = new StringBuilder();
        sb.append(linkGeocode).append(tempAddress).append(linkEnd).append(key);

        //URL Request
        URL url = null;
        try {
            url = new URL(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Read response from URL
        if (url != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    jsonBuild.append(line);
                }
                json = jsonBuild.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Parse read string to Json
        JSONParser parser = new JSONParser();
        Object jobj = null;
        try {
            jobj = parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jb = (JSONObject) jobj;

        //Read Json response
        JSONArray hits = null;
        //Enter Json "hits"
        if (jb != null) hits = (JSONArray) jb.get("hits");
        JSONObject firstCase = null;
        //Enter the first case (could be more than 1)
        if (hits.size() > 0) firstCase = (JSONObject) hits.get(0);
        JSONObject point = null;
        //Enter point
        if (firstCase != null) point = (JSONObject) firstCase.get("point");

        //Set the order's point
        if (point != null) {
            lat = (double) point.get("lat");
            lon = (double) point.get("lng");

            return new GHPoint(lat, lon);
        }
        //bad address returns default point
        return new GHPoint(0, 0);
    }


    //Create route between 2 points
    public void setRoute(GHPoint address1, GHPoint address2) {
        GHRequest req = new GHRequest(address1, address2). // latFrom, lonFrom, latTo, lonTo
                setWeighting("fastest").
                setVehicle(vehicle).
                setLocale(Locale.US);
        rsp = hopper.route(req);
        //path = rsp.getBest();
    }

    //return distance in meters
    public double getDistance(GHPoint address1, GHPoint address2) {
        setRoute(address1, address2);
        return rsp.getDistance();
    }

    //Return time in milliseconds
    public long getMillis(GHPoint address1, GHPoint address2) {
        setRoute(address1, address2);
        return rsp.getMillis();
    }
}
