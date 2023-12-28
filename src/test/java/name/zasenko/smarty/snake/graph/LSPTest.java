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
        var context = this.readContext("findfood/closed");
        var graph = Graph.createGenericGameGraph(context);
        LSP lsp = new LSP(graph, context.state().me().head());
        final List<DirectedEdge> path = lsp.findLongestPath();

        assertNotNull(path);
        assertEquals(15, path.size());
    }

    @Test
    void testLongestSimplePath2() throws IOException {
        var context = this.readContext("fill/simple-fill");
        var graph = Graph.createGenericGameGraph(context);
        LSP lsp = new LSP(graph, context.state().me().head());
        final List<DirectedEdge> path = lsp.findLongestPath();

        assertNotNull(path);
        assertEquals(24, path.size());
    }

}
