package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidClosedSpaces;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

import java.util.stream.Stream;

public class FindTail implements Strategy {
    final static int MinHealth = 10;

    @Override
    public Direction findMove(Context ctx) {
        Direction towardsTail = moveToTail(ctx);
        if (ctx.getMe().getHealth() >= MinHealth)
            return towardsTail;
        else {
            // Move towards food
            final var food = ctx.getBoard().getFood();
            final var head = ctx.getMe().getHead();

            return Stream.of(towardsTail, towardsTail.rotateClockwise(), towardsTail.rotateCounterclockwise())
                    .filter(direction -> food.contains(head.move(direction)))
                    .findFirst()
                    .orElse(new FindFood().findMove(ctx));
        }
    }

    private Direction moveToTail(Context ctx) {
        final var head = ctx.getMe().getHead();
        var possibleMoves = Utils.initDirections(ctx, ctx.getMe());
        final var body = ctx.getMe().getBody();
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