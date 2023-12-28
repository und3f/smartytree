package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.entities.Snake;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.strategy.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class AreaControl implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        if (possibleMoves.size() <= 1)
            return;

        Snake me = ctx.state().me();
        Dijkstra d = new Dijkstra(ctx.boardGraph(), me.head());

        List<Point> targets = ctx.state().snakes()
                .stream().filter(snake -> !snake.equals(me)).map(Snake::head)
                .collect(Collectors.toList());

        Point target = Utils.findClosestPoint(targets, d);
        List<DirectedEdge> path = d.findPath(target);

        Direction direction = Utils.moveThruPath(ctx, possibleMoves, path);
        possibleMoves.clear();
        possibleMoves.add(direction);
    }
}
