package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.strategy.Constrictor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstrictorTest extends BaseUnitTestHelper {

    @Disabled
    @Test
    void testClosedSpace() throws IOException {
        GameState gameState = readState("constrictor/avoid-closed-spaces");

        assertEquals(Direction.up, new Context(gameState).findMove(new Constrictor()));
    }

    @Test
    void constrictorMaze() throws IOException {
        GameState gameState = readState("constrictor/constrictor-maze");

        assertEquals(Direction.down, new Context(gameState).findMove(new Constrictor()));
    }
}
