package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;

import java.util.ArrayList;
import java.util.List;

public class LSP {
    int iterations = 15000;
    private List<DirectedEdge> longestPath = new ArrayList<>();

    public LSP(Graph graph, Point startPoint) {
        boolean[] marked = new boolean[graph.V()];
        int v = graph.board.valueOfPoint(startPoint);
        ArrayList<DirectedEdge> path = new ArrayList<>();

        dfs(graph, v, marked, path);
    }

    private void dfs(Graph graph, int v, boolean[] marked, ArrayList<DirectedEdge> path) {
        if (iterations-- <= 0)
            return;

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
