package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.graph.Graph;
import name.zasenko.smarty.snake.strategy.Strategy;

public class Context {
    private final BoardContext boardContext;
    private final GameStateContext gameStateContext;
    private final Graph boardGraph;
    private final int turn;

    public Context(BoardContext boardContext, GameStateContext gameStateContext, int turn) {
        this.boardContext = boardContext;
        this.gameStateContext = gameStateContext;
        this.turn = turn;

        this.boardGraph = Graph.createGenericGameGraph(this);
    }

    public Context(GameState gameState) {
        this(
                BoardContextFactory.createBoard(gameState),
                new GameStateContext(gameState),
                gameState.turn()
        );
    }

    public Direction findMove(Strategy strategy) {
        return strategy.findMove(this);
    }

    public GameStateContext state() {
        return gameStateContext;
    }

    public BoardContext board() {
        return boardContext;
    }

    public Graph boardGraph() {
        return boardGraph;
    }

    public int turn() {
        return turn;
    }
}
