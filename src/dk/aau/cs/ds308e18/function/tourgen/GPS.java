package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.GraphhopperTest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

// Graphopper API

public class GPS {
    //Strings for building the API URL

    private String vehicle = "car";
    private GHResponse rsp;

    GraphHopper hopper = new GraphHopper().forServer();

    ClassLoader classLoader = GraphhopperTest.class.getClassLoader();



    public GPS() {
        // where to store graphhopper files?
        hopper.setGraphHopperLocation("resources/graphFolder");
        hopper.setEncodingManager(new EncodingManager(vehicle));
        hopper.importOrLoad();
    }


    public GHPoint GeocodeAddress(String address) {
        String key = "1095aac7-8a71-4c56-b725-eca17fdf1284";
        String linkGeocode = "https://graphhopper.com/api/1/geocode?q=";
        String linkEnd = "&locale=en&debug=true&key=";
        StringBuilder jsonBuild = new StringBuilder();
        String json = "";
        String tempAddress = address;
        double lat;
        double lon;

        //Remove spaces in address for the URL
        tempAddress = tempAddress.replaceAll("\\s", "%20");

        //Build link for API request
        StringBuilder sb = new StringBuilder();
        sb.append(linkGeocode).append(tempAddress).append(linkEnd).append(key);

        // URL Request
        URL url = null;
        try {
            url = new URL(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                jsonBuild.append(line);
            }
            json = jsonBuild.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //now parse
        JSONParser parser = new JSONParser();
        Object jobj = null;
        try {
            jobj = parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jb = (JSONObject) jobj;

        //now read
        JSONArray hits = (JSONArray) jb.get("hits");
        JSONObject hitsIndent = (JSONObject) hits.get(0);
        JSONObject ghPoint = (JSONObject) hitsIndent.get("point");

        //Set the order's point
        lat = (double) ghPoint.get("lat");
        lon = (double) ghPoint.get("lng");

        return new GHPoint(lat, lon);
    }


    public void setRoute(GHPoint addrese1, GHPoint addresse2) {
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
