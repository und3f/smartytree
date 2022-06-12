package name.zasenko.smarty.snake.strategy.filter;


import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.graph.CC;
import name.zasenko.smarty.snake.graph.DirectedEdge;

import java.util.*;

public class AvoidClosedSpaces implements StrategyFilter {

    @Override
    public void filterMoves(Context ctx, List<Direction> possibleMoves) {
        final var head = ctx.getMe().getHead();
        final var body = ctx.getMe().getBody();
        final var tail = body.get(body.size() - 1);
        final var board = ctx.getBoard();
        final var boardGraph = ctx.getBoardGraph();

        if (possibleMoves.size() <= 0) {
            return;
        }

        CC cc = new CC(boardGraph);
        Integer[] areaSizes = new Integer[possibleMoves.size()];

        Set<Integer> expandingClusters = new TreeSet<Integer>();
        expandingClusters.add(cc.id(board.valueOfPoint(tail)));
        for (DirectedEdge edge : boardGraph.pointsAround(tail, 1)) {
            expandingClusters.add(cc.id(edge.getDestination()));
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            int moveDst = board.valueOfPoint(head.move(possibleMoves.get(i)));
            areaSizes[i] = cc.componentSize(moveDst);
            if (expandingClusters.contains(cc.id(moveDst)))
                areaSizes[i] = Integer.MAX_VALUE;
        }

        Integer maxArea = Collections.max(Arrays.asList(areaSizes));
        for (int i = possibleMoves.size() - 1; i >= 0; i--)
            if (areaSizes[i] < maxArea)
                possibleMoves.remove(i);
    }
}
