package nz.ac.wgtn.swen225.lc.persistency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.wgtn.swen225.lc.domain.*;

public class FileHandler {

  public Maze generateMaze() {
    Maze maze = new Maze(9, 9);
//    Tile[][] tiles = new Tile[9][9];
    for (int i = 0; i < 9; i++) {
      for(int j=0; j<9; j++) {
        maze.setTile(i, j, new FreeTile())  ;
      }
    }
    // BLANK
    maze.setTile(0, 1, new TreasureTile(1));
    // BLANK
    maze.setTile(0, 3, new WallTile());
    maze.setTile(0, 4, new ExitTile());
    maze.setTile(0, 5, new WallTile());
    // BLANK
    maze.setTile(0, 7, new TreasureTile(1));
    // BLANK
    // ===========================================================
    maze.setTile(1, 0, new WallTile());
    maze.setTile(1, 1, new WallTile());
    maze.setTile(1, 2, new LockedDoorTile("green"));
    maze.setTile(1, 3, new WallTile());
    maze.setTile(1, 4, new ExitLockTile());
    maze.setTile(1, 5, new WallTile());
    maze.setTile(1, 6, new LockedDoorTile("green"));
    maze.setTile(1, 7, new WallTile());
    maze.setTile(1, 8, new WallTile());
    // ===========================================================
    // BLANK
    maze.setTile(2, 1, new LockedDoorTile("blue"));
    // BLANK * 5
    maze.setTile(2, 7, new LockedDoorTile("red"));
    // BLANK
    // ===========================================================
    // BLANK
    maze.setTile(3, 1, new WallTile());
    maze.setTile(3, 2, new KeyTile("blue"));
    // BLANK
    // TODO: UNKNOWN TILE
    // BLANK
    maze.setTile(3, 2, new KeyTile("red"));
    maze.setTile(3, 7, new WallTile());
    // BLANK
    // ===========================================================
    maze.setTile(4, 0, new WallTile());
    maze.setTile(4, 1, new WallTile());
    maze.setTile(4, 2, new TreasureTile(1));
    // BLANK
    maze.getChap().setPosition(2, 4);// CHAP START POS
    // BLANK
    maze.setTile(4, 2, new TreasureTile(1));
    maze.setTile(4, 2, new KeyTile("red"));
    maze.setTile(4, 7, new WallTile());
    maze.setTile(4, 8, new WallTile());
    // ===========================================================
    // BLANK
    maze.setTile(5, 1, new WallTile());
    maze.setTile(5, 2, new KeyTile("blue"));
    // BLANK * 3
    maze.setTile(5, 2, new KeyTile("red"));
    maze.setTile(5, 7, new WallTile());
    // BLANK
    // ===========================================================
    // BLANK
    maze.setTile(6, 1, new LockedDoorTile("blue"));
    // BLANK
    maze.setTile(6, 4, new TreasureTile(1));
    // BLANK
    maze.setTile(6, 7, new LockedDoorTile("red"));
    // BLANK
    // ===========================================================
    for(int i=0; i<9; i++) maze.setTile(7, i, new WallTile());
    // ===========================================================
    // BLANK
    maze.setTile(8, 1, new WallTile());
    // BLANK
    maze.setTile(8, 4, new WallTile());
    // BLANK
    maze.setTile(8, 7, new WallTile());
    // BLANK
    // ===========================================================

    return maze;
  }

  public void save(Maze maze, File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(maze);
    System.out.println(json); // DEBUG: raw json string

    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(maze));

    fileWriter.close();
    System.out.println("Save successful");
  }

  public Maze loadMaze(String fileName) {
    File file = new File(fileName);
    Maze maze;

    ObjectMapper mapper = new ObjectMapper();
    try {
      maze = mapper.readValue(file, Maze.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return maze;
  }
}
