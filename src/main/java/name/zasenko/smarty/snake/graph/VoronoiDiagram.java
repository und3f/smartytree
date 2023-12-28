package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.Snake;

import java.util.*;

public class VoronoiDiagram {
    private final Map<String, Set<Point>> snakesAreas;

    public VoronoiDiagram(Context ctx) {
        snakesAreas = new HashMap<>();
        for (Snake snake : ctx.state().snakes()) {
            snakesAreas.put(snake.id(), new HashSet<>());
        }

        buildVoronoiDiagram(ctx);
    }

    private void buildVoronoiDiagram(Context ctx) {
        Graph g = ctx.boardGraph();
        List<Dijkstra> dijkstras = ctx.state().snakes().stream()
                .map(snake -> new Dijkstra(g, snake.head()))
                .toList();

        for (int y = 0; y < ctx.board().height(); y++) {
            for (int x = 0; x < ctx.board().width(); x++) {
                Point p = new Point(y, x);
                var snake = findAreaControllingSnake(ctx.state().snakes(), dijkstras, p);
                if (snake != null) {
                    snakesAreas.get(snake.id()).add(p);
                }
            }
        }
    }

    private static Snake findAreaControllingSnake(List<Snake> snakes, List<Dijkstra> dijkstras, Point p) {
        int closestSnakeInd = 0;
        double closestSnakeDistance = dijkstras.get(closestSnakeInd).findDistance(p);
        int closestSnakeSize = snakes.get(closestSnakeInd).length();

        for (int i = 1; i < dijkstras.size(); i++) {
            double snakeDistance = dijkstras.get(i).findDistance(p);
            if (snakeDistance > closestSnakeDistance) {
                continue;
            }

            int snakeSize = snakes.get(i).length();
            if (snakeDistance == closestSnakeDistance) {
                if (closestSnakeSize > snakeSize) {
                    continue;
                }

                if (closestSnakeSize == snakeSize) {
                    closestSnakeInd = -1;
                    continue;
                }
            }

            closestSnakeInd = i;
            closestSnakeDistance = snakeDistance;
            closestSnakeSize = snakeSize;
        }
        if (closestSnakeInd < 0) {
            return null;
        }

        return snakes.get(closestSnakeInd);
    }

    public Set<Point> getSnakeArea(Snake snake) {
        return snakesAreas.get(snake.id());
    }
}
