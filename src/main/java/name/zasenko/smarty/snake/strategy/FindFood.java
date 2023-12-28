package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.entities.Snake;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FindFood implements Strategy {
    @Override
    public Direction findMove(Context ctx) {
        final var gameCtx = ctx.state();
        final var board = ctx.board();

        Snake me = gameCtx.me();
        final var possibleMoves = Utils.initPossibleDirections(ctx, me);


        if (possibleMoves.size() == 1) {
            return Utils.moveForward(ctx, possibleMoves);
        }

        Dijkstra dijkstra = new Dijkstra(ctx.boardGraph(), me.head());

        List<Point> targets = new ArrayList<Point>(gameCtx.food());

        targets.addAll(gameCtx.snakes()
                .stream().filter(snake -> snake.length() < me.length())
                .map(Snake::head)
                .collect(Collectors.toList())
        );

        Point closestTarget = Utils.findClosestPoint(targets, dijkstra);

        List<DirectedEdge> path = dijkstra.findPath(closestTarget);
        if (path == null || dijkstra.findDistance(closestTarget) > me.health()) {
            closestTarget = me.tail();
            path = dijkstra.findPath(closestTarget);
        }

        if (path == null || dijkstra.findDistance(closestTarget) > me.health()) {
            targets = possibleMoves.stream()
                    .map(direction -> board.movePoint(me.head(), direction))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            closestTarget = Utils.findClosestPoint(targets, dijkstra);
            path = dijkstra.findPath(closestTarget);
        }

        return Utils.moveThruPath(ctx, possibleMoves, path);
    }

}
