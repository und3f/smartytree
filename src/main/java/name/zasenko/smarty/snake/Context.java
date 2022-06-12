package name.zasenko.smarty.snake;

import lombok.Getter;
import name.zasenko.smarty.snake.graph.Graph;
import name.zasenko.smarty.snake.strategy.Strategy;

public class Context {
    @Getter
    private final GameState.Board board;

    @Getter
    private final GameState.Snake me;

    @Getter
    private final Graph boardGraph;

    @Getter
    private final int turn;

    public Context(GameState gameState) {
        this(gameState.getBoard(),
                gameState.getYou(),
                gameState.getTurn(),
                gameState.getGame().getRuleset().getSettings().getHazardDamagePerTurn()
        );
    }

    public Context(GameState.Board board, GameState.Snake me, int turn, double hazardDamage) {
        this.board = board;
        this.me = me;
        this.turn = turn;

        this.boardGraph = new Graph(board, hazardDamage);
    }

    public Direction findMove(Strategy strategy) {
        return strategy.findMove(this);
    }
}
