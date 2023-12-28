package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorderedBoardContextTest {

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
    }
}