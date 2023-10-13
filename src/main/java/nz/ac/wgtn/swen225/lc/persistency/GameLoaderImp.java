package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import nz.ac.wgtn.swen225.lc.domain.Domain;

public class GameLoaderImp implements GameLoader {

  public void loadGame(Domain domain, File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    GameState gameState = mapper.readValue(file, GameState.class);

    domain.buildNewLevel(gameState.mazeTiles(), gameState.chap(), gameState.enemies(), List.of(), gameState.infoText(), gameState.levelNumber());
  }
}

