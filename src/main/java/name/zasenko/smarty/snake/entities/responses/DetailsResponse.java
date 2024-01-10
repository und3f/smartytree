package name.zasenko.smarty.snake.entities.responses;

public record DetailsResponse(
        String apiversion,
        String name,
        String author,
        String color,
        String head,
        String tail,
        String version) {

    public static final String API_VERSION = "1";
    public static final String AUTHOR = "Serhii Zasenko";
    public static final String VERSION = "1.0.0";
    public static final String DEFAULT_SKIN = "default";

    public static final String DEFAULT_NAME = "Unnamed";
    public static final String DEFAULT_COLOR = "#0F0";
}
