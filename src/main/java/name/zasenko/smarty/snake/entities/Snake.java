package name.zasenko.smarty.snake.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.Point;

import java.util.List;

public record Snake(
        String id,
        String name,
        String shout,
        int health,
        int length,
        List<Point> body,
        Point head
) {

    public boolean equals(Snake snake2) {
        return id.equals(snake2.id);
    }

    public Point head() {
        return this.body.get(0);
    }

    public Direction headDirection() {
        Point neck = this.body.get(1);
        Direction[] directions = neck.directionTo(this.head());
        if (directions.length < 1) return Direction.up;

        Direction direction = directions[0];
        if (neck.manhattanTo(this.head) > 1) {
            direction = direction.invert();
        }
        return direction;
    }

    public Point tail() {
        return this.tail(0);
    }

    public Point tail(int offset) {
        return this.body.get(this.body.size() - 1 - offset);
    }

    @JsonIgnore
    public boolean isNextToTail() {
        return head().manhattanTo(tail()) == 1;
    }

    @JsonIgnore
    public boolean isExpanding() {
        return tail(1).equals(tail());
    }

}
