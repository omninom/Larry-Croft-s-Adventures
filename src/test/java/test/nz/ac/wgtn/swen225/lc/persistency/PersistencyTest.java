package test.nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;
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
    //      f.save(f.generateMaze(), new File("test-save-maze.json"));
  }

  // FIXME: Tests will currently fail due to level not being available in the test environment
//  @Test
//  public void loadMaze() {
//    FileHandler f = new FileHandler();
//    Maze m = f.loadMaze("level1.json");
//  }
//
//  @Test
//  public void saveAndLoadMaze() {
//    FileHandler f = new FileHandler();
//    Maze m = f.loadMaze("level1.json");
//
//    try {
//      f.save(m, new File("test-save-maze.json"));
//    } catch (IOException e) {
//      assertFalse(false, "Failed to save file");
//    }
//  }

  @Test
  public void malformedJson() {}

}
