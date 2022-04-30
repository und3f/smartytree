package name.zasenko.smarty.snake;

import lombok.Getter;
import name.zasenko.smarty.snake.strategy.Strategy;

import java.util.List;

public class Context {
  @Getter
  private final GameState gameState;

  @Getter
  private final GameState.Board board;

  @Getter
  private final Point head, neck;

  @Getter
  private final List<Point> body;

  @Getter
  private final Graph boardGraph;

  public Context(GameState gameState) {
    this.gameState = gameState;
    this.board = gameState.getBoard();

    head = gameState.getYou().getHead();
    body = gameState.getYou().getBody();
    neck = body.get(1);

    boardGraph = new Graph(board);
  }

  public Direction move(Strategy strategy) {
    return strategy.findMove(this);
  }

  public GameState getGameState() {
    return gameState;
  }
}
