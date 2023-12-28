package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidClosedSpaces;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

import java.util.stream.Stream;

public class FindTail implements Strategy {
    final static int MinHealth = 10;

    @Override
    public Direction findMove(Context ctx) {
        Direction towardsTail = moveToTail(ctx);
        if (ctx.state().me().health() >= MinHealth)
            return towardsTail;
        else {
            // Move towards food
            final var board = ctx.board();
            final var food = ctx.state().food();
            final var head = ctx.state().me().head();

            return Stream.of(towardsTail, towardsTail.rotateClockwise(), towardsTail.rotateCounterclockwise())
                    .filter(direction -> food.contains(board.movePoint(head, direction)))
                    .findFirst()
                    .orElse(new FindFood().findMove(ctx));
        }
    }

    private Direction moveToTail(Context ctx) {
        final var head = ctx.state().me().head();
        var possibleMoves = Utils.initDirections(ctx, ctx.state().me());
        final var body = ctx.state().me().body();
        final Point tail = body.get(body.size() - 1);

        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpaces().filterMoves(ctx, possibleMoves);

        if (head.manhattanTo(tail) > 1 || !(body.get(body.size() - 2).equals(tail))) {
            System.out.println("Moving to tail");
            return Utils.moveTowards(ctx, possibleMoves, tail);
        }

        System.out.println("Moving forward");
        return Utils.moveForward(ctx, possibleMoves);
    }
}