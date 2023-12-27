package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.entities.Snake;
import name.zasenko.smarty.snake.graph.Graph;
import name.zasenko.smarty.snake.strategy.Strategy;

public class Context {
    private final GameStateContext gameStateContext;

    private final Snake me;

    private final Graph boardGraph;

    private final int turn;

    public Context(GameStateContext gameStateContext, Snake me, int turn, double hazardDamage) {
        this.gameStateContext = gameStateContext;
        this.me = me;
        this.turn = turn;

        this.boardGraph = new Graph(gameStateContext);
    }

    public Context(GameState gameState) {
        this(
                new GameStateContext(gameState),
                gameState.you(),
                gameState.turn(),
                gameState.game().ruleset().settings().hazardDamagePerTurn()
        );
    }

    public Direction findMove(Strategy strategy) {
        return strategy.findMove(this);
    }

    public GameStateContext gameStateContext() {
        return gameStateContext;
    }

    public Snake me() {
        return me;
    }

    public Graph boardGraph() {
        return boardGraph;
    }

    public int turn() {
        return turn;
    }
}
