package dk.aau.cs.ds308e18;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraphhopperTestMain {

    public static void main(String[] args) throws MalformedURLException {

        String vehicle = "car";

        // create singleton
        GraphHopper hopper = new GraphHopper().forServer();

        ClassLoader classLoader = GraphhopperTestMain.class.getClassLoader();

        //File osm = new File("C:/Users/the_p/Desktop/graphhopper/europe_denmark.osm");

        //hopper.setOSMFile(osm.getAbsolutePath());


        // URL TING
        URL url = new URL("https://graphhopper.com/api/1/geocode?q=Aalborg&locale=en&debug=true&key=[YOUR_KEY]");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // where to store graphhopper files?
        hopper.setGraphHopperLocation("resources/graphFolder");
        hopper.setEncodingManager(new EncodingManager(vehicle));

        // now this can take minutes if it imports or a few seconds for loading
        // of course this is dependent on the area you import
        hopper.importOrLoad();

        // simple configuration of the request object, see the GraphHopperServlet classs for more possibilities.
        GHRequest req = new GHRequest(/* latFrom, lonFrom, latTo, lonTo */).
                setWeighting("fastest").
                setVehicle(vehicle).
                setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);

        // first check for errors
        if (rsp.hasErrors()) {
            // handle them!
            // rsp.getErrors()
            return;
        }

        // points, distance in meters and time in millis of the full path
        PointList pointList = rsp.getPoints();
        double distance = rsp.getDistance();
        long timeInMs = rsp.getMillis();

        InstructionList il = rsp.getInstructions();
        // iterate over every turn instruction
        for (Instruction instruction : il) {
            instruction.getDistance();

        }

        // or get the json
        List<Map<String, Object>> iList = il.createJson();

        // or get the result as gpx entries:
        List<GPXEntry> list = il.createGPXList();
    }

}


