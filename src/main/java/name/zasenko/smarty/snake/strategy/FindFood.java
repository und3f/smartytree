package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.strategy.filter.AvoidBorders;
import name.zasenko.smarty.snake.strategy.filter.AvoidClosedSpaces;
import name.zasenko.smarty.snake.strategy.filter.AvoidObstacles;

import java.util.List;
import java.util.ListIterator;

public class FindFood implements Strategy {
    @Override
    public Direction findMove(Context ctx) {
        final var board = ctx.getBoard();
        final var head = ctx.getHead();
        final var possibleMoves = Utils.initDirections(ctx);

        new AvoidBorders().filterMoves(ctx, possibleMoves);
        new AvoidObstacles().filterMoves(ctx, possibleMoves);
        new AvoidClosedSpaces().filterMoves(ctx, possibleMoves);

        List<Point> food = board.getFood();
        if (possibleMoves.size() == 1 || food.size() < 1)
            return Utils.moveForward(ctx, possibleMoves);

        int[] foodDistance = new int[food.size()];
        for (ListIterator<Point> it = food.listIterator(); it.hasNext();)
            foodDistance[it.nextIndex()] = head.manhattanTo(it.next());

        int closestFoodIndex = 0;
        for (int i = closestFoodIndex + 1; i < foodDistance.length; i++)
            if (foodDistance[closestFoodIndex] > foodDistance[i])
                closestFoodIndex = i;

        return Utils.moveTowards(ctx, possibleMoves, food.get(closestFoodIndex));
    }
}
