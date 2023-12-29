package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(Direction.up, this.runStrategy("findfood/tail-test"));
    }

    @Test
    void moveDecisionToTail2Test() throws IOException {
        assertEquals(Direction.left, this.runStrategy("findfood/tail-test2"));
    }

    @Test
    void avoidClosedSpace() throws IOException {
        assertEquals(Direction.left, this.runStrategy("findfood/closed"));
    }

    @Test
    void preferOthersTailOverClosedSpace() throws IOException {
        assertEquals(Direction.right, this.runStrategy("findfood/others-tail"));
    }

    @Test
    void testMaze() throws IOException {
        assertEquals(Direction.up, this.runStrategy("maze/basic-test"));
    }

    @Test
    void testMaze2() throws IOException {
        assertEquals(Direction.right, this.runStrategy("maze/unexpected-move"));
    }

    @Test
    void testMaze3() throws IOException {
        assertEquals(Direction.up, this.runStrategy("maze/destroy-snakes"));
    }

    @Test
    void testMaze4() throws IOException {
        assertEquals(Direction.down, runStrategy("maze/exception"));
    }

    @Test
    void testMaze5() throws IOException {
        assertEquals(Direction.up, runStrategy("maze/avoid-closed-spaces"));
    }

    @Test
    void closedSpaceFindExitPoint() throws IOException {
        assertEquals(Direction.right, runStrategy("fill/closed-space-exit"));
    }

}