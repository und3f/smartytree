package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.Point;

public class WrappedBoardContext extends BoardContext {
    public WrappedBoardContext(int height, int width) {
        super(height, width);
    }

    @Override
    public Point movePoint(Point source, Point vector) {
        return new Point(
                (source.y() + vector.y() + height()) % height(),
                (source.x() + vector.x() + width()) % width()
        );
    }
}
