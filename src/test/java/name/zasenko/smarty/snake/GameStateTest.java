package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.context.BoardContext;
import name.zasenko.smarty.snake.context.BorderedBoardContext;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.context.WrappedBoardContext;
import name.zasenko.smarty.snake.entities.GameState;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest extends BaseUnitTestHelper {
    @Test
    void pointUtilsTests() {
        final int width = 10, height = 12;
        BoardContext board = new BorderedBoardContext(height, width);

        assertTrue(board.isValid(new Point(0, 0)));
        assertFalse(board.isValid(new Point(0, -1)));
        assertFalse(board.isValid(new Point(0, width)));
        assertTrue(board.isValid(new Point(0, width - 1)));
        assertFalse(board.isValid(new Point(height, 0)));
        assertTrue(board.isValid(new Point(height - 1, 0)));

        assertEquals(2 * width + 3, board.valueOfPoint(new Point(2, 3)));
        assertEquals(board.fromValue(board.valueOfPoint(new Point(2, 3))), new Point(2, 3));
    }

  /*
  TODO
  @Test
  void cloneTests() throws IOException {
    GameState sampleState = this.readState("sample-state");

    Context ctx = new Context(sampleState);
    BoardContext cloneBoard = new BoardContext(sampleBoard);

    assertNotSame(sampleBoard, cloneBoard);
    assertEquals(sampleBoard.getWidth(), cloneBoard.getWidth());
    assertEquals(sampleBoard.getHeight(), cloneBoard.getHeight());
    assertEquals(sampleBoard.getFood(), cloneBoard.getFood());
    assertEquals(sampleBoard.getHazards(), cloneBoard.getHazards());
    assertEquals(sampleBoard.getSnakes(), cloneBoard.getSnakes());
  }
 */

    @Test
    void MovePointTests() {
        BoardContext board = new WrappedBoardContext(7, 7);

        Point result = board.movePoint(new Point(2, 3), Direction.up);
        assertEquals(new Point(3, 3), result);

        result = board.movePoint(new Point(2, 3), new Point(-1, 1));
        assertEquals(new Point(1, 4), result);
    }

    @Test
    void InvalidMovePointTest() {
        final int width = 10, height = 12;
        BoardContext board = new BorderedBoardContext(height, width);

        Point result = board.movePoint(new Point(0, width - 1), Direction.down);
        assertNull(result);

        result = board.movePoint(new Point(0, width - 1), new Point(1, 1));
        assertNull(result);
    }

    @Test
    void isPointValidTest() {
        final int width = 10, height = 12;
        BoardContext board = new BorderedBoardContext(height, width);

        assertTrue(board.isValid(new Point(height - 1, width - 1)));
        assertTrue(board.isValid(new Point(0, 0)));

        assertFalse(board.isValid(new Point(height, 6)));
        assertFalse(board.isValid(new Point(1, width)));
        assertFalse(board.isValid(null));
    }

    @Test
    void wrapMovePointTest() {
        final int width = 19, height = 21;
        BoardContext board = new WrappedBoardContext(height, width);

        Point result = board.movePoint(new Point(height - 1, width - 1), Direction.up);
        assertEquals(new Point(0, width - 1), result);

        result = board.movePoint(new Point(height - 1, width - 1), Direction.right);
        assertEquals(new Point(height - 1, 0), result);

        result = board.movePoint(new Point(0, 0), Direction.left);
        assertEquals(new Point(0, width - 1), result);

        result = board.movePoint(new Point(0, 0), Direction.down);
        assertEquals(new Point(height - 1, 0), result);
    }

    @Test
    void wrappedSnakeDirectionTest() throws IOException {
        GameState gameState = readState("maze/unexpected-move");
        Context ctx = new Context(gameState);

        assertEquals(Direction.right, ctx.state().me().headDirection());
    }
}
