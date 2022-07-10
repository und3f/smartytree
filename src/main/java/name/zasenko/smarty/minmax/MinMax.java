package name.zasenko.smarty.minmax;

import lombok.Getter;
import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.graph.Graph;
import name.zasenko.smarty.snake.strategy.Utils;

import java.util.List;

public class MinMax {

    public static final int LENGTH_MULTIPLIER = 10;

    public void invoke(Context context) {

    }

    private void findSnakeMove(Context context, GameState.Snake snake) {
        Context localContext = new Context(context.getBoard(), snake, 0, Graph.HAZARD_WEIGHT);
        List<Direction> moves = Utils.initPossibleDirections(localContext, snake);
    }

    static double calculateScore(Context context, GameState.Snake me, GameState.Snake opponent) {
        if (Utils.initPossibleDirections(context, me).size() == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        if (Utils.initPossibleDirections(context, opponent).size() == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return (me.getLength() - opponent.getLength()) * LENGTH_MULTIPLIER;
    }

    static class DirectionPriority implements Comparable<DirectionPriority> {
        private final double score;

        @Getter
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
