package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AvoidObstacles implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        final var body = ctx.getMe().getBody();
        final var board = ctx.getBoard();
        final var avoider = new Avoider(ctx, possibleMoves);
        final var me = ctx.getMe();

        ArrayList<Point> obstacles = new ArrayList<Point>();
        ArrayList<Point> possibleObstacles = new ArrayList<Point>();
        obstacles.addAll(body.subList(2, body.size() - 1));

        if (me.getHealth() == 100)
            obstacles.add(body.get(body.size() - 1));

        for (Iterator<GameState.Snake> it = board.getSnakes().iterator(); it.hasNext();) {
            GameState.Snake snake = it.next();
            if (snake.equals(me))
                continue;

            if (snake.getLength() >= me.getLength()) {
                Point enemyHead = snake.getBody().get(0);
                obstacles.add(enemyHead);

                if (snake.getLength() > me.getLength()) {
                    Direction[] directions = snake.getBody().get(1).directionTo(enemyHead);
                    Direction enemyForwardDirection = Direction.up;
                    if (directions.length > 0)
                        enemyForwardDirection = directions[0];

                    for (Direction direction : Arrays.asList(
                            enemyForwardDirection,
                            enemyForwardDirection.rotateClockwise(),
                            enemyForwardDirection.rotateCounterclockwise()))
                        possibleObstacles.add(enemyHead.move(direction));
                }
            }

            obstacles.addAll(snake.getBody().subList(1, snake.getBody().size() - 1));
        }

        for (Iterator<Point> it = obstacles.iterator(); it.hasNext();) {
            avoider.avoidObstacle(it.next());
        }

        for (Iterator<Point> it = possibleObstacles.iterator(); it.hasNext();) {
            if (possibleMoves.size() <= 1)
                break;
            Point obstacle = it.next();
            avoider.avoidObstacle(obstacle);
        }
    }

    public static class Avoider {
        final Point head;
        final List<Direction> possibleMoves;

        public Avoider(Context ctx, List<Direction> possibleMoves) {
            this.head = ctx.getMe().getHead();
            this.possibleMoves = possibleMoves;
        }

        public void avoidObstacle(Point obstacle) {
            if (head.manhattanTo(obstacle) == 1)
            possibleMoves.remove(head.directionTo(obstacle)[0]);
        }
    }
}
