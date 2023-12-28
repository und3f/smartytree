package name.zasenko.smarty;

import io.helidon.config.Config;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.*;
import name.zasenko.smarty.snake.strategy.Constrictor;
import name.zasenko.smarty.snake.strategy.Fill;
import name.zasenko.smarty.snake.strategy.Strategy;
import name.zasenko.smarty.snake.strategy.StrategyFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SnakeService implements HttpService {
    public static final String CONFIG_PREFIX = "app.snake.";
    public static final String DEFAULT_SKIN = "default";
    private static final Logger LOGGER = Logger.getLogger(SnakeService.class.getName());
    private final String name;
    private final String color;
    private final String head;
    private final String tail;
    private final Strategy defaultStrategy;

    SnakeService() {
        Config config = Config.global();

        this.name = config.get(CONFIG_PREFIX + "name").asString().orElse("Unnamed");
        this.color = config.get(CONFIG_PREFIX + "color").asString().orElse("#0F0");
        this.head = config.get(CONFIG_PREFIX + "head").asString().orElse(DEFAULT_SKIN);
        this.tail = config.get(CONFIG_PREFIX + "tail").asString().orElse(DEFAULT_SKIN);

        final String strategyName = config.get(CONFIG_PREFIX + "strategy").asString().orElse(StrategyFactory.StrategyFindFood);
        LOGGER.log(Level.INFO, "Building strategy {0}", strategyName);
        this.defaultStrategy = StrategyFactory.build(strategyName);
    }

    @Override
    public void routing(HttpRules rules) {
        rules
                .get("/", this::information)
                .post("/start", this::start)
                .post("/move", this::turn)
                .post("/end", this::end);
    }

    public void information(ServerRequest request, ServerResponse response) {
        String API_VERSION = "1";
        String AUTHOR = "Serhii Zasenko";
        String VERSION = "0.0.1-beta";

        response.send(
                new AboutResponse(
                        API_VERSION,
                        name,
                        AUTHOR,
                        this.color,
                        this.head,
                        this.tail,
                        VERSION
                )
        );
    }

    public void start(ServerRequest request, ServerResponse response) {
        okResponse(response);
    }

    public void turn(ServerRequest request, ServerResponse response) {
        GameState gameState = request.content().as(GameState.class);
        performTurn(gameState, response);
    }

    private void performTurn(GameState gameState, ServerResponse response) {
        Strategy strategy = getStrategyForRuleset(gameState.game().ruleset().name());

        LOGGER.log(Level.INFO, "Turn #{0}", gameState.turn());
        LOGGER.log(Level.FINE, "Strategy #{0}", strategy.toString());

        final String move = new Context(gameState).findMove(strategy).toString();

        response.send(new MoveResponse(move));
    }

    public void end(ServerRequest request, ServerResponse response) {
        okResponse(response);
    }

    private void okResponse(ServerResponse response) {
        response.send(new EmptyResponse());
    }

    private Strategy getStrategyForRuleset(String ruleset) {
        return switch (ruleset) {
            case Ruleset.CONSTRICTOR, Ruleset.WRAPPED_CONSTRICTOR -> new Constrictor();
            case Ruleset.SOLO -> new Fill();
            default -> defaultStrategy;
        };
    }
}
