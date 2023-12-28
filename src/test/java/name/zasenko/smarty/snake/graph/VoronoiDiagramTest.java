package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Point;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.Snake;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoronoiDiagramTest extends BaseUnitTestHelper {
    private static String visualizeArea(Context ctx, VoronoiDiagram voronoi) {
        String[][] grid = new String[ctx.board().height()][ctx.board().width()];
        for (String[] line : grid) {
            Arrays.fill(line, " ");
        }

        int i = 1;
        for (Snake snake : ctx.state().snakes()) {
            for (Point p : voronoi.getSnakeArea(snake)) {
                grid[p.y()][p.x()] = Integer.toString(i);
            }
            i++;
        }

        StringBuilder sb = new StringBuilder();
        for (i = grid.length - 1; i >= 0; i--) {
            String[] line = grid[i];
            sb.append(String.join(" ", line));
            sb.append("\n");
        }

        return sb.toString();
    }

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


        Set<Point> intersection = ctx.state().snakes().stream()
                .map(voronoi::getSnakeArea).reduce(
                        (a, el) -> {
                            a.retainAll(el);
                            return a;
                        })
                .get();
        assertEquals(Set.of(), intersection, "Snakes' areas should not overlap");
    }

    @Test
    void visualizeAreasSampleState() throws IOException {
        Context ctx = readContext("sample-state");
        var voronoi = new VoronoiDiagram(ctx);

        assertEquals(visualizeArea(ctx, voronoi),
                """
                        1 2 2 2 2 2 2 2 2 2 2
                        1 2 2 2 2 2 2 2 2 2 2
                        1 2 2 2 2 2 2 2 2 2 2
                        1 2 2 2 2 2 2 2 2 2 2
                        1 2 2 2 2 2 2 2 2 2 2
                        1 2 2 2 2 2 2 2 2 2 2
                        1 2 2 2 2   2 2 2 2 2
                        1 1 2 2 2     2 2 2 2
                        1 1 2 2 2 2 2 2 2 2 2
                        1 1 1 2 2 2 2 2 2 2 2
                            1 2 2 2 2 2 2 2 2
                        """
        );
    }

}
