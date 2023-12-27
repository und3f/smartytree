package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.strategy.Fill;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FillTest extends BaseUnitTestHelper {

    @Disabled
    @Test
    void testFill() throws IOException {
        GameState gameState = readState("fill/fill-test");

        assertEquals(Direction.right, new Context(gameState).findMove(new Fill()));
    }

}
