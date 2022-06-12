package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.strategy.filter.*;

import java.util.List;

import static name.zasenko.smarty.snake.strategy.Utils.initDirections;

public class Constrictor implements Strategy {

    @Override
    public Direction findMove(Context ctx) {
        if (ctx.getTurn() < 5) {
            final int center = ctx.getBoard().valueOfPoint(new Point(ctx.getBoard().getHeight()/2, ctx.getBoard().getWidth()/2));
            Dijkstra d = new Dijkstra(ctx.getBoardGraph(), ctx.getMe().getHead());
            List<DirectedEdge> pathToCenter = d.findPath(center);

            if (pathToCenter != null) {
                final Point destination = ctx.getBoard().fromValue(pathToCenter.get(0).getDestination());
                return ctx.getMe().getHead().directionTo(destination)[0];
            }
        }

        List<Direction> possibleMoves = initDirections(ctx, ctx.getMe());

        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpacesWithoutExpansion().filterMoves(ctx, possibleMoves);

        return Utils.moveForward(ctx, possibleMoves);
    }

}
