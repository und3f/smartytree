package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static List<Direction> initDirections(Context context) {
        Direction forwardDirection = Direction.up;
        Direction[] forwardDirections = context.getNeck().directionTo(context.getHead());
        if (forwardDirections.length > 0)
            forwardDirection = forwardDirections[0];

        return new ArrayList<>(Arrays.asList(
                forwardDirection,
                forwardDirection.rotateClockwise(),
                forwardDirection.rotateCounterclockwise()));
    }

    public static Direction moveForward(Context context, List<Direction> possibleMoves) {
        if (possibleMoves.size() == 0)
            return context.getNeck().directionTo(context.getHead())[0];

        return possibleMoves.get(0);
    }

    public static Direction moveTowards(Context context, List<Direction> possibleMoves, Point target) {
        if (possibleMoves.size() == 1)
            return moveForward(context, possibleMoves);

        Set<Direction> possibleMovesToTarget = Arrays.asList(context.getHead().directionTo(target)).stream()
                .distinct()
                .filter(possibleMoves::contains)
                .collect(Collectors.toSet());

        if (possibleMovesToTarget.size() > 0) {
            possibleMoves = new ArrayList<>(possibleMovesToTarget);
        }

        return moveForward(context, possibleMoves);
    }

    public static Direction randomMove(List<Direction> possibleMoves) {
        final int choice = new Random().nextInt(possibleMoves.size());
        return possibleMoves.get(choice);
    }

}