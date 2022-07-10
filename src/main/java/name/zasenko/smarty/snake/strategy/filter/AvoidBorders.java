package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;

import java.util.List;

public class AvoidBorders implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        final var board = ctx.getBoard();
        final var head = ctx.getMe().getHead();

        for (var it = possibleMoves.listIterator(); it.hasNext(); ) {
            if (board.movePoint(head, it.next()) == null) {
                it.remove();
            }
        }
    }
}
