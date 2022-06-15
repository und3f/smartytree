package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.strategy.filter.*;

import java.util.List;
import java.util.stream.Collectors;

import static name.zasenko.smarty.snake.strategy.Utils.initDirections;

public class Constrictor implements Strategy {

    @Override
    public Direction findMove(Context ctx) {
        GameState.Snake me = ctx.getMe();
        List<Direction> possibleMoves = initDirections(ctx, me);

        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpacesWithoutExpansion().filterMoves(ctx, possibleMoves);

        Dijkstra d = new Dijkstra(ctx.getBoardGraph(), me.getHead());

        List<Point> targets = ctx.getBoard().getSnakes()
                .stream().filter(snake -> !snake.equals(me)).map(snake -> snake.getHead())
                .collect(Collectors.toList());

        Point target = Utils.findClosestPoint(targets, d);
        List<DirectedEdge> path = d.findPath(target);

        // A lot of TODOs

        return Utils.moveThruPath(ctx, possibleMoves, path);
    }

}
