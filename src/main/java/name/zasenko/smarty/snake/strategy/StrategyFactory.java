package name.zasenko.smarty.snake.strategy;

public class StrategyFactory {
    public final static String StrategyFindFood = "FindFood";
    public final static String StrategyFindTail = "FindTail";
    public final static String StrategyCycle = "Cycle";
    public final static String StrategyConstrictor = "Constrictor";
    public final static String StrategyFill = "Fill";

    public static Strategy build(String strategy) {
        switch (strategy) {
            case StrategyFindFood:
                return new FindFood();
            case StrategyFindTail:
                return new FindTail();
            case StrategyCycle:
                return new Cycle();
            case StrategyConstrictor:
                return new Constrictor();
            case StrategyFill:
                return new Fill();
            default:
                throw new RuntimeException("Unknown strategy " + strategy);
        }
    }

}