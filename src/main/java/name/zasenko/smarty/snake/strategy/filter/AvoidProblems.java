package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.Direction;

import java.util.List;

public class AvoidProblems implements StrategyFilter {

    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpaces().filterMoves(ctx, possibleMoves);
    }
}
