package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.entities.GameState;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WrappedBoardContextTest extends BaseUnitTestHelper {

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