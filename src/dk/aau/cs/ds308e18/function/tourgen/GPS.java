package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.model.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    //Strings for building the API URL
    private String key = "1095aac7-8a71-4c56-b725-eca17fdf1284";
    private String linkGeocode = "https://graphhopper.com/api/1/geocode?q=";
    private String linkEnd = "&locale=en&debug=true&key=";
    private String vehicle = "car";
    private GHResponse rsp;

    GraphHopper hopper = new GraphHopper().forServer();


    GHPoint GeocodeAddress(Order order) throws MalformedURLException, ParseException {

        StringBuilder jsonBuild = new StringBuilder();
        String json = "";
        String tempAddress = order.getAddress();
        double lat;
        double lon;

        //Remove spaces in address for the URL
        tempAddress = tempAddress.replaceAll("\\s", "%20");

        //Build link for API request
        StringBuilder sb = new StringBuilder();
        sb.append(linkGeocode).append(tempAddress).append(linkEnd).append(key);

        // URL Request
        URL url = new URL(sb.toString());
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
        Object jobj = parser.parse(json);
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
