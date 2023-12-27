package name.zasenko.smarty.snake.entities;

public record Ruleset(
        String name,
        String version,
        GameSettings settings
) {
    public static final String STANDARD = "standard";
    public static final String ROYALE = "royale";
    public static final String SQUAD = "squad";
    public static final String CONSTRICTOR = "constrictor";
    public static final String WRAPPED_CONSTRICTOR = "wrapped-constrictor";
    public static final String WRAPPED = "wrapped";
    public static final String SOLO = "solo";
}
