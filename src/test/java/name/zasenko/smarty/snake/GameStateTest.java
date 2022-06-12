package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest extends BaseUnitTestHelper {
  @Test
  void PointUtilsTests() {
    final int width = 10, height = 12;
    GameState.Board board = new GameState.Board();
    board.setWidth(width);
    board.setHeight(height);

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
  void CloneTests() throws IOException {
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
}
