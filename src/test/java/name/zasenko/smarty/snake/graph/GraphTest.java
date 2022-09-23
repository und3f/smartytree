package name.zasenko.smarty.snake.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.Point;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTest extends BaseUnitTestHelper {

    public static final String GRAPH_TEST_2_STATE = "graph-test2-state";

    @Test
    void TestAdjacentSimple() throws IOException {
        GameState gameState = this.readState("graph-test-state");

        final GameState.Board board = gameState.getBoard();
        Graph graph = new Graph(board);

        List<DirectedEdge> adjacent = graph.adj(board.valueOfPoint(new Point(6, 0)), 0);
        List<Integer> expected = Arrays.asList(board.valueOfPoint(new Point(6, 1)));
        assertEquals(expected, adjacent.stream().map(DirectedEdge::getDestination).collect(Collectors.toList()));
    }

    @Test
    void TestAdjacentFromSnakeHead() throws IOException {
        GameState gameState = this.readState("graph-test-state");

        final GameState.Board board = gameState.getBoard();
        Graph graph = new Graph(board);

        List<DirectedEdge> adjacent = graph.adj(board.valueOfPoint(new Point(0, 5)), 0);

        List<Integer> expected = Arrays.asList(
                board.valueOfPoint(new Point(0, 6)),
                board.valueOfPoint(new Point(1, 5))
        );
        assertEquals(expected, adjacent.stream().map(DirectedEdge::getDestination).sorted().collect(Collectors.toList()));
    }

    @Test
    void PointUtilsTests() throws IOException {
        GameState gameState = this.readState("graph-test-state");

        final GameState.Board board = gameState.getBoard();
        Graph graph = new Graph(board);

        List<DirectedEdge> adjanced = graph.adj(gameState.getYou().getHead(), 0);
        assertEquals(2, adjanced.size());

        adjanced = graph.adj(board.movePoint(gameState.getYou().getHead(), Direction.up), 1);
        assertEquals(2, adjanced.size());

        int v44 = board.valueOfPoint(new Point(4, 4));
        int v35 = board.valueOfPoint(new Point(3, 5));
        int v14 = board.valueOfPoint(new Point(1, 4));

        assertTrue(adjanced.stream().anyMatch(edge -> edge.getDestination() == v44));
        assertTrue(adjanced.stream().anyMatch(edge -> edge.getDestination() == v35));

        CC cc = new CC(graph, Integer.MAX_VALUE);
        assertTrue(cc.connected(v44, v35));

        assertTrue(!cc.connected(v44, v14));
        assertEquals(4, cc.componentSize(v35));
        assertEquals(5, cc.componentSize(v14));
    }

    @Test
    void PointTTLTest() throws IOException {
        GameState gameState = this.readState(GRAPH_TEST_2_STATE);

        final GameState.Board board = gameState.getBoard();
        Graph graph = new Graph(board);

        final Point head = gameState.getYou().getHead();

        final int headRight = board.valueOfPoint(board.movePoint(head, Direction.right));
        final int headLeft = board.valueOfPoint(board.movePoint(head, Direction.left));

        CC cc = new CC(graph, Integer.MAX_VALUE);

        assertTrue(!cc.connected(headRight, headLeft));
        assertEquals(3, cc.componentSize(headRight));
        assertEquals(6, cc.componentSize(headLeft));

        List<Point> body = gameState.getYou().getBody();
        assertTrue(cc.connected(headRight, board.valueOfPoint(body.get(body.size() - 1))));
    }

    @Test
    void toStringTest() throws IOException {
        GameState gameState = this.readState(GRAPH_TEST_2_STATE);

        final GameState.Board board = gameState.getBoard();
        Graph graph = new Graph(board);
        final String str = graph.toString();

        assertEquals(this.getResourceContent("output/graph-test2-output.txt"), str);
    }

}
