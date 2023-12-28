package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.BoardContext;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.Snake;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {
    public static final int MOVE_WEIGHT = 1;
    public static final int DEFAULT_HAZARD_WEIGHT = 14;

    protected final Map<Integer, Integer> hazards;
    final BoardContext board;
    private final int hazardWeight;
    private final Map<Integer, Integer> obstacles;

    public Graph(BoardContext board, int hazardWeight) {
        this.board = board;
        this.hazardWeight = hazardWeight;
        obstacles = new TreeMap<>();
        hazards = new TreeMap<>();
    }

    public static Graph createGenericGameGraph(Context context) {
        var gameStateContext = context.state();
        var graph = new Graph(context.board(), gameStateContext.hazardDamage());

        for (Snake snake : gameStateContext.snakes()) {
            int ttl = 0;
            List<Point> body = snake.body();

            for (int i = body.size() - 1; i >= 0; i--) {
                if (ttl > 0) {
                    Point obstacle = body.get(i);
                    graph.obstacles.put(graph.board.valueOfPoint(obstacle), ttl);
                }
                ttl++;
            }
        }

        for (Point p : gameStateContext.hazards()) {
            int v = graph.board.valueOfPoint(p);
            int weight = graph.hazardWeight + graph.hazards.getOrDefault(v, 0);
            graph.hazards.put(graph.board.valueOfPoint(p), weight);
        }

        return graph;
    }

    public static Graph createFoodHazardGraph(Context ctx) {
        Graph graph = new Graph(ctx.board(), ctx.state().hazardDamage());

        for (Point p : ctx.state().food()) {
            graph.hazards.put(graph.board.valueOfPoint(p), DEFAULT_HAZARD_WEIGHT);
        }

        return graph;
    }

    public int V() {
        return board.width() * board.height();
    }

    public List<DirectedEdge> adj(int v, int time) {
        return adj(board.fromValue(v), time);
    }

    public List<DirectedEdge> adj(Point startPoint, int time) {
        return pointsAround(startPoint, time);
    }

    public boolean isObstacle(Point p) {
        return obstacles.containsKey(board.valueOfPoint(p));
    }

    public boolean isObstacle(int v) {
        return isObstacle(board.fromValue(v));
    }

    public List<DirectedEdge> pointsAround(Point startPoint, int time) {
        int startPointV = board.valueOfPoint(startPoint);

        return Stream.of(Direction.values())
                .map(direction -> board.movePoint(startPoint, direction))
                .filter(board::isValid)
                .map(board::valueOfPoint)
                .filter(p -> !obstacles.containsKey(p) || obstacles.get(p) < time)
                .map(destination -> new DirectedEdge(startPointV, destination, getMoveWeight(destination)))
                .collect(Collectors.toList());
    }

    private double getMoveWeight(int destination) {
        return hazards.getOrDefault(destination, MOVE_WEIGHT);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int v = 0; v < this.V(); v++) {
            List<DirectedEdge> adj = this.adj(v, 0);
            if (adj.size() == 0) continue;

            sb.append(this.board.fromValue(v));
            sb.append(" -> ");
            sb.append(adj.stream()
                    .map(d -> this.board.fromValue(d.getDestination()).toString())
                    .collect(Collectors.joining(", "))
            );
            sb.append("\n");
        }

        return sb.toString();
    }

}
