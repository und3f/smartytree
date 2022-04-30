package name.zasenko.smarty.snake.strategy;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.Direction;

public interface Strategy {
    public Direction findMove(Context ctx);
}
