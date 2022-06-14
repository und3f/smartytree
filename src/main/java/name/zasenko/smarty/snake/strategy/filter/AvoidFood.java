package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;

import java.util.List;
import java.util.TreeSet;

public class AvoidFood implements StrategyFilter {

    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        TreeSet<Point> food = new TreeSet<Point>(ctx.getBoard().getFood());
        for (int i = 0; possibleMoves.size() > 1 && i < possibleMoves.size(); i++) {
            Direction direction = possibleMoves.get(i);

            if (food.contains(ctx.getBoard().movePoint(ctx.getMe().getHead(), direction))) {
                possibleMoves.remove(i);
                i--;
            }
        }

    }

}
