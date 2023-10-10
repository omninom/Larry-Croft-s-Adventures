package nz.ac.wgtn.swen225.lc.domain;

/**
 * Handles the generation of the level and most of the logic for the game.
 * TODO: export a lot of this to Domain. Maze != game logic.
 *
 * @author Riley West (300608942).
 * @author Jebadiah (300629357).
 */
public class Maze {
  private Tile[][] tiles;
  private int numRows;
  private int numCols;
  private int treasureRemaining;
  private Chap chap;

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
    this.treasureRemaining = 0;
    this.chap = new Chap(0, 0);
  }

  //GETTER METHODS

  /**
   * Getter method for the amount of remaining chips.
   *
   * @return Amount of Chips Remaining
   */
  public int remainingTreasure() {
    return treasureRemaining;
  }

  /**
   * Getter method for the maze tiles.
   *
   * @return 2d Array of the maze
   */
  public Tile[][] getTiles() {
    return tiles;
  }

  /**
   * Getter method for chap object.
   *
   * @return chap object
   */
  public Chap getChap() {
    return chap;
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
   * Setter for treasure count.
   * TODO check if this should be removed.
   *
   * @param treasureRemaining new amount of remaining treasure.
   */
  public void setTreasureRemaining(int treasureRemaining) {
    this.treasureRemaining = treasureRemaining;
  }

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
   * Attempts to move chap in the given Direction. Silently fails if it's an invalid move.
   * TODO move this to dedicated game logic class.
   * TODO throw errors if the move is invalid.
   *
   * @param dir the Direction of movement.
   */
  public void moveChap(Direction dir) {
    int newRow = this.chap.getPosition().x;
    int newCol = this.chap.getPosition().y;

    switch (dir) {
      case UP:
        newRow--;
        break;
      case DOWN:
        newRow++;
        break;
      case LEFT:
        newCol--;
        break;
      case RIGHT:
        newCol++;
        break;
      default:
        //In normal cases, should not trigger, as only these 4 enums exist.
        throw new IllegalArgumentException("Unknown direction.");
    }

    if (isValidMove(newRow, newCol)) {
      this.chap.setPosition(newRow, newCol);
    }
  }

  /**
   * Utility method for checking whether Chap can move to a particular tile.
   * TODO either add logic in here that references Chap's position or rename the tile
   * To something like "IsOccupiableTile", to better reflect what it does.
   * Currently, it doesn't check if the tile is adjacent to Chap.
   * TODO Investigate locked doors and exit lock code
   *
   * @param row row of the tile to check.
   * @param col row of the tile to check.
   * @return whether Chap can move to this tile.
   */
  public boolean isValidMove(int row, int col) {
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
   * TODO fix bug in this method where it loops over numRows instead of numCols once
   */
  public void generateMaze() {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numRows; j++) {
        tiles[i][j] = new FreeTile();
      }
    }
  }
}
