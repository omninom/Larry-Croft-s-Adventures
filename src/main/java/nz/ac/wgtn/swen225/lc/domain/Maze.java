package nz.ac.wgtn.swen225.lc.domain;

/**
 * Handles the generation of the level and most of the logic for the game.
 * Should ONLY be handled directly by Domain and Persistency.
 * TODO: export a lot of this to Domain. Maze != game logic.
 * TODO: run spotbugs, do a bunch of immutability.
 *
 * @author Riley West (300608942).
 * @author Jebadiah (300629357).
 */
public class Maze {
  private Tile[][] tiles;
  private int numRows;
  private int numCols;

  /**
   * Default constructor for deserialising by Jackson.
   * TODO implement one of these? Check with Benjamin.
   */
  public Maze() {
  }

  /**
   * "Normal" constructor.
   *
   * @param numRows row count of the maze.
   * @param numCols column count of the maze.
   */
  public Maze(int numRows, int numCols) {
    this.numRows = numRows;
    this.numCols = numCols;
    this.tiles = new Tile[numRows][numCols];
  }

  //GETTER METHODS

  /**
   * Getter method for the maze tiles.
   *
   * @return 2d Array of the maze
   */
  public Tile[][] getTiles() {
    return tiles;
  }

  /**
   * Getter method for number of rows in a maze.
   *
   * @return Number of rows
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * Getter method for number of columns in the maze.
   *
   * @return Number of columns
   */
  public int getNumCols() {
    return numCols;
  }

  // SETTER METHODS

  /**
   * Setter for the tiles of the maze grid.
   * TODO Validating of passed array, shouldn't we change the variables for maze size?
   *
   * @param tiles new tile grid array.
   */
  public void setTiles(Tile[][] tiles) {
    this.tiles = tiles;
  }

  /**
   * Setter for a single tile of the maze.
   *
   * @param row  row to place tile on.
   * @param col  column to place tile at.
   * @param tile Tile to place.
   */
  public void setTile(int row, int col, Tile tile) {
    this.tiles[row][col] = tile;
  }

  //OTHER METHODS

  /**
   * Utility method for checking whether a particular tile is passable to actors.
   * TODO Implement locked doors and exit locks.
   * TODO move this to Domain too.
   *
   * @param row row of the tile to check.
   * @param col row of the tile to check.
   * @return whether Chap can move to this tile.
   */
  public boolean isTilePassable(int row, int col) {
    if (row >= numRows || row < 0) {
      return false;
    } else if (col >= numCols || col < 0) {
      return false;
    } else {
      switch (tiles[row][col].getType()) {
        case WALL:
          return false;
        case LOCKED_DOOR:
          LockedDoorTile test = (LockedDoorTile) tiles[row][col];
          return test.isLocked();
        case EXIT_LOCK:
          ExitLockTile test2 = (ExitLockTile) tiles[row][col];
          return test2.canPass();
        default:
          return true;
      }
    }
  }

  /**
   * Utility method to fill the grid with blank tiles.
   */
  public void generateMaze() {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        tiles[i][j] = new FreeTile();
      }
    }
  }
}
