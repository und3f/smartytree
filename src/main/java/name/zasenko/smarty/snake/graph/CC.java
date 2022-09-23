package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;

public class CC {
    private final Graph G;
    private final boolean[] marked;
    private final int[] id;
    private int count;
    private final int[] componentsSize;
    private final int maxHazard;

    public CC(Graph graph, int maxHazard) {
        this.G = graph;
        this.maxHazard = maxHazard;

        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }

        componentsSize = new int[count];
        for (int v : id) {
            componentsSize[v]++;
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (DirectedEdge edge : G.adj(v, 0)) {
            if (!marked[edge.getDestination()] && !G.isObstacle(v) && edge.getWeight() < maxHazard) {
                dfs(G, edge.getDestination());
            }
        }
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int componentSize(int v) {
        return componentsSize[id[v]];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < G.board.getHeight(); y++) {
            for (int x = 0; x < G.board.getWidth(); x++)
                sb.append(String.format("%3d ", id(G.board.valueOfPoint(new Point(y, x)))));
            sb.append("\n");
        }
        return sb.toString();
    }
}
