package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.context.Context;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoronoiDiagramTest extends BaseUnitTestHelper {
    @Test
    void basicTest() throws IOException {
        Context ctx = readContext("sample-state");
        var voronoi = new VoronoiDiagram(ctx);

        assertTrue(
                voronoi.getSnakeArea(ctx.state().snakes().stream()
                        .filter(snake -> !(snake.equals(ctx.state().me())))
                        .findFirst().get()).size()
                > voronoi.getSnakeArea(ctx.state().me()).size()
        );
    }
}
