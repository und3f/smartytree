package name.zasenko.smarty;

import io.helidon.common.http.Http;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import name.zasenko.smarty.snake.Context;
import name.zasenko.smarty.snake.GameState;
import name.zasenko.smarty.snake.strategy.Constrictor;
import name.zasenko.smarty.snake.strategy.Strategy;
import name.zasenko.smarty.snake.strategy.StrategyFactory;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnakeService implements Service {
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private static final Logger LOGGER = Logger.getLogger(SnakeService.class.getName());
    public static final String CONFIG_PREFIX = "app.snake.";
    public static final String DEFAULT_SKIN = "default";

    private final String name;
    private final String color;
    private final String head;
    private final String tail;
    private final Strategy defaultStrategy;

    SnakeService(Config config) {
        this.name = config.get(CONFIG_PREFIX + "name").asString().orElse("Unnamed");
        this.color = config.get(CONFIG_PREFIX + "color").asString().orElse("#0F0");
        this.head = config.get(CONFIG_PREFIX + "head").asString().orElse(DEFAULT_SKIN);
        this.tail = config.get(CONFIG_PREFIX + "tail").asString().orElse(DEFAULT_SKIN);

        final String strategyName = config.get(CONFIG_PREFIX + "strategy").asString().orElse(StrategyFactory.StrategyFindFood);
        LOGGER.log(Level.INFO, "Building strategy {0}", strategyName);
        this.defaultStrategy = StrategyFactory.build(strategyName);
    }

    @Override
    public void update(Routing.Rules rules) {
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

        JsonObject returnObject = JSON.createObjectBuilder()
                .add("apiversion", API_VERSION)
                .add("name", this.name)
                .add("author", AUTHOR)
                .add("color", this.color)
                .add("head", this.head)
                .add("tail", this.tail)
                .add("version", VERSION)
                .build();

        response.send(returnObject);
    }

    public void start(ServerRequest request, ServerResponse response) {
        okResponse(response);
    }

    public void turn(ServerRequest request, ServerResponse response) {
        request.content().as(GameState.class)
                .thenAccept(gameState -> performTurn(gameState, response))
                .exceptionally(ex -> processErrors(ex, response));
    }

    private void performTurn(GameState gameState, ServerResponse response) {
        var strategy = defaultStrategy;
        if (gameState.getGame().getRuleset().getName().equals("constrictor")) {
            strategy = new Constrictor();
        }

        LOGGER.log(Level.INFO, "Turn #{0}", gameState.getTurn());
        LOGGER.log(Level.INFO, "Strategy #{0}", strategy.toString());

        final String move = new Context(gameState).findMove(strategy).toString();

        JsonObject returnObject = JSON.createObjectBuilder()
                .add("move", move)
                .build();
        response.send(returnObject);
    }

    public void end(ServerRequest request, ServerResponse response) {
        okResponse(response);
    }

    private void okResponse(ServerResponse response) {
        JsonObject returnObject = JSON.createObjectBuilder().build();
        response.send(returnObject);
    }

    private static <T> T processErrors(Throwable ex, ServerResponse response) {

        if (ex.getCause() instanceof JsonException){

            LOGGER.log(Level.FINE, "Invalid JSON", ex);
            JsonObject jsonErrorObject = JSON.createObjectBuilder()
                    .add("error", "Invalid JSON")
                    .build();
            response.status(Http.Status.BAD_REQUEST_400).send(jsonErrorObject);
        }  else {

            LOGGER.log(Level.SEVERE, "Internal error {0}", ex);
            JsonObject jsonErrorObject = JSON.createObjectBuilder()
                    .add("error", "Internal error")
                    .build();
            response.status(Http.Status.INTERNAL_SERVER_ERROR_500).send(jsonErrorObject);
        }

        return null;
    }

}
