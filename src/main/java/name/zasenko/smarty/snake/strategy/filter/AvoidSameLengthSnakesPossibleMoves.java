package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AvoidSameLengthSnakesPossibleMoves implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        ArrayList<Point> obstacles = new ArrayList<>();

        final GameState.Snake me = ctx.getMe();

        for (Iterator<GameState.Snake> it = ctx.getBoard().getSnakes().iterator(); it.hasNext();) {
            GameState.Snake snake = it.next();

            if (snake.equals(me))
                continue;

            if (snake.getLength() == me.getLength()) {
                Point enemyHead = snake.getBody().get(0);
                obstacles.add(enemyHead);

                Direction[] directions = snake.getBody().get(1).directionTo(enemyHead);
                if (directions.length == 0) {
                    continue;
                }

                Direction enemyForwardDirection = directions[0];

                for (Direction direction : Arrays.asList(
                        enemyForwardDirection,
                        enemyForwardDirection.rotateClockwise(),
                        enemyForwardDirection.rotateCounterclockwise()))
                    obstacles.add(enemyHead.move(direction));
            }
        }
    }
}
