package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.strategy.Constrictor;
import name.zasenko.smarty.snake.strategy.Fill;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstrictorTest extends BaseUnitTestHelper {
    @Test
    void testClosedSpace() throws IOException {
        GameState gameState = readState("constrictor/avoid-closed-spaces");

        assertEquals(Direction.up, new Context(gameState).findMove(new Constrictor()));
    }
}
