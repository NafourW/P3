package dk.aau.cs.ds308e18;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraphhopperTest {

    public static void main(String[] args) {

        // create singleton
        GraphHopper hopper = new GraphHopper().forServer();

        ClassLoader classLoader = getClass().getClassLoader();
        

        hopper.setOSMFile(classLoader.getResource("europe_denmark-gh").getFile());

// where to store graphhopper files?
        hopper.setGraphHopperLocation("graphFolder");
        hopper.setEncodingManager(new EncodingManager("car"));

// now this can take minutes if it imports or a few seconds for loading
// of course this is dependent on the area you import
        hopper.importOrLoad();

        // simple configuration of the request object, see the GraphHopperServlet classs for more possibilities.
        GHRequest req = new GHRequest(/* latFrom, lonFrom, latTo, lonTo */).
                setWeighting("fastest").
                setVehicle("car").
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


