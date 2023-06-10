
package name.zasenko.smarty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webclient.WebClient;
import io.helidon.webclient.WebClientResponse;
import io.helidon.webserver.WebServer;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private static WebServer webServer;
    private static WebClient webClient;

    @BeforeAll
    static void startTheServer() {
        webServer = Main.startServer().await();

        webClient = WebClient.builder()
                .baseUri("http://localhost:" + webServer.port())
                .addMediaSupport(JsonpSupport.create())
                .build();
    }

    @AfterAll
    static void stopServer() throws Exception {
        if (webServer != null) {
            webServer.shutdown()
                    .toCompletableFuture()
                    .get(10, TimeUnit.SECONDS);
        }
    }

    @Test
    void indexTest() {
        JsonObject response = webClient.get()
                .path("/")
                .request(JsonObject.class)
                .await();
        assertTrue(response.getString("color").matches("(?i)^#[0-9A-F]{3,6}$"));
        assertTrue(response.getString("head").matches("^[\\w-]+$"));
        assertTrue(response.getString("tail").matches("^[\\w-]+$"));
    }

    @Test
    void startTest() throws IOException, NullPointerException {
        WebClientResponse response = webClient.post()
                .path("/start")
                .submit(new String(
                        getClass().getClassLoader().getResourceAsStream("json/sample-state.json").readAllBytes(),
                        StandardCharsets.UTF_8
                ))
                .await();
        assertEquals(200, response.status().code());
    }

    @Test
    void moveTest() throws IOException, NullPointerException {
        JsonObject response = webClient.post()
                .path("/move")
                .submit(new String(
                        getClass().getClassLoader().getResourceAsStream("json/sample-state.json").readAllBytes(),
                        StandardCharsets.UTF_8
                ), JsonObject.class)
                .await();

        List<String> options = List.of("up", "down", "left", "right");
        assertTrue(options.contains(response.getString("move")));
    }

    @Test
    void endTest() throws IOException {
        WebClientResponse response = webClient.post()
                .path("/end")
                .submit(new String(
                        getClass().getClassLoader().getResourceAsStream("json/sample-state.json").readAllBytes(),
                        StandardCharsets.UTF_8
                ))
                .await();
        assertEquals(200, response.status().code());
    }

}
