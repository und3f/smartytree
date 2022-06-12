package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.Dijkstra;
import name.zasenko.smarty.snake.strategy.filter.AvoidProblems;

import java.util.*;

public class FindFood implements Strategy {
    @Override
    public Direction findMove(Context ctx) {
        final var board = ctx.getBoard();
        final var head = ctx.getMe().getHead();

        final var possibleMoves = Utils.initPossibleDirections(ctx, ctx.getMe());
        List<Point> food = board.getFood();

        if (possibleMoves.size() == 1 || food.size() < 1)
            return Utils.moveForward(ctx, possibleMoves);

        Dijkstra dijkstra = new Dijkstra(ctx.getBoardGraph(), ctx.getMe().getHead());

        Point closestFood = findClosestFood(food, dijkstra);

        return Utils.moveTowards(ctx, possibleMoves, closestFood);
    }

    public static Point findClosestFood(List<Point> food, Dijkstra dijkstra) {
        double[] foodDistance = new double[food.size()];
        PriorityQueue<Integer> foodIndexPQ = new PriorityQueue<>(food.size(),
                Comparator.comparingDouble(i -> foodDistance[i])
        );

        for (ListIterator<Point> it = food.listIterator(); it.hasNext();) {
            final int i = it.nextIndex();
            foodDistance[i] = dijkstra.findDistance(it.next());
            foodIndexPQ.add(i);
        }

        Integer closestFoodIndex = foodIndexPQ.poll();
        Point closestFood = food.get(closestFoodIndex);
        return closestFood;
    }
}
