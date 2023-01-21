package name.zasenko.smarty.minmax;

import name.zasenko.smarty.BaseUnitTestHelper;
import name.zasenko.smarty.snake.Context;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class MinMaxTest extends BaseUnitTestHelper {
    @Disabled("Not implemented")
    @Test
    void ScoreTests() throws IOException {
        Context context = new Context(this.readState("sample-state"));

        var me = context.getMe();
        var opponent  = context.getBoard().getSnakes()
                .stream().filter(s -> !s.equals(me)).findFirst();

        assertTrue(opponent.isPresent());
        assertTrue(MinMax.calculateScore(context, me, opponent.get()) < 0);
    }

    @Disabled("Not implemented")
    @Test
    void WinScoreTests() throws IOException {
        Context context = new Context(this.readState("minmax/win"));

        var me = context.getMe();
        var opponent = context.getBoard().getSnakes()
                .stream().filter(s -> !s.equals(me)).findFirst();

        assertTrue(opponent.isPresent());
        assertEquals(Double.NEGATIVE_INFINITY, MinMax.calculateScore(context, me, opponent.get()));

    }

    @Disabled("Not implemented")
    @Test
    void LastMoveScoreTest() throws IOException {
        Context context = new Context(this.readState("minmax/last-move"));

        var me = context.getMe();
        var opponent = context.getBoard().getSnakes()
                .stream().filter(s -> !s.equals(me)).findFirst();

        assertTrue(opponent.isPresent());
        assertEquals(Double.NEGATIVE_INFINITY, MinMax.calculateScore(context, me, opponent.get()));

    }
}
