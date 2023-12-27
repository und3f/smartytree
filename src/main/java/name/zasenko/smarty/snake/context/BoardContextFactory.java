package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.entities.Ruleset;

public class BoardContextFactory {
    public static BoardContext createBoard(GameState gameState) {
        var board = gameState.board();
        switch (gameState.game().ruleset().name()) {
            case Ruleset.WRAPPED, Ruleset.WRAPPED_CONSTRICTOR:
                return new WrappedBoardContext(board.height(), board.width());
            default:
            return new BorderedBoardContext(board.height(), board.width());
        }
    }
}
