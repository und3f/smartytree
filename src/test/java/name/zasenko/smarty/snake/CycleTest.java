package name.zasenko.smarty.snake;

import com.fasterxml.jackson.core.JsonProcessingException;
import name.zasenko.smarty.snake.strategy.Cycle;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CycleTest extends BaseUnitTestHelper {

  @Test
  void MoveDecisionAvoidSelf() throws JsonProcessingException, IOException {
    GameState gameState = readState("avoid-tail-test");

    assertNotEquals(Direction.left, new Context(gameState).move(new Cycle()));
  }

}
