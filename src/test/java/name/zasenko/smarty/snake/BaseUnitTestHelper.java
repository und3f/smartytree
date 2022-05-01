package name.zasenko.smarty.snake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import name.zasenko.smarty.snake.strategy.StrategyFactory;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseUnitTestHelper {

  protected static String strategy;

  protected GameState readState(String filename) throws JsonProcessingException, IOException {
    final InputStream in = getClass().getClassLoader().getResourceAsStream("json/" + filename + ".json");

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ObjectReader reader = objectMapper.readerFor(new TypeReference<GameState>() {
    });
    return reader.readValue(in);
  }

  protected Direction RunStrategy(String filename) throws JsonProcessingException, IOException {
    GameState gameState = readState(filename);

    return new Context(gameState).findMove(StrategyFactory.build(strategy));
  }

}