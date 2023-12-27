package name.zasenko.smarty.snake.entities;

import name.zasenko.smarty.snake.Point;

import java.util.List;

public record Board(
        int height, int width,
        List<Point> food, List<Point> hazards,
        List<Snake> snakes) {

    public Board(Board proto) {
        this(
                proto.height(), proto.width(),
                proto.food(), proto.hazards(),
                proto.snakes()
        );
    }

    @Override
    public String toString() {
        String[][] board = new String[height()][width()];

        char snakeIndex = 0;
        for (Snake snake : snakes()) {
            for (Point p : snake.body()) {
                board[p.y()][p.x()] = Integer.toString(snakeIndex + 1);
            }

            Point head = snake.head();
            board[head.y()][head.x()] = "H";
            snakeIndex++;
        }

        StringBuilder sb = new StringBuilder();
        for (int y = width() - 1; y >= 0; y--) {
            for (int x = 0; x < height(); x++) {
                String c = board[y][x];
                sb.append(c != null ? c : '.');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
