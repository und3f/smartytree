package name.zasenko.smarty.minmax;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.context.GameStateContext;
import name.zasenko.smarty.snake.entities.Snake;
import name.zasenko.smarty.snake.strategy.Utils;

import java.util.List;

public class MinMax {

    public static final int LENGTH_MULTIPLIER = 10;

    public void invoke(Context context) {

    }

    private void findSnakeMove(Context ctx, Snake snake) {
        GameStateContext gameStateContext = new GameStateContext(
                snake,
                ctx.state().snakes(),
                ctx.state().food(),
                ctx.state().hazards(),
                ctx.state().hazardDamage()
        );
        Context localContext = new Context(ctx.board(), gameStateContext, ctx.turn() );
        List<Direction> moves = Utils.initPossibleDirections(localContext, snake);
    }

    static double calculateScore(Context context, Snake me, Snake opponent) {
        if (Utils.initPossibleDirections(context, me).size() == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        if (Utils.initPossibleDirections(context, opponent).size() == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return (me.length() - opponent.length()) * LENGTH_MULTIPLIER;
    }

    static class DirectionPriority implements Comparable<DirectionPriority> {
        private final double score;

        private final Direction direction;

        DirectionPriority(Direction direction, double score) {
            this.direction = direction;
            this.score = score;
        }

        @Override
        public int compareTo(DirectionPriority directionPriority) {
            return (int) (score - directionPriority.score);
        }
    }
}
