package name.zasenko.smarty;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.config.Config;
import io.helidon.http.media.MediaContextConfig;
import io.helidon.http.media.jackson.JacksonSupport;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private Main() {
    }

    public static void main(final String[] args) {
        startServer();
    }

    static WebServer startServer() {
        Config config = Config.create();
        Config.global(config);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .routing(Main::routing)
                .mediaContext(
                        MediaContextConfig.builder()
                                .mediaSupportsDiscoverServices(false)
                                .addMediaSupport(JacksonSupport.create(objectMapper))
                                .build()
                )
                .build()
                .start();

        LOGGER.log(
                Level.INFO,
                "SNAKE is up! {0}:{1}/", new Object[]{
                        "http://localhost",
                        Integer.toString(server.port())
                }
        );

        return server;
    }

    protected static void routing(HttpRouting.Builder routing) {
        routing.register("/", new SnakeService());
    }
}
