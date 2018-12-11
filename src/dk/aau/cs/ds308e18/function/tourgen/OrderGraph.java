package dk.aau.cs.ds308e18.function.tourgen;


import com.graphhopper.routing.Dijkstra;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.util.*;
import com.graphhopper.storage.GraphBuilder;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.util.EdgeIteratorState;
import gnu.trove.list.TIntList;

public class OrderGraph {
    private FlagEncoder encoder = new CarFlagEncoder();
    private EncodingManager em = new EncodingManager(encoder);
    //private Weighting weight = new FastestWeighting(encoder);
    private FastestWeighting weight = new FastestWeighting(encoder);
    private TraversalMode tmode = TraversalMode.EDGE_BASED_1DIR;

    private GraphBuilder gb = new GraphBuilder(em)
            .setLocation("resources/graphhopper_graphFolder")
            .setStore(true);
    private GraphStorage graph = gb.create();

    public OrderGraph() {
    }

    // Make a weighted edge between two nodes.
    public void setEdge(int IdIn, int IdOut, double distance) {
        EdgeIteratorState edge = graph.edge(IdIn, IdOut);
        edge.setDistance(distance);
        edge.setFlags(encoder.setProperties(80, true, true));
    }

    public TIntList calcPath(int start, int end) {
        graph.flush();
        GraphStorage graphStorage = gb.load();
        Path path = new Dijkstra(graphStorage.getBaseGraph(), encoder, weight, tmode).calcPath(start, end);

        return path.calcNodes();
    }

    public void flush() {
        // Flush to disc
        graph.flush();
    }
}

//Don't look below






    /*
        String graphFolder = "resources/graphFolder";
    private FlagEncoder encoder = new CarFlagEncoder();
    private EncodingManager em = new EncodingManager(encoder);
    private Weighting weight = new FastestWeighting(encoder);

    // Creating and saving the graph
    private GraphBuilder gb = new GraphBuilder(em).
            setLocation(graphFolder).
            setStore(true).
            setCHGraph(weight);
    private GraphHopperStorage graph = gb.create();
    private Directory dir = graph.getDirectory();
    // Create a new edge between two nodes, set access, distance, speed, geometry, ..

    // Prepare the graph for fast querying
    private TraversalMode tMode = TraversalMode.NODE_BASED;
    private GraphHopper hopper = new GraphHopperOSM().forServer();
    private CHGraph chGraph = graph.getGraph(CHGraph.class, weight);
    private PrepareContractionHierarchies pch = new PrepareContractionHierarchies(dir, graph, chGraph, weight, tMode);

    public OrderGraph() {
        hopper.setDataReaderFile("C:/Users/the_p/Desktop/graphhopper-0.8/denmark-latest.osm.pbf");
        hopper.setGraphHopperLocation(graphFolder);
        hopper.setEncodingManager(new EncodingManager("car"));
        hopper.importOrLoad();
    }

    public void setEdge(int fromID, int toID, double distance) {
        EdgeIteratorState edge = graph.edge(fromID, toID);
        edge.setDistance(distance);
    }


    public void startPch() {
        pch.doWork();
    }

    // flush after preparation!
    public void flushGraph() {
        graph.flush();
    }

    public void CalcPath(Order orderFrom, Order orderTo) {
        // Load and use the graph
        GraphHopperStorage graph = gb.load();

        // Load index
        LocationIndex index = new LocationIndexTree(graph.getBaseGraph(), new RAMDirectory(graphFolder, true));
        //LocationIndex index = hopper.getLocationIndex();
        if (!index.loadExisting())
            throw new

                    IllegalStateException("location index cannot be loaded!");

        // calculate path is identical
        //QueryResult fromQR = index.findClosest(orderFrom.getLatLon().lat, orderFrom.getLatLon().lon, EdgeFilter.ALL_EDGES);
        //QueryResult toQR = index.findClosest(orderTo.getLatLon().lat, orderTo.getLatLon().lon, EdgeFilter.ALL_EDGES);
        //QueryGraph queryGraph = new QueryGraph(graph);
        //queryGraph.lookup(fromQR, toQR);



        // create the algorithm using the PrepareContractionHierarchies object
        AlgorithmOptions algoOpts = AlgorithmOptions.start().
                algorithm(Parameters.Algorithms.DIJKSTRA_BI).traversalMode(tMode).weighting(weight).
                build();
        RoutingAlgorithm algorithm = pch.createAlgo(graph, algoOpts);
        Path path = algorithm.calcPath(1, 4);
        //return path.calcNodes();
        System.out.println(path.getDistance());
    }
     */
