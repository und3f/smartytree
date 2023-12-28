package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;

import java.util.List;
import java.util.TreeSet;

public class AvoidFood implements StrategyFilter {

    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        final var board = ctx.board();
        TreeSet<Point> food = new TreeSet<Point>(ctx.state().food());
        for (int i = 0; possibleMoves.size() > 1 && i < possibleMoves.size(); i++) {
            Direction direction = possibleMoves.get(i);

            if (food.contains(board.movePoint(ctx.state().me().head(), direction))) {
                possibleMoves.remove(i);
                i--;
            }
        }

    }

}
