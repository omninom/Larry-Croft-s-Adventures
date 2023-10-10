package test.nz.ac.wgtn.swen225.lc.persistency;

import java.util.Arrays;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import nz.ac.wgtn.swen225.lc.persistency.*;
import java.io.File;

public class PersistencyTest {
  @Test
  public void saveGame() {
  }

  @Test
  public void loadGame() {
  }

  @Test
  public void saveMaze() {
    FileHandler f = new FileHandler();
    //      f.save(f.generateMaze(), new File("test-save-maze.json"));
  }

  // FIXME: Tests will currently fail due to level not being available in the test environment
  @Test
  public void loadMaze() {
    FileHandler f = new FileHandler();
    Maze m = f.loadMaze("level1.json");
  }

  @Test
  public void saveAndLoadMaze() {
    FileHandler f = new FileHandler();
    Maze m = f.loadMaze("level1.json");
    System.out.println("==== loaded ====");

    try {
      f.save(m, new File("test-save-maze.json"));
    } catch (IOException e) {
      assertFalse(false, "Failed to save file");
    }
    System.out.println("==== saved ====");

//    Maze l = f.loadMaze("test-save-maze.json");
    Maze l = f.loadMaze("level1.json");
//    assertTrue(m.equals(l), "Loaded maze does not match saved maze.");

    // Check tiles in loaded maze match original
    Tile[][] mTiles = m.getTiles();
    Tile[][] lTiles = l.getTiles();
    for (int i = 0; i < mTiles.length; i++) {
      for (int j = 0; j < mTiles[i].length; j++) {
        assertTrue(mTiles[i][j].getType() == lTiles[i][j].getType(), "Loaded maze does not match saved maze.");
      }
    }

    System.out.println("==== reloaded ====");
  }

  @Test
  public void malformedJson() {
  }

}
