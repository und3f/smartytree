package name.zasenko.smarty.snake.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectedEdgeTest {

    public static final int V = 2;
    public static final int W = 1;
    public static final double WEIGHT = 0.1;

    @Test
    void edgeTest() {
        DirectedEdge edge = new DirectedEdge(V, W, WEIGHT);
        Assertions.assertEquals(V, edge.getSource());
        Assertions.assertEquals(W, edge.getDestination());
        Assertions.assertEquals(WEIGHT, edge.getWeight());
    }

    @Test
    void toStringTest() {
        DirectedEdge edge = new DirectedEdge(V, W, WEIGHT);
        Assertions.assertEquals("2 -> 1 0.10", edge.toString());
    }
}
