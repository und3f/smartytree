package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;

import java.util.List;

public interface StrategyFilter {
    void filterMoves(Context ctx, List<Direction> possibleMoves);
}
