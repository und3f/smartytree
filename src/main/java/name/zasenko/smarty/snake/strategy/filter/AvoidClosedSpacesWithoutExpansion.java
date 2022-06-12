package name.zasenko.smarty.snake.strategy.filter;

import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.graph.CC;

import java.util.Set;

public class AvoidClosedSpacesWithoutExpansion extends AvoidClosedSpaces {

    @Override
    protected Set<Integer> getExpandingClusters(Context ctx, CC cc) {
        return Set.of();
    }

}
