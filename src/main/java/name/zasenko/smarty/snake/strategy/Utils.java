package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.entities.Snake;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.graph.LSP;
import name.zasenko.smarty.snake.strategy.filter.AvoidProblems;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static List<Direction> initDirections(Context context, Snake snake) {
        Direction forwardDirection = snake.headDirection();

        return new ArrayList<>(Arrays.asList(
                forwardDirection,
                forwardDirection.rotateClockwise(),
                forwardDirection.rotateCounterclockwise()));
    }

    public static List<Direction> initPossibleDirections(Context context, Snake snake) {
        List<Direction> directions = initDirections(context, snake);
        new AvoidProblems().filterMoves(context, directions);

        return directions;
    }

    public static Direction moveForward(Context context, List<Direction> possibleMoves) {
        if (possibleMoves.size() == 0)
            return context.state().me().headDirection();

        return possibleMoves.get(0);
    }

    public static Direction moveTowards(Context context, List<Direction> possibleMoves, Point target) {
        if (possibleMoves.size() == 1)
            return moveForward(context, possibleMoves);

        Set<Direction> possibleMovesToTarget = Arrays.stream(context.state().me().head().directionTo(target))
                .distinct()
                .filter(possibleMoves::contains)
                .collect(Collectors.toSet());

        if (possibleMovesToTarget.size() > 0) {
            possibleMoves = new ArrayList<>(possibleMovesToTarget);
        }

        return moveForward(context, possibleMoves);
    }

    public static Direction moveThruPath(Context context, List<Direction> possibleMoves, List<DirectedEdge> path) {
        if (path == null || path.size() == 0) {
            return moveForward(context, possibleMoves);
        }

        Point nextPoint = context.board().fromValue(path.get(0).getDestination());
        return moveTowards(context, possibleMoves, nextPoint);
    }


    public static Direction randomMove(List<Direction> possibleMoves) {
        final int choice = new Random().nextInt(possibleMoves.size());
        return possibleMoves.get(choice);
    }

    public static Point findClosestPoint(List<Point> points, Dijkstra dijkstra) {
        if (points.size() == 0)
            return null;

        double[] distances = new double[points.size()];
        PriorityQueue<Integer> distancePQ = new PriorityQueue<>(points.size(),
                Comparator.comparingDouble(i -> distances[i])
        );

        for (ListIterator<Point> it = points.listIterator(); it.hasNext(); ) {
            final int i = it.nextIndex();
            distances[i] = dijkstra.findDistance(it.next());
            distancePQ.add(i);
        }

        Integer closestPointIndex = distancePQ.poll();
        return points.get(closestPointIndex);
    }

    public static Point findClosestPoint(List<Point> points, Dijkstra[] waypoints) {
        if (points.size() == 0)
            return null;

        double[] distances = new double[points.size()];
        PriorityQueue<Integer> distancePQ = new PriorityQueue<>(points.size(),
                Comparator.comparingDouble(i -> distances[i])
        );

        for (ListIterator<Point> it = points.listIterator(); it.hasNext(); ) {
            final int i = it.nextIndex();
            Point dst = it.next();

            double distance = 0.;
            for (Dijkstra d : waypoints)
                distance += d.findDistance(dst);

            distances[i] = distance;
            distancePQ.add(i);
        }

        Integer closestPointIndex = distancePQ.poll();
        return points.get(closestPointIndex);
    }

    public static Direction fillSpace(Context ctx, List<Direction> possibleMoves) {
        List<DirectedEdge> path = new LSP(ctx.boardGraph(), ctx.state().me().head()).findLongestPath();
        if (path != null) {
            return Utils.moveThruPath(ctx, possibleMoves, path);
        }

        return Utils.moveForward(ctx, possibleMoves);
    }
}