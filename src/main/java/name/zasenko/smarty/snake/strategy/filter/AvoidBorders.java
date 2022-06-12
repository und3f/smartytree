package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;

import java.util.List;

public class AvoidBorders implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        final var head = ctx.getMe().getHead();

        if (head.getX() == 0)
            possibleMoves.remove(Direction.left);
        else if (head.getX() == ctx.getBoard().getWidth() - 1)
            possibleMoves.remove(Direction.right);

        if (head.getY() == 0)
            possibleMoves.remove(Direction.down);
        else if (head.getY() == ctx.getBoard().getWidth() - 1)
            possibleMoves.remove(Direction.up);
    }
}
