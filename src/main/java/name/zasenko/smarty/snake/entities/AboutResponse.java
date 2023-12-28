package name.zasenko.smarty.snake.entities;

public record AboutResponse(
        String apiversion,
        String name,
        String author,
        String color,
        String head,
        String tail,
        String version ) {
}
