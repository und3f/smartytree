package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FillTest extends BaseUnitTestHelper {
    FillTest() {
        strategy = StrategyFactory.StrategyFill;
    }

    @Disabled
    @Test
    void testFill() throws IOException {
        Assertions.assertEquals(Direction.right, runStrategy("fill/fill-test"));
    }

    @Test
    void testFindExitPoint() throws IOException {
        Assertions.assertEquals(Direction.right, runStrategy("fill/closed-space-exit"));
    }

}
