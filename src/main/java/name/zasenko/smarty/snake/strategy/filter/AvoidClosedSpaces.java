package name.zasenko.smarty.snake.strategy.filter;


import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.entities.Snake;
import name.zasenko.smarty.snake.graph.CC;
import name.zasenko.smarty.snake.graph.DirectedEdge;

import java.util.*;

public class AvoidClosedSpaces implements StrategyFilter {

    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        if (possibleMoves.size() <= 1) {
            return;
        }

        final var head = ctx.state().me().head();
        final var board = ctx.board();
        final var boardGraph = ctx.boardGraph();

        CC cc = new CC(boardGraph, ctx.state().me().health());
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
        Set<Integer> expandingClusters = new TreeSet<>();
        for (Snake snake : ctx.state().snakes()) {
            Point tail = snake.tail();
            expandingClusters.add(cc.id(ctx.board().valueOfPoint(tail)));
            for (DirectedEdge edge : ctx.boardGraph().pointsAround(tail, 1)) {
                expandingClusters.add(cc.id(edge.getDestination()));
            }
        }
        return expandingClusters;
    }
}
