package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Voronoi {
    final private Graph graph;

    public Voronoi(Graph graph) {
        this.graph = graph;
    }

    public List<List<Point>> buildDiagram(List<Point> points) {
        List<List<Point>> areas = new ArrayList<>(points.size());
        for (int i = 0; i < points.size(); i++) {
            areas.add(new ArrayList<>());
        }

        Set snakePoints = graph.board.getSnakes().stream().map(s -> s.getBody()).flatMap(Collection::stream).collect(Collectors.toSet());

        List<Dijkstra> dijkstras = new ArrayList<>(points.size());
        for (var point : points) {
            dijkstras.add(new Dijkstra(graph, point));
        }

        for (int y = graph.board.getHeight() - 1; y >= 0; y--) {
            for (int x = graph.board.getWidth() - 1; x >= 0; x--) {
                Point p = new Point(y, x);
                if (snakePoints.contains(p)) {
                    continue;
                }

                double closestDistance = Double.POSITIVE_INFINITY;
                int closestPointIndex = -1;

                for (int i = points.size() - 1; i >= 0; i--) {
                    double distance = dijkstras.get(i).findDistance(p);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPointIndex = i;
                    } else if (distance == closestDistance) {
                        closestPointIndex = -1;
                    }
                }

                if (closestPointIndex != -1) {
                    areas.get(closestPointIndex).add(p);
                }
            }
        }

        return areas;
    }
}
