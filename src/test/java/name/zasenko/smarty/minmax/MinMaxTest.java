package name.zasenko.smarty.minmax;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.context.Context;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinMaxTest extends BaseUnitTestHelper {
    @Disabled("Not implemented")
    @Test
    void ScoreTests() throws IOException {
        Context context = this.readContext("sample-state");

        var me = context.state().me();
        var opponent  = context.state().snakes()
                .stream().filter(s -> !s.equals(me)).findFirst();

        assertTrue(opponent.isPresent());
        assertTrue(MinMax.calculateScore(context, me, opponent.get()) < 0);
    }

    @Disabled("Not implemented")
    @Test
    void WinScoreTests() throws IOException {
        Context context = this.readContext("minmax/win");

        var me = context.state().me();
        var opponent = context.state().snakes()
                .stream().filter(s -> !s.equals(me)).findFirst();

        assertTrue(opponent.isPresent());
        assertEquals(Double.NEGATIVE_INFINITY, MinMax.calculateScore(context, me, opponent.get()));

    }

    @Disabled("Not implemented")
    @Test
    void LastMoveScoreTest() throws IOException {
        Context context = this.readContext("minmax/last-move");

        var me = context.state().me();
        var opponent = context.state().snakes()
                .stream().filter(s -> !s.equals(me)).findFirst();

        assertTrue(opponent.isPresent());
        assertEquals(Double.NEGATIVE_INFINITY, MinMax.calculateScore(context, me, opponent.get()));

    }
}
