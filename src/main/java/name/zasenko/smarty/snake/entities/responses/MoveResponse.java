package name.zasenko.smarty.snake.entities.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import name.zasenko.smarty.snake.Direction;

public record MoveResponse(
        Direction move,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String shout
) {
    public MoveResponse(Direction move) {
        this(move, null);
    }

}
