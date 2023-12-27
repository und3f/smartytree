package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.Point;

public class BorderedBoardContext extends BoardContext {
    public BorderedBoardContext(int height, int width) {
        super(height, width);
    }

    @Override
    public Point movePoint(Point source, Point vector) {
        Point point = new Point(source.y() + vector.y(), source.x() + vector.x());

        if (!isValid(point))
            return null;
        return point;
    }
}
