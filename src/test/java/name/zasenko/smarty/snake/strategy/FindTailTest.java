package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import org.junit.jupiter.api.Assertions;
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
    Assertions.assertEquals(Direction.left, this.runStrategy("avoid-tail-test2"));
  }

}
