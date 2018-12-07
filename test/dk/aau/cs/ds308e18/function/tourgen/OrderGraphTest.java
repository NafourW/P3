package dk.aau.cs.ds308e18.function.tourgen;

import dk.aau.cs.ds308e18.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
