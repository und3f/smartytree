package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.graph.DirectedEdge;

import java.util.*;
import java.util.stream.Collectors;

public class FindFood implements Strategy {
    @Override
    public Direction findMove(Context ctx) {
        final var board = ctx.getBoard();

        GameState.Snake me = ctx.getMe();
        final var possibleMoves = Utils.initPossibleDirections(ctx, me);

        Dijkstra dijkstra = new Dijkstra(ctx.getBoardGraph(), me.getHead());

        if (possibleMoves.size() == 1) {
            return Utils.moveForward(ctx, possibleMoves);
        }

        List<Point> targets = new ArrayList<Point>(board.getFood());

        targets.addAll(ctx.getBoard().getSnakes()
                .stream().filter(snake -> snake.getLength() < me.getLength())
                .map(GameState.Snake::head)
                .collect(Collectors.toList())
        );

        Point closestTarget = Utils.findClosestPoint(targets, dijkstra);

        List<DirectedEdge> path = dijkstra.findPath(closestTarget);
        if (path == null) {
            path = dijkstra.findPath(me.tail());
        }

        if (path == null) {
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
