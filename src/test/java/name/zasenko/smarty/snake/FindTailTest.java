package name.zasenko.smarty.snake;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.strategy.StrategyFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindTailTest extends BaseUnitTestHelper {

  @BeforeAll
  static void initStrategy() {
    strategy = StrategyFactory.StrategyFindTail;
  }

  @Test
  void MoveDecisionToTail3Test() throws IOException {
    assertEquals(Direction.left, this.RunStrategy("avoid-tail-test2"));
  }

}
