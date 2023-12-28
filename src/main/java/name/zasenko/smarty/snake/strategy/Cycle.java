package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.graph.Graph;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidClosedSpaces;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

import java.util.List;

public class Cycle implements Strategy {

    public static final int CORNER = 0;

    @Override
    public Direction findMove(Context ctx) {
        final var me = ctx.me();

        if (me.length() == 0)
            return Direction.down;

        final var possibleMoves = Utils.initDirections(ctx, ctx.me());
        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpaces().filterMoves(ctx, possibleMoves);

        if (possibleMoves.size() == 1) {
            return possibleMoves.get(0);
        }

        final Point head = ctx.me().head();
        final Point tail = ctx.me().tail();

        Dijkstra dijkstraHead = new Dijkstra(ctx.boardGraph(), head);
        Point closestFood = Utils.findClosestPoint(ctx.gameStateContext().food(), dijkstraHead);

        if (ctx.turn() > 700)
            return Utils.fillSpace(ctx, possibleMoves);

        int foodReserve = 3;
        if (dijkstraHead.findDistance(closestFood) + foodReserve > ctx.me().health()) {
            // System.out.println("Move to food");
            return Utils.moveThruPath(ctx, possibleMoves, dijkstraHead.findPath(closestFood));
        }

        // Do not enter closed without insufficient health
        final int tailOffset = ctx.me().isNextToTail() ? 1 : 0;
        if (ctx.boardGraph().adj(ctx.me().tail(tailOffset), 0).size() <= 1) {
            if (ctx.me().health() <= ctx.me().length()) {
                // System.out.println("Last chance to eat");
                return Utils.moveThruPath(ctx, possibleMoves, dijkstraHead.findPath(closestFood));
            }
        }

        // Try to avoid food
        Graph foodHazard = Graph.createFoodHazardGraph(ctx.gameStateContext());
        Dijkstra dijkstra = new Dijkstra(foodHazard, head);

        if (dijkstra.findDistance(CORNER) != Double.POSITIVE_INFINITY) {
            var path = dijkstra.findPath(CORNER);
            return Utils.moveThruPath(ctx, possibleMoves, path);
        }

        if (me.isNextToTail() && !me.isExpanding()) {
            // System.out.println("Keep catching Tail");
            return Utils.moveTowards(ctx, possibleMoves, tail);
        }

        return moveToTail(ctx, possibleMoves, dijkstra);
    }

    private Direction moveToTail(Context ctx, List<Direction> possibleMoves, Dijkstra dijkstra) {
        // System.out.println("To tail");
        var aroundTail = ctx.boardGraph()
                .pointsAround(ctx.me().tail(1), 0)
                .stream().map(DirectedEdge::getDestination).toList();

        if (aroundTail.size() < 1)
            return Utils.randomMove(possibleMoves);

        int destination = aroundTail.get(0);
        for (int p : aroundTail) {
            if (dijkstra.findDistance(destination) > dijkstra.findDistance(p)) {
                destination = p;
            }
        }

        var path = dijkstra.findPath(destination);
        return Utils.moveThruPath(ctx, possibleMoves, path);
    }

}
