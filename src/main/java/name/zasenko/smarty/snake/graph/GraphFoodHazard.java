package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;

public class GraphFoodHazard extends Graph {
    public GraphFoodHazard(GameState.Board board) {
        super(board);
        for (Point p : board.getFood()) {
            hazards.put(board.valueOfPoint(p), HAZARD_WEIGHT);
        }
    }
}
