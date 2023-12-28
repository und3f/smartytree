package name.zasenko.smarty;

import io.helidon.webclient.http1.Http1Client;
import io.helidon.webclient.http1.Http1ClientResponse;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.testing.junit5.ServerTest;
import io.helidon.webserver.testing.junit5.SetUpRoute;
import name.zasenko.smarty.snake.entities.AboutResponse;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.entities.MoveResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServerTest
class MainTest extends BaseUnitTestHelper {

    private static Http1Client client;

    protected MainTest(Http1Client client) {
        MainTest.client = client;
    }

    @SetUpRoute
    static void routing(HttpRouting.Builder builder) {
        Main.routing(builder);
    }

    @Test
    void indexTest() {
        try (var response = client.get("/").request()) {
            AboutResponse json = response.as(AboutResponse.class);
            assertTrue(json.color().matches("(?i)^#[0-9A-F]{3,6}$"));
            assertTrue(json.head().matches("^[\\w-]+$"));
            assertTrue(json.tail().matches("^[\\w-]+$"));
        }
    }

    @Test
    void startTest() throws IOException, NullPointerException {
        GameState state = readState("sample-state");
        try (Http1ClientResponse response = client
                .post("/start")
                .submit(state)
        ) {
            assertEquals(200, response.status().code());
        }
    }

    @Test
    void moveTest() throws IOException, NullPointerException {
        var gameState = readState("sample-state");
        try (
                Http1ClientResponse response = client.post("/move").submit(gameState)
        ) {
            MoveResponse json = response.as(MoveResponse.class);
            List<String> options = List.of("up", "down", "left", "right");
            assertTrue(options.contains(json.move()));
        }

    }

    @Test
    void endTest() throws IOException {
        var gameState = readState("sample-state");

        try (Http1ClientResponse response = client.post("/end").submit(gameState)) {
            assertEquals(200, response.status().code());
        }
    }

}
