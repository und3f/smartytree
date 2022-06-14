package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest extends BaseUnitTestHelper {
  private GameState.Board newBoard(int height, int width) {
    GameState.Board board = new GameState.Board();
    board.setWidth(width);
    board.setHeight(height);

    return board;
  }

  @Test
  void pointUtilsTests() {
    final int width = 10, height = 12;
    GameState.Board board = newBoard(height, width);

    assertTrue(board.isValid(new Point(0, 0)));
    assertFalse(board.isValid(new Point(0, -1)));
    assertFalse(board.isValid(new Point(0, width)));
    assertTrue(board.isValid(new Point(0, width - 1)));
    assertFalse(board.isValid(new Point(height, 0)));
    assertTrue(board.isValid(new Point(height - 1, 0)));

    assertEquals(2 * width + 3, board.valueOfPoint(new Point(2, 3)));
    assertEquals(board.fromValue(board.valueOfPoint(new Point(2, 3))), new Point(2, 3));
  }

  @Test
  void cloneTests() throws IOException {
    GameState sampleState = this.readState("sample-state");

    GameState.Board sampleBoard = sampleState.getBoard();
    GameState.Board cloneBoard = new GameState.Board(sampleBoard);

    assertNotSame(sampleBoard, cloneBoard);
    assertEquals(sampleBoard.getWidth(), cloneBoard.getWidth());
    assertEquals(sampleBoard.getHeight(), cloneBoard.getHeight());
    assertEquals(sampleBoard.getFood(), cloneBoard.getFood());
    assertEquals(sampleBoard.getHazards(), cloneBoard.getHazards());
    assertEquals(sampleBoard.getSnakes(), cloneBoard.getSnakes());
  }

  @Disabled
  @Test
  void cloneMoveStrategyTest() {
    GameState.Board board = newBoard(7, 7);
    board.setMoveStrategy(board.new WrapMoveStrategy());
    GameState.Board cloneBoard = new GameState.Board(board);

    assertEquals(board.getMoveStrategy().getClass(), cloneBoard.getMoveStrategy().getClass());
    assertNotSame(board.getMoveStrategy(), cloneBoard.getMoveStrategy());
  }

  @Test
  void MovePointTests() {
    GameState.Board board = newBoard(7, 7);

    Point result = board.movePoint(new Point(2, 3), Direction.up);
    assertEquals(new Point(3, 3), result);

    result = board.movePoint(new Point(2, 3), new Point(-1, 1));
    assertEquals(new Point(1, 4), result);
  }

  @Test
  void InvalidMovePointTest() {
    final int width = 10, height = 12;
    GameState.Board board = newBoard(height, width);

    Point result = board.movePoint(new Point(0, width-1), Direction.down);
    assertNull(result);

    result = board.movePoint(new Point(0, width-1), new Point(1, 1));
    assertNull(result);
  }

  @Test
  void isPointValidTest() {
    final int width = 10, height = 12;
    GameState.Board board = newBoard(height, width);

    assertTrue(board.isValid(new Point(height-1, width-1)));
    assertTrue(board.isValid(new Point(0, 0)));

    assertFalse(board.isValid(new Point(height, 6)));
    assertFalse(board.isValid(new Point(1, width)));
    assertFalse(board.isValid(null));
  }

  @Test
  void wrapMovePointTest() {
    final int width = 19, height = 21;
    GameState.Board board = newBoard(height, width);
    board.setMoveStrategy(board.new WrapMoveStrategy());

    Point result = board.movePoint(new Point(height-1, width-1), Direction.up);
    assertEquals(new Point(0, width-1), result);

    result = board.movePoint(new Point(height-1, width-1), Direction.right);
    assertEquals(new Point(height-1, 0), result);

    result = board.movePoint(new Point(0, 0), Direction.left);
    assertEquals(new Point(0, width-1), result);

    result = board.movePoint(new Point(0, 0), Direction.down);
    assertEquals(new Point(height-1, 0), result);
  }

  @Test
  void wrappedSnakeDirectionTest() throws IOException {
    GameState gameState = readState("maze/unexpected-move");
    Context ctx = new Context(gameState);

    assertEquals(Direction.right, ctx.getMe().headDirection());
  }
}
