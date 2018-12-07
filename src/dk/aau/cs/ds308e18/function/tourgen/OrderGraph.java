package dk.aau.cs.ds308e18.function.tourgen;

import dk.aau.cs.ds308e18.model.Order;

public class OrderGraph {
    //adjacency matrix
    private int[][] edges;
    private Order[] nodes;

    public OrderGraph(int orderAmount) {
        edges = new int[orderAmount][orderAmount];
        nodes = new Order[orderAmount];
    }

    public void setNode(Order node, int nodeNumber) {
        nodes[nodeNumber] = node;
    }

    public Order getNode(int nodeNumber) {
        return nodes[nodeNumber];
    }

    //Use orderID for source and destination
    public void addEdge(int source, int destination, int weight) {
        edges[source][destination] = weight;
    }

    public int getWeight(int source, int destination) {
        return edges[source][destination];
    }
}
