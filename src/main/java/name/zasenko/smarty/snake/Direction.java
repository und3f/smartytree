package name.zasenko.smarty.snake;

public enum Direction {
  up(1),
  down(2),
  left(3),
  right(4);

  private final int directionCode;

  Direction(int directionCode) {
    this.directionCode = directionCode;
  }

  public Point offset() {
    return directionToOffset[directionCode - 1];
  }

  public boolean equals(Direction other) {
    return other.directionCode == this.directionCode;
  }

  public String toString() {
    return directionToString[directionCode - 1];
  }

  public Direction rotateClockwise() {
    switch (this) {
      case up:
        return Direction.right;
      case down:
        return Direction.left;
      case right:
        return Direction.down;
      case left:
        return Direction.up;
    }

    throw new RuntimeException("Unexpected direction");
  }

  public Direction rotateCounterclockwise() {
    switch (this) {
      case down:
        return Direction.right;
      case up:
        return Direction.left;
      case left:
        return Direction.down;
      case right:
        return Direction.up;
    }

    throw new RuntimeException("Unexpected direction");
  }

  private static final Point[] directionToOffset = {
      new Point(1, 0), new Point(-1, 0), new Point(0, -1), new Point(0, 1)
  };

  private static final String[] directionToString = { "up", "down", "left", "right" };

  public Direction invert() {
    switch (this) {
      case down:
        return Direction.up;
      case up:
        return Direction.down;
      case left:
        return Direction.right;
      case right:
        return Direction.left;
    }

    throw new RuntimeException("Unexpected direction");
  }
}
