package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.GameStateContext;

public class GraphFoodHazard extends Graph {
    public GraphFoodHazard(GameStateContext ctx) {
        super(ctx.boardContext(), ctx.hazardDamage());

        for (Point p : ctx.food()) {
            hazards.put(board.valueOfPoint(p), DEFAULT_HAZARD_WEIGHT);
        }
    }
}
