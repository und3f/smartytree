package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LSPTest extends BaseUnitTestHelper {

    @Test
    void testLongestSimplePath() throws IOException {
        var gameState = this.readState("findfood/closed");
        var graph = new Graph(gameState.getBoard());
        LSP lsp = new LSP(graph, gameState.getYou().getHead());
        final List<DirectedEdge> path = lsp.findLongestPath();

        assertNotNull(path);
        assertEquals(15, path.size());
    }

    @Test
    void testLongestSimplePath2() throws IOException {
        var gameState = this.readState("fill/simple-fill");
        var graph = new Graph(gameState.getBoard());
        LSP lsp = new LSP(graph, gameState.getYou().getHead());
        final List<DirectedEdge> path = lsp.findLongestPath();

        assertNotNull(path);
        assertEquals(24, path.size());
    }

}
