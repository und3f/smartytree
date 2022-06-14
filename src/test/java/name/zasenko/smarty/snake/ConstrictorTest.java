package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.strategy.Constrictor;
import name.zasenko.smarty.snake.strategy.Fill;
import name.zasenko.smarty.snake.strategy.StrategyFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstrictorTest extends BaseUnitTestHelper {

    @BeforeAll
    static void initStrategy() {
        strategy = StrategyFactory.StrategyConstrictor;
    }

    @Test
    void testClosedSpace() throws IOException {
        assertEquals(Direction.up, this.RunStrategy("constrictor/avoid-closed-spaces"));
    }

    @Test
    void testHeadCollision() throws IOException {
        assertEquals(Direction.down, this.RunStrategy("constrictor/avoid-snakes"));
    }

}
