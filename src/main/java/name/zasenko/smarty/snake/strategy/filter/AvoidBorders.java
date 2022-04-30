package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;

import java.util.List;

public class AvoidBorders implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        if (ctx.getHead().getX() == 0)
            possibleMoves.remove(Direction.left);
        else if (ctx.getHead().getX() == ctx.getBoard().getWidth() - 1)
            possibleMoves.remove(Direction.right);

        if (ctx.getHead().getY() == 0)
            possibleMoves.remove(Direction.down);
        else if (ctx.getHead().getY() == ctx.getBoard().getWidth() - 1)
            possibleMoves.remove(Direction.up);
    }
}
