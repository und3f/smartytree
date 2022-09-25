package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;

import java.util.ArrayList;
import java.util.List;

public class TSP {
    private List<DirectedEdge> longestPath = new ArrayList<>();

    public TSP(Graph graph, Point startPoint) {
        boolean[] marked = new boolean[graph.V()];
        int v = graph.board.valueOfPoint(startPoint);
        ArrayList<DirectedEdge> path = new ArrayList<>();

        dfs(graph, v, marked, path);
    }

    private void dfs(Graph graph, int v, boolean[] marked, ArrayList<DirectedEdge> path) {
        marked[v] = true;

        for (DirectedEdge edge : graph.adj(v, 0)) {
            if (!marked[edge.getDestination()]) {
                path.add(edge);
                dfs(graph, edge.getDestination(), marked, path);
                path.remove(path.size() - 1);
            }
        }

        if (path.size() > longestPath.size()) {
            longestPath = new ArrayList<>(path);
        }

        marked[v] = false;
    }

    public List<DirectedEdge> findLongestPath() {
        return longestPath;
    }
}
