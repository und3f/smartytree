package name.zasenko.smarty.snake.graph;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Point;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoronoiTest extends BaseUnitTestHelper {

    @Test
    void basicTest() throws IOException {
        var gameState = this.readState("sample-state");
        var graph = new Graph(gameState.getBoard());
        Voronoi v = new Voronoi(graph);
        List<Point> snakeHeads = gameState.getBoard().getSnakes().stream()
                .map(snake -> snake.getHead()).collect(Collectors.toList());

        List<List<Point>> areas = v.buildDiagram(snakeHeads);

        assertEquals(17, areas.get(0).size());
        assertEquals(97, areas.get(1).size());
    }
}
