package test.nz.ac.wgtn.swen225.lc.persistency;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import nz.ac.wgtn.swen225.lc.persistency.*;
import java.io.File;

public class PersistencyTest {
  @Test
  public void saveGame() {}

  @Test
  public void loadGame() {}

  @Test
  public void saveMaze() {
    FileHandler f = new FileHandler();
    try {
      f.save(f.generateMaze(), new File("level1.json"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void malformedJson() {}

}
