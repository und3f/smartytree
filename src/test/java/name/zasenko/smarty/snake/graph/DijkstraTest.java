package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.GameState;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import name.zasenko.smarty.snake.Point;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class DijkstraTest extends BaseUnitTestHelper {
    private GameState gameState;
    private Graph graph;

    @BeforeEach
    void initGraph() throws IOException {
        gameState = this.readState("findfood/closed");

        graph = new Graph(gameState.getBoard());
    }

    @Test
    void testSimpleShortPath() {
        Dijkstra dijkstra = new Dijkstra(graph, gameState.getYou().getHead());
        final List<DirectedEdge> path = dijkstra.findPath(0);
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(3.0, dijkstra.findDistance(0));
    }

    @Test
    void testShortPath() {
        Dijkstra dijkstra = new Dijkstra(graph, gameState.getYou().getHead());
        Point destination = new Point(4, 6);
        final List<DirectedEdge> path = dijkstra.findPath(destination);
        assertNotNull(path);
        assertEquals(7, path.size());
        assertEquals(7.0, dijkstra.findDistance(destination));
    }

    @Test
    void testToString() throws IOException {
        Dijkstra dijkstra = new Dijkstra(graph, gameState.getYou().getHead());
        assertEquals(this.getResourceContent("output/graph-test-dijkstra-output.txt"), dijkstra.toString());
    }

}
