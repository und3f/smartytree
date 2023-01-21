package name.zasenko.smarty.snake;

import com.fasterxml.jackson.core.JsonProcessingException;
import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.strategy.Cycle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CycleTest extends BaseUnitTestHelper {

  @Test
  void MoveDecisionAvoidSelf() throws IOException {
    GameState gameState = readState("avoid-tail-test");

    assertNotEquals(Direction.left, new Context(gameState).findMove(new Cycle()));
  }

  @Test
  void MoveDecisionFollowTail() throws IOException {
    GameState gameState = readState("cycle/follow-tail-test");

    assertEquals(Direction.left, new Context(gameState).findMove(new Cycle()));
  }

  @Test
  void MoveDecisionFollowTail2() throws IOException {
    GameState gameState = readState("cycle/follow-tail-empty-test");

    assertEquals(Direction.down, new Context(gameState).findMove(new Cycle()));
  }

  @Test
  void TestLastChanceToEat() throws IOException {
    GameState gameState = readState("cycle/last-chance-eat-test");

    assertEquals(Direction.up, new Context(gameState).findMove(new Cycle()));
  }

  @Disabled("Not implemented")
  @Test
  void eatBeforeStarving() throws IOException {
    GameState gameState = readState("cycle/last-chance-eat-test2");

    assertEquals(Direction.right, new Context(gameState).findMove(new Cycle()));
  }


}
