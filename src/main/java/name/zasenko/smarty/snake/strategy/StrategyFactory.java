package name.zasenko.smarty.snake.strategy;

public class StrategyFactory {
    public final static String StrategyFindFood = "FindFood";
    public final static String StrategyFindTail = "FindTail";
    public final static String StrategyCycle = "Cycle";

    public static Strategy build(String strategy) {
        switch (strategy) {
            case StrategyFindFood:
                return new FindFood();
            case StrategyFindTail:
                return new FindTail();
            case StrategyCycle:
                return new Cycle();
            default:
                throw new RuntimeException("Unknown strategy " + strategy);
        }
    }

}