package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.graph.Graph;
import name.zasenko.smarty.snake.graph.GraphFoodHazard;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidClosedSpaces;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

import java.util.stream.Collectors;

public class Cycle implements Strategy {

    public static final int CORNER = 0;

    @Override
    public Direction findMove(Context ctx) {
        final var me = ctx.getMe();
        final var board = ctx.getBoard();

        if (me.getLength() == 0)
            return Direction.down;

        final var possibleMoves = Utils.initDirections(ctx, ctx.getMe());
        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpaces().filterMoves(ctx, possibleMoves);

        if (possibleMoves.size() == 1) {
            return possibleMoves.get(0);
        }

        final var body = ctx.getMe().getBody();
        final Point head = ctx.getMe().getHead();
        final Point tail = ctx.getMe().tail();

        Dijkstra foodDijkstra = new Dijkstra(ctx.getBoardGraph(), head);
        Point closestFood = FindFood.findClosestFood(ctx.getBoard().getFood(), foodDijkstra);
        int foodReserve = 3;
        if (foodDijkstra.findDistance(closestFood) + foodReserve > ctx.getMe().getHealth()) {
            // System.out.println("Move to food");
            return Utils.moveThruPath(ctx, possibleMoves, foodDijkstra.findPath(closestFood));
        }

        // Do not enter closed without insufficient health
        final int tailOffset = ctx.getMe().isNextToTail() ? 1 : 0;
        if (ctx.getBoardGraph().adj(ctx.getMe().tail(tailOffset), 0).size() <= 1) {
            if (ctx.getMe().getHealth() <= ctx.getMe().getLength()) {
                // System.out.println("Last chance to eat");
                return Utils.moveThruPath(ctx, possibleMoves, foodDijkstra.findPath(closestFood));
            }
        }

        // Try to avoid food
        Graph foodHazard = new GraphFoodHazard(ctx.getBoardGraph());
        Dijkstra dijkstra = new Dijkstra(foodHazard, head);

        if (dijkstra.findDistance(CORNER) != Double.POSITIVE_INFINITY) {
            var path = dijkstra.findPath(CORNER);
            return Utils.moveThruPath(ctx, possibleMoves, path);
        }

        if (me.isNextToTail() && !me.isExpanding()) {
            // System.out.println("Keep catching Tail");
            return Utils.moveTowards(ctx, possibleMoves, tail);
        }

        // System.out.println("To tail");
        var aroundTail = ctx.getBoardGraph()
                .pointsAround(ctx.getMe().tail(1), 0)
                .stream().map(DirectedEdge::getDestination).collect(Collectors.toList());

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
