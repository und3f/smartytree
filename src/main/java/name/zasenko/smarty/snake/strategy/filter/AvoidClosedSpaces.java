package name.zasenko.smarty.snake.strategy.filter;


import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.graph.CC;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.graph.Graph;

import java.util.*;

public class AvoidClosedSpaces implements StrategyFilter {

    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        if (possibleMoves.size() <= 1) {
            return;
        }

        final var head = ctx.getMe().getHead();
        final var board = ctx.getBoard();
        final var boardGraph = ctx.getBoardGraph();

        CC cc = new CC(boardGraph);
        Integer[] areaSizes = new Integer[possibleMoves.size()];

        Set<Integer> expandingClusters = getExpandingClusters(ctx, cc);

        for (int i = 0; i < possibleMoves.size(); i++) {
            int moveDst = board.valueOfPoint(board.movePoint(head, possibleMoves.get(i)));
            areaSizes[i] = cc.componentSize(moveDst);
            if (expandingClusters.contains(cc.id(moveDst)))
                areaSizes[i] = Integer.MAX_VALUE;
        }

        Integer maxArea = Collections.max(Arrays.asList(areaSizes));
        for (int i = possibleMoves.size() - 1; i >= 0; i--)
            if (areaSizes[i] < maxArea)
                possibleMoves.remove(i);
    }

    protected Set<Integer> getExpandingClusters(Context ctx, CC cc) {
        Set<Integer> expandingClusters = new TreeSet<Integer>();
        Point tail = ctx.getMe().tail();
        expandingClusters.add(cc.id(ctx.getBoard().valueOfPoint(tail)));
        for (DirectedEdge edge : ctx.getBoardGraph().pointsAround(tail, 1)) {
            expandingClusters.add(cc.id(edge.getDestination()));
        }
        return expandingClusters;
    }
}
