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
        this.board = gameState.getBoard();
        String rulesetName = gameState.getGame().getRuleset().getName();
        if (rulesetName.equals(GameState.Ruleset.WRAPPED)
            || rulesetName.equals(GameState.Ruleset.WRAPPED_CONSTRICTOR)
        ) {
            this.board.setMoveStrategy(this.board.new WrapMoveStrategy());
        }

        this.me = gameState.getYou();
        this.turn = gameState.getTurn();

        var hazardDamage = gameState.getGame().getRuleset().getSettings().getHazardDamagePerTurn();
        this.boardGraph = new Graph(board, hazardDamage);
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
