package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.BoardContext;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.GameState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTest extends BaseUnitTestHelper {

    @Test
    void TestAdjacentSimple() throws IOException {
        Context context = this.readContext("graph/state1");
        final BoardContext board = context.gameStateContext().boardContext();
        Graph graph = new Graph(context.gameStateContext());

        List<DirectedEdge> adjacent = graph.adj(board.valueOfPoint(new Point(6, 0)), 0);
        List<Integer> expected = Arrays.asList(board.valueOfPoint(new Point(6, 1)));
        assertEquals(expected, adjacent.stream().map(DirectedEdge::getDestination).collect(Collectors.toList()));
    }

    @Test
    void TestAdjacentFromSnakeHead() throws IOException {
        var context = this.readContext("graph/state1");

        final BoardContext board = context.gameStateContext().boardContext();
        Graph graph = new Graph(context.gameStateContext());

        List<DirectedEdge> adjacent = graph.adj(board.valueOfPoint(new Point(0, 5)), 0);

        List<Integer> expected = Arrays.asList(
                board.valueOfPoint(new Point(0, 6)),
                board.valueOfPoint(new Point(1, 5))
        );
        assertEquals(expected, adjacent.stream().map(DirectedEdge::getDestination).sorted().collect(Collectors.toList()));
    }

    @Test
    void PointUtilsTests() throws IOException {
        final Context ctx = this.readContext("graph/state1");
        final BoardContext board = ctx.gameStateContext().boardContext();
        Graph graph = new Graph(ctx.gameStateContext());

        List<DirectedEdge> adjanced = graph.adj(ctx.me().head(), 0);
        assertEquals(2, adjanced.size());

        adjanced = graph.adj(board.movePoint(ctx.me().head(), Direction.up), 1);
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
        Context ctx = this.readContext("graph/state2");

        final BoardContext board = ctx.gameStateContext().boardContext();
        Graph graph = new Graph(ctx.gameStateContext());

        final Point head = ctx.me().head();

        final int headRight = board.valueOfPoint(board.movePoint(head, Direction.right));
        final int headLeft = board.valueOfPoint(board.movePoint(head, Direction.left));

        CC cc = new CC(graph, Integer.MAX_VALUE);

        assertTrue(!cc.connected(headRight, headLeft));
        assertEquals(3, cc.componentSize(headRight));
        assertEquals(6, cc.componentSize(headLeft));

        List<Point> body = ctx.me().body();
        assertTrue(cc.connected(headRight, board.valueOfPoint(body.get(body.size() - 1))));
    }

    @Test
    void toStringTest() throws IOException {
        GameState gameState = this.readState("graph/state2");
        final Context context = new Context(gameState);

        Graph graph = new Graph(context.gameStateContext());
        final String str = graph.toString();

        assertEquals(this.getResourceContent("output/graph-test2-output.txt"), str);
    }

    @Test
    void StackedHazardsTest() throws IOException {

        GameState gameState = this.readState("graph/snail");

        final Context ctx = new Context(gameState);
        Graph graph = new Graph(ctx.gameStateContext());

        final BoardContext board = ctx.gameStateContext().boardContext();
        List<DirectedEdge> adjacent = graph.adj(board.valueOfPoint(new Point(3, 2)), 0);
        int rightPoint = board.valueOfPoint(new Point(3, 3));
        DirectedEdge right = adjacent.stream().filter(edge -> edge.getDestination() == rightPoint).findFirst().get();
        assertEquals(ctx.gameStateContext().hazardDamage() * 6, right.getWeight(), 0.001f);
    }
}
