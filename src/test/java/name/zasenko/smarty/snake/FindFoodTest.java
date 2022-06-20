package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.strategy.StrategyFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindFoodTest extends BaseUnitTestHelper {

    @BeforeAll
    static void initStrategy() {
        strategy = StrategyFactory.StrategyFindFood;
    }

    @Test
    void moveDecisionToTailTest() throws IOException {
        assertEquals(Direction.up, this.RunStrategy("tail-test-state"));
    }

    @Test
    void moveDecisionToTail2Test() throws IOException {
        assertEquals(Direction.left, this.RunStrategy("tail-test2-state"));
    }

    @Test
    void avoidClosedSpace() throws IOException {
        assertEquals(Direction.left, this.RunStrategy("findfood-closed-test"));
    }

    @Test
    void preferOthersTailOverClosedSpace() throws IOException {
        assertEquals(Direction.right, this.RunStrategy("findfood-others-tail"));
    }

    @Test
    void testMaze() throws IOException {
        assertEquals(Direction.up, this.RunStrategy("maze/basic-test"));
    }

    @Test
    void testMaze2() throws IOException {
        assertEquals(Direction.right, this.RunStrategy("maze/unexpected-move"));
    }

}