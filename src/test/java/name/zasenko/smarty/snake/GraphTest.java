package name.zasenko.smarty.snake;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTest extends BaseUnitTestHelper {
  @Test
  void PointUtilsTests() throws JsonProcessingException, IOException {
    GameState gameState = this.readState("graph-test-state");

    final GameState.Board board = gameState.getBoard();
    Graph graph = new Graph(board);

    List<Integer> adjanced = graph.adj(gameState.getYou().getHead(), 0);
    assertEquals(0, adjanced.size());

    adjanced = graph.adj(gameState.getYou().getHead().move(Direction.up), 1);
    assertEquals(2, adjanced.size());
    int v44 = board.valueOfPoint(new Point(4, 4));
    int v35 = board.valueOfPoint(new Point(3, 5));
    int v14 = board.valueOfPoint(new Point(1, 4));
    assertTrue(adjanced.indexOf(v44) != -1);
    assertTrue(adjanced.indexOf(v35) != -1);

    Graph.CC cc = graph.new CC();
    assertTrue(cc.connected(v44, v35));

    assertTrue(!cc.connected(v44, v14));
    assertEquals(4, cc.componentSize(v35));
    assertEquals(5, cc.componentSize(v14));
  }

  @Test
  void PointTTLTest() throws JsonProcessingException, IOException {
    GameState gameState = this.readState("graph-test2-state");

    final GameState.Board board = gameState.getBoard();
    Graph graph = new Graph(board);

    final Point head = gameState.getYou().getHead();

    final int headRight = board.valueOfPoint(head.move(Direction.right));
    final int headLeft = board.valueOfPoint(head.move(Direction.left));

    Graph.CC cc = graph.new CC();

    assertTrue(!cc.connected(headRight, headLeft));
    assertEquals(3, cc.componentSize(headRight));
    assertEquals(6, cc.componentSize(headLeft));

    List<Point> body = gameState.getYou().getBody();
    assertTrue(cc.connected(headRight, board.valueOfPoint(body.get(body.size()-1))));
  }

}
