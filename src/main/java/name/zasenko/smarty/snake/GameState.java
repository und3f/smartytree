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

    public boolean isCatchingTail() {
      final Point nextPosition = this.head().move(this.headDirection());

      return nextPosition.equals(this.tail());
    }
  }
}

