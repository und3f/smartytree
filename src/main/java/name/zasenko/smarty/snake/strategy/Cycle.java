package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

public class Cycle implements Strategy {

    @Override
    public Direction findMove(Context ctx) {
        final var me = ctx.getGameState().getYou();
        final var board = ctx.getBoard();

        if (me.getLength() == 0)
            return Direction.right;

        final var possibleMoves = Utils.initDirections(ctx);
        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        // new AvoidClosedSpaces().filterMoves(ctx, possibleMoves);

        final Direction direction = me.headDirection();
        if (direction == Direction.down) {
            return Utils.moveForward(ctx, possibleMoves);
        }

        if (me.head().getY() != board.getHeight() - 1) {
            possibleMoves.remove(Direction.down);
            if (me.head().getX() <= 2)
                possibleMoves.remove(Direction.left);
        }

        if (direction.equals(Direction.up)) {
            possibleMoves.remove(direction);
        }

        /*
         * // Avoid catching the tail
         * if (me.isCatchingTail(ctx) &&
         * me.tail().move(Direction.up).equals(me.tail(1))) {
         * return Direction.up;
         * }
         */

        return Utils.moveForward(ctx, possibleMoves);
    }

}
