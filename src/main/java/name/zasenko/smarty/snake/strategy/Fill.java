package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.graph.DirectedEdge;
import name.zasenko.smarty.snake.graph.LSP;

import java.util.List;

public class Fill implements Strategy {

    public Direction findMove(Context ctx) {
        final var me = ctx.state().me();

        if (me.length() == 0)
            return Direction.down;

        final var possibleMoves = Utils.initPossibleDirections(ctx, me);

        List<DirectedEdge> path = new LSP(ctx.boardGraph(), ctx.state().me().head()).findLongestPath();
        if (path != null) {
            return Utils.moveThruPath(ctx, possibleMoves, path);
        }

        return Utils.moveForward(ctx, possibleMoves);
    }

}
