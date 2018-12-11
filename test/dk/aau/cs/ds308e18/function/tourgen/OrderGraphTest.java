package dk.aau.cs.ds308e18.function.tourgen;

import dk.aau.cs.ds308e18.model.Order;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class OrderGraphTest {
    private OrderGraph graph = new OrderGraph();
    private GPS gps = new GPS();
    private Order order1 = new Order();
    private Order order2 = new Order();
    private Order order3 = new Order();
    private Order order4 = new Order();

    @Test
    public void calcPathTest() {
        //Set IDs

        order1.setOrderID(1);
        order2.setOrderID(2);
        order3.setOrderID(3);
        order4.setOrderID(4);

        //Set addresses
        order1.setAddress("scoresbysundvej 8");
        order2.setAddress("pontoppidanstræde");
        order3.setAddress("kroghstræde");
        order4.setAddress("selma lagerløfs vej 300");

        //request Lat/Lon

        order1.requestLatLonFromAddress();
        order2.requestLatLonFromAddress();
        order3.requestLatLonFromAddress();
        order4.requestLatLonFromAddress();


        //Create expected list of ints
        TIntList testList = new TIntArrayList();
        testList.add(1);
        testList.add(4);

        //Add edges to graph

        graph.setEdge(order1.getOrderID(), order2.getOrderID(), gps.getDistance(order1.getLatLon(), order2.getLatLon()));
        graph.setEdge(order2.getOrderID(), order3.getOrderID(), gps.getDistance(order2.getLatLon(), order3.getLatLon()));
        graph.setEdge(order3.getOrderID(), order4.getOrderID(), gps.getDistance(order3.getLatLon(), order4.getLatLon()));
        graph.setEdge(order1.getOrderID(), order4.getOrderID(), gps.getDistance(order1.getLatLon(), order4.getLatLon()));

        System.out.println(gps.getDistance(order1.getLatLon(), order2.getLatLon()));
        System.out.println(gps.getDistance(order2.getLatLon(), order3.getLatLon()));
        System.out.println(gps.getDistance(order3.getLatLon(), order4.getLatLon()));
        System.out.println(gps.getDistance(order1.getLatLon(), order4.getLatLon()));



        //graph.setEdge(1, 2, 100);
        //Save graph
        graph.flush();

        //Find shortest path
        //System.out.println(graph.calcPath(1, 2).toString());
        graph.calcPath(order1.getOrderID(), order4.getOrderID());
        Assertions.assertEquals(testList.toString(), graph.calcPath(order1.getOrderID(), order4.getOrderID()).toString());
    }
}



//rip ass

/*
public class OrderGraphTest {
    OrderGraph graph = new OrderGraph(4);
    Order order = new Order();


    @Test
    public void getNodeTest() {
        graph.setNode(order, 0);

        Assertions.assertEquals(order, graph.getNode(0));
    }

    @Test
    public void getWeightTest() {
        graph.addEdge(0, 0, 200);

        Assertions.assertEquals(200, graph.getWeight(0, 0));
    }
}
*/
