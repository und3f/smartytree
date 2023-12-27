package name.zasenko.smarty;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import name.zasenko.smarty.snake.Direction;
import name.zasenko.smarty.snake.context.Context;
import name.zasenko.smarty.snake.entities.GameState;
import name.zasenko.smarty.snake.strategy.StrategyFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseUnitTestHelper {

  protected static String strategy;

  protected GameState readState(String filename) throws IOException {
    String resourceFileName = "json/" + filename + ".json";
    final InputStream in = getResource(resourceFileName);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ObjectReader reader = objectMapper.readerFor(new TypeReference<GameState>() {
    });
    return reader.readValue(in);
  }

  protected Context readContext(String filename) throws IOException {
    return new Context(readState(filename));
  }

  protected InputStream getResource(String resourceFileName) {
    return getClass().getClassLoader().getResourceAsStream(resourceFileName);
  }

  protected String getResourceContent(String resourceFileName) throws IOException {
    return new String(this.getResource(resourceFileName).readAllBytes(), StandardCharsets.UTF_8);
  }

  protected Direction RunStrategy(String filename) throws IOException {
    GameState gameState = readState(filename);

    return new Context(gameState).findMove(StrategyFactory.build(strategy));
  }

}