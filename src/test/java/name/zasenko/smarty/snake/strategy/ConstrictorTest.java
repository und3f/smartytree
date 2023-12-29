package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConstrictorTest extends BaseUnitTestHelper {

    @BeforeAll
    static void initStrategy() {
        strategy = StrategyFactory.StrategyConstrictor;
    }

    @Disabled
    @Test
    void testClosedSpace() throws IOException {
        Assertions.assertEquals(
                Direction.down,
                runStrategy("constrictor/avoid-closed-spaces")
        );
    }

    @Test
    void constrictorMaze() throws IOException {
        GameState gameState = readState("constrictor/constrictor-maze");

        assertEquals(
                Direction.down,
                runStrategy("constrictor/constrictor-maze")
        );
    }

    @Test
    void isSnakeNotAlone() throws IOException {
        GameState gameState = readState("constrictor/constrictor-maze");

        assertFalse(new Constrictor().isSnakeAlone(new Context(gameState)));
    }
}
