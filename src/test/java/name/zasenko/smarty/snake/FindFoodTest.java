package name.zasenko.smarty.snake;

import com.fasterxml.jackson.core.JsonProcessingException;
import name.zasenko.smarty.snake.strategy.StrategyFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindFoodTest extends BaseUnitTestHelper {

    @BeforeAll
    static void initStrategy() {
        strategy = StrategyFactory.StrategyFindFood;
    }

    @Test
    void MoveDecisionToTailTest() throws JsonProcessingException, IOException {
        assertEquals(Direction.up, this.RunStrategy("tail-test-state"));
    }

    @Test
    void MoveDecisionToTail2Test() throws JsonProcessingException, IOException {
        assertEquals(Direction.left, this.RunStrategy("tail-test2-state"));
    }

    @Disabled
    @Test
    void EatNearTail() throws JsonProcessingException, IOException {
        assertEquals(Direction.down, this.RunStrategy("findfood-cycle-test"));
    }

    @Test
    void AvoidClosedSpace() throws JsonProcessingException, IOException {
        assertEquals(Direction.left, this.RunStrategy("findfood-closed-test"));
    }

}
