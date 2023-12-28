package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FillTest extends BaseUnitTestHelper {

    @Disabled
    @Test
    void testFill() throws IOException {
        GameState gameState = readState("fill/fill-test");

        Assertions.assertEquals(Direction.right, new Context(gameState).findMove(new Fill()));
    }

}
