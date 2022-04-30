package name.zasenko.smarty.snake;

public class Point implements Comparable<Point> {
  private int x, y;

  Point() {
  }

  public Point(int y, int x) {
    this.x = x;
    this.y = y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Point move(Point p2) {
    return new Point(y + p2.y, x + p2.x);
  }

  public Point move(Direction direction) {
    Point p2 = direction.offset();
    return new Point(y + p2.y, x + p2.x);
  }

  public int manhattanTo(Point to) {
    return Math.abs(x - to.x) + Math.abs(y - to.y);
  }

  public Direction[] directionTo(Point destination) {
    int dx = destination.x - x;
    int dy = destination.y - y;
    Direction[] ret = new Direction[(dx != 0 ? 1 : 0) + (dy != 0 ? 1 : 0)];

    if (dx < 0) {
      ret[0] = Direction.left;
    } else if (dx > 0) {
      ret[0] = Direction.right;
    }
    if (dy < 0) {
      ret[ret.length - 1] = Direction.down;
    } else if (dy > 0) {
      ret[ret.length - 1] = Direction.up;
    }

    return ret;
  }

  @Override
  public String toString() {
    return String.format("{%d, %d}", y, x);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point) {
      final Point p2 = (Point) obj;
      return this.x == p2.x && this.y == p2.y;
    }

    return super.equals(obj);
  }

  @Override
  public int compareTo(Point p2) {
    int compare = this.y - p2.y;
    if (compare == 0)
      compare = this.x - p2.x;
    return compare;
  }
}
