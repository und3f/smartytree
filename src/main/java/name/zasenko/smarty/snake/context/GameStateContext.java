package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.entities.Snake;

import java.util.List;

public record GameStateContext(
        BoardContext boardContext,
        List<Snake> snakes,
        List<Point> food,
        List<Point> hazards,
        int hazardDamage
) {

    public GameStateContext(GameState gameState) {
        this(
                BoardContextFactory.createBoard(gameState),
                gameState.board().snakes(),
                gameState.board().food(),
                gameState.board().hazards(),
                gameState.game().ruleset().settings().hazardDamagePerTurn()
        );
    }

}
