package name.zasenko.smarty.snake.context;

import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;

public abstract class BoardContext {
    private final int height, width;

    public BoardContext(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int height() {
        return this.height;
    }

    public int width() {
        return this.width;
    }

    public int valueOfPoint(Point p) {
        return width * p.y() + p.x();
    }

    public Point fromValue(int pointValue) {
        int x = pointValue % width;
        int y = pointValue / width;
        return new Point(y, x);
    }

    public boolean isValid(Point p) {
        if (p == null)
            return false;
        return p.x() >= 0 && p.x() < width && p.y() >= 0 && p.y() < height;
    }

    public abstract Point movePoint(Point source, Point vector);

    public Point movePoint(Point s, Direction direction) {
        return movePoint(s, direction.offset());
    }
}
