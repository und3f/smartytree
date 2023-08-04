package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.CC;
import name.zasenko.smarty.snake.strategy.filter.AreaControl;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidClosedSpacesWithoutExpansion;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static name.zasenko.smarty.snake.strategy.Utils.initDirections;

public class Constrictor implements Strategy {
    @Override
    public Direction findMove(Context ctx) {
        List<Direction> possibleMoves = initConstrictorModePossibleMoves(ctx);

        if (!isSnakeAlone(ctx)) {
            new AreaControl().filterMoves(ctx, possibleMoves);
            return Utils.moveForward(ctx, possibleMoves);
        }

        return Utils.fillSpace(ctx, possibleMoves);
    }

    public boolean isSnakeAlone(Context ctx) {
        GameState.Snake me = ctx.getMe();
        // TODO cache CC computation
        CC cc = new CC(ctx.getBoardGraph(), ctx.getMe().getHealth());

        Set<Integer> clusters = getSnakeClustersStream(ctx, me, cc).collect(Collectors.toSet());

        for (GameState.Snake snake : ctx.getBoard().getSnakes()) {
            if (snake.equals(me))
                continue;

            if (getSnakeClustersStream(ctx, snake, cc).anyMatch(clusters::contains))
                return false;
        }

        return true;
    }

    private List<Direction> initConstrictorModePossibleMoves(Context ctx) {
        List<Direction> possibleMoves = initDirections(ctx, ctx.getMe());
        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpacesWithoutExpansion().filterMoves(ctx, possibleMoves);
        return possibleMoves;
    }

    private Stream<Integer> getSnakeClustersStream(Context ctx, GameState.Snake snake, CC cc) {
        GameState.Board board = ctx.getBoard();
        return Utils.initDirections(ctx, snake).stream()
                .map(d -> board.movePoint(snake.getHead(), d))
                .filter(Objects::nonNull)
                .map(p -> cc.id(board.valueOfPoint(p)));
    }

    private GameState.Snake moveSnake(GameState.Board board, GameState.Snake snake, Direction direction) {
        var newHead = board.movePoint(snake.getHead(), direction);
        List<Point> newBody = new ArrayList<>(snake.getLength() + 1);
        newBody.add(newHead);
        newBody.addAll(snake.getBody());

        GameState.Snake newSnake = new GameState.Snake();
        newSnake.setHead(newHead);
        newSnake.setBody(newBody);
        newSnake.setHealth(snake.getHealth());
        newSnake.setLength(newBody.size());

        return newSnake;
    }

}
