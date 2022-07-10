package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra {
    private final Graph G;
    private final double[] distTo;
    private final DirectedEdge[] edgeTo;
    private final IndexMinPQ<Double> pq;
    final private int source;

    public Dijkstra(Graph graph, int source) {
        G = graph;
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        this.source = source;

        pq = new IndexMinPQ<Double>(G.V());
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);

        distTo[source] = 0.0;
        pq.insert(source, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            // TODO: add ttl
            for (DirectedEdge e : G.adj(v, 0)) {
                relax(e);
            }
        }
    }

    public Dijkstra(Graph graph, Point source) {
        this(graph, graph.board.valueOfPoint(source));
    }

    private void relax(DirectedEdge e) {
        int v = e.getSource(), w = e.getDestination();
        double newDistance = distTo[v] + e.getWeight();
        if (distTo[w] > newDistance) {
            distTo[w] = newDistance;
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, newDistance);
            else
                pq.insert(w, newDistance);
        }
    }

    public List<DirectedEdge> findPath(Point p) {
        if (p == null)
            return null;

        return findPath(G.board.valueOfPoint(p));
    }

    public List<DirectedEdge> findPath(int d) {
        ArrayList<DirectedEdge> path = new ArrayList<>();

        if (edgeTo[d] == null) {
            return null;
        }

        for (; d != this.source; ) {
            DirectedEdge edge = edgeTo[d];
            path.add(0, edge);
            d = edge.getSource();
        }

        return path;
    }

    public double findDistance(Point d) {
        return findDistance(G.board.valueOfPoint(d));
    }

    public double findDistance(int d) {
        return distTo[d];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = G.board.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < G.board.getWidth(); x++) {
                double distance = distTo[G.board.valueOfPoint(new Point(y, x))];
                if (distance != Double.POSITIVE_INFINITY)
                    sb.append(String.format("%4.1f", distance));
                else
                    sb.append(" ---");

                if (x != G.board.getWidth() - 1)
                    sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
