package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TSPTest extends BaseUnitTestHelper {
        private GameState gameState;
        private Graph graph;

        @BeforeEach
        void initGraph() throws IOException {
            gameState = this.readState("findfood-closed-test");

            graph = new Graph(gameState.getBoard());
        }

        @Test
        void testSimpleShortPath() {
            TSP tsp = new TSP(graph, gameState.getYou().getHead());
            final List<DirectedEdge> path = tsp.findLongestPath();

            assertNotNull(path);
            assertEquals(15, path.size());
        }
}
