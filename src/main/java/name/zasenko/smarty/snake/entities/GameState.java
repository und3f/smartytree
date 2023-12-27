package name.zasenko.smarty.snake.entities;

public record GameState(Game game, Board board, int turn, Snake you) {

    @Override
    public String toString() {
        return this.board.toString();
    }

}

