package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.snake.Point;

public class GraphFoodHazard extends Graph {
    public GraphFoodHazard(Graph G) {
        super(G.board, G.hazardWeight);

        for (Point p : board.getFood()) {
            hazards.put(board.valueOfPoint(p), HAZARD_WEIGHT);
        }
    }
}
