package name.zasenko.smarty.snake;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GameState {
  @Getter
  @Setter
  private Game game;

  @Getter
  @Setter
  private Board board;

  @Getter
  @Setter
  private int turn;

  @Getter
  @Setter
  private Snake you;

  @Override
  public String toString() {
    return this.board.toString();
  }

  public static class Game {
    @Getter
    @Setter
    private String id, source;

    @Getter
    @Setter
    private Ruleset ruleset;

    @Getter
    @Setter
    private int timeout;
  }

  public static class Ruleset {
    @Getter
    @Setter
    private String name, version;

    @Getter
    @Setter
    private GameSettings settings;
  }

  public static class GameSettings {
    @Getter
    @Setter
    private int foodSpawnChance;

    @Getter
    @Setter
    private int hazardDamagePerTurn;
  }

  public static class Board {
    @Getter
    @Setter
    private int height, width;

    @Getter
    @Setter
    private List<Point> food, hazards;

    @Getter
    @Setter
    private List<Snake> snakes;

    Board() {
    }

    Board(Board proto) {
      this.height = proto.height;
      this.width = proto.width;

      this.food = proto.food;
      this.hazards = proto.hazards;
      this.snakes = proto.snakes;
    }

    public int valueOfPoint(Point p) {
      return width * p.getY() + p.getX();
    }

    public Point fromValue(int pointValue) {
      int x = pointValue % width;
      int y = pointValue / width;
      return new Point(y, x);
    }

    public boolean isValid(Point p) {
      return p.getX() >= 0 && p.getX() < width && p.getY() >= 0 && p.getY() < height;
    }

    @Override
    public String toString() {
      String [][] board = new String[getHeight()][getWidth()];

      char snakeIndex = 0;
      for (Snake snake : getSnakes()) {
        for (Point p : snake.getBody()) {
          board[p.getY()][p.getX()] = Integer.toString(snakeIndex + 1);
        }

        Point head = snake.getHead();
        board[head.getY()][head.getX()] = "H";
        snakeIndex++;
      }

      StringBuilder sb = new StringBuilder();
      for (int y = getWidth()-1; y >= 0; y--) {
        for (int x = 0; x < getHeight(); x++) {
          String c = board[y][x];
          sb.append(c != null ? c : '.');
        }
        sb.append("\n");
      }
      return sb.toString();
    }

  }

  public static class Snake {
    @Getter
    @Setter
    private String id, name, shout;

    @Getter
    @Setter
    private int health, length;

    @Getter
    @Setter
    private List<Point> body;

    @Getter
    @Setter
    private Point head;

    public boolean equals(Snake snake2) {
      return id.equals(snake2.id);
    }

    public Point head() {
      return this.body.get(0);
    }

    public Direction headDirection() {
      Direction[] directions = this.body.get(1).directionTo(this.head());
      if (directions.length < 1)
        return Direction.up;

      return directions[0];
    }

    public Point tail() {
      return this.tail(0);
    }

    public Point tail(int offset) {
      return this.body.get(this.body.size() - 1 - offset);
    }

    public boolean isNextToTail() {
      return head().manhattanTo(tail()) == 1;
    }

    public boolean isExpanding() {
      return tail(1).equals(tail());
    }

  }
}

