package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.Snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AvoidObstacles implements StrategyFilter {
    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        final var body = ctx.me().body();
        final var gameCtx = ctx.gameStateContext();
        final var board = gameCtx.boardContext();
        final var avoider = new Avoider(ctx, possibleMoves);
        final var me = ctx.me();

        ArrayList<Point> possibleObstacles = new ArrayList<>();
        ArrayList<Point> obstacles = new ArrayList<>(body.subList(2, body.size() - 1));

        if (me.health() == 100)
            obstacles.add(body.get(body.size() - 1));

        for (Snake snake : gameCtx.snakes()) {
            if (snake.equals(me))
                continue;

            if (snake.length() >= me.length()) {
                Point enemyHead = snake.body().get(0);
                obstacles.add(enemyHead);

                Direction[] directions = snake.body().get(1).directionTo(enemyHead);
                Direction enemyForwardDirection = Direction.up;
                if (directions.length > 0)
                    enemyForwardDirection = directions[0];

                for (Direction direction : Arrays.asList(
                        enemyForwardDirection,
                        enemyForwardDirection.rotateClockwise(),
                        enemyForwardDirection.rotateCounterclockwise())) {

                    Point possiblePosition = board.movePoint(enemyHead, direction);
                    if (possiblePosition != null)
                        possibleObstacles.add(possiblePosition);
                }
            }

            obstacles.addAll(snake.body().subList(1, snake.body().size() - 1));
        }

        if (gameCtx.hazardDamage() >= ctx.me().health()) {
            obstacles.addAll(gameCtx.hazards());
        }


        for (Point point : obstacles) {
            avoider.avoidObstacle(point);
        }

        for (Point possibleObstacle : possibleObstacles) {
            if (possibleMoves.size() <= 1)
                break;
            avoider.avoidObstacle(possibleObstacle);
        }
    }

    public static class Avoider {
        final Point head;
        final List<Direction> possibleMoves;

        public Avoider(Context ctx, List<Direction> possibleMoves) {
            this.head = ctx.me().head();
            this.possibleMoves = possibleMoves;
        }

        public void avoidObstacle(Point obstacle) {
            if (head.manhattanTo(obstacle) == 1)
                possibleMoves.remove(head.directionTo(obstacle)[0]);
        }
    }
}
