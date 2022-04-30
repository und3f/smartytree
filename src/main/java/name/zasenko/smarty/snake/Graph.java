package name.zasenko.smarty.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {
  final GameState.Board board;
  final Map<Integer, Integer> obstacles;

  Graph(GameState.Board board) {
    this.board = board;
    obstacles = new TreeMap<>();

    for (GameState.Snake snake : board.getSnakes()) {
      int ttl = 0;
      List<Point> body = snake.getBody();

      for (int i = body.size() - 1; i >= 0; i--) {
        if (ttl > 0) {
          Point obstacle = body.get(i);
          this.obstacles.put(board.valueOfPoint(obstacle), ttl);
        }
        ttl++;
      }
    }
  }

  public int V() {
    return board.getWidth() * board.getHeight();
  }

  public List<Integer> adj(int v, int time) {
    return adj(board.fromValue(v), time);
  }

  public List<Integer> pointsAround(Point startPoint, int time) {
    return Stream.of(Direction.values())
        .map(startPoint::move)
        .filter(board::isValid)
        .map(board::valueOfPoint)
        .filter(p -> !obstacles.containsKey(p) || obstacles.get(p) < time)
        .collect(Collectors.toList());
  }

  public List<Integer> adj(Point startPoint, int time) {
    if (obstacles.containsKey(board.valueOfPoint(startPoint)))
      return new ArrayList<>();

    return pointsAround(startPoint, time);
  }

  public String toString() {
    return null;
  }

  public class CC {
    private final boolean[] marked;
    private final int[] id;
    private int count;
    private final int[] componentsSize;

    public CC() {
      Graph G = Graph.this;
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
      for (int w : G.adj(v, 0))
        if (!marked[w])
          dfs(G, w);
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
      for (int y = 0; y < board.getHeight(); y++) {
        for (int x = 0; x < board.getWidth(); x++)
          sb.append(String.format("%3d ", id(board.valueOfPoint(new Point(y, x)))));
        sb.append("\n");
      }
      return sb.toString();
    }
  }
}
