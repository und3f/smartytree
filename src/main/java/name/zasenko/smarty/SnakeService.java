package name.zasenko.smarty;

import io.helidon.config.Config;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.entities.Ruleset;
import name.zasenko.smarty.snake.entities.responses.DetailsResponse;
import name.zasenko.smarty.snake.entities.responses.MoveResponse;
import name.zasenko.smarty.snake.strategy.Constrictor;
import name.zasenko.smarty.snake.strategy.Fill;
import name.zasenko.smarty.snake.strategy.Strategy;
import name.zasenko.smarty.snake.strategy.StrategyFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

import static name.zasenko.smarty.snake.entities.responses.DetailsResponse.*;

public class SnakeService implements HttpService {
    private static final Logger LOGGER = Logger.getLogger(SnakeService.class.getName());
    public static final String CONFIG_PREFIX = "app.snake.";
    private final Strategy defaultStrategy;

    SnakeService() {
        final String strategyName = Config.global().get(CONFIG_PREFIX + "strategy")
                .asString().orElse(StrategyFactory.StrategyFindFood);
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
        Config config = Config.global();
        DetailsResponse details = new DetailsResponse(
                API_VERSION,
                config.get(CONFIG_PREFIX + "name").asString().orElse(DEFAULT_NAME),
                AUTHOR,
                config.get(CONFIG_PREFIX + "color").asString().orElse(DEFAULT_COLOR),
                config.get(CONFIG_PREFIX + "head").asString().orElse(DEFAULT_SKIN),
                config.get(CONFIG_PREFIX + "tail").asString().orElse(DEFAULT_SKIN),
                VERSION
        );

        response.send(details);
    }

    public void start(ServerRequest request, ServerResponse response) {
        response.status(Status.NO_CONTENT_204);
        response.send();
    }

    public void turn(ServerRequest request, ServerResponse response) {
        GameState gameState = request.content().as(GameState.class);
        performTurn(gameState, response);
    }

    private void performTurn(GameState gameState, ServerResponse response) {
        Strategy strategy = createStrategy(gameState);

        LOGGER.log(Level.INFO, "Turn #{0}", gameState.turn());
        LOGGER.log(Level.FINE, "Strategy #{0}", strategy.toString());

        final Direction move = new Context(gameState).findMove(strategy);

        response.send(new MoveResponse(move));
    }

    public void end(ServerRequest request, ServerResponse response) {
        response.status(Status.NO_CONTENT_204);
        response.send();
    }

    private Strategy createStrategy(GameState state) {
        return switch (state.game().ruleset().name()) {
            case Ruleset.CONSTRICTOR, Ruleset.WRAPPED_CONSTRICTOR -> new Constrictor();
            case Ruleset.SOLO -> new Fill();
            default -> defaultStrategy;
        };
    }
}
