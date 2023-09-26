package nz.ac.wgtn.swen225.lc.domain;

/**
 * Maze.java
 *
 * Handles the generation of the level and most of the logic for the game
 *
 * @Author: Riley West (300608942).
 */
public class Maze {
  private Tile[][] tiles;
  private int numRows;
  private int numCols;
  private int treasureRemaining;
  private Chap chap;

  public Maze(int numRows, int numCols){
    this.numRows = numRows;
    this.numCols = numCols;
    this.tiles = new Tile[numRows][numCols];
    this.treasureRemaining = 0;
    this.chap = new Chap(0, 0);
  }

  //GETTER METHODS

  /**
   * Getter method for the amount of remaining chips
   * @return Amount of Chips Remaining
   */
  public int remainingTreasure(){
    return treasureRemaining;
  }

  /**
   * Getter method for the maze tiles
   * @return 2d Array of the maze
   */
  public Tile[][] getTiles(){
    return tiles;
  }

  /**
   * Getter method for chap object
   * @return chap object
   */
  public Chap getChap() {
    return chap;
  }

  /**
   * Getter method for number of rows in a maze
   * @return Number of rows
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * Getter method for number of columns in the maze
   * @return Number of columns
   */
  public int getNumCols() {
    return numCols;
  }

  // SETTER METHODS

  public void setTreasureRemaining(int treasureRemaining) {
    this.treasureRemaining = treasureRemaining;
  }

  public void setTiles(Tile[][] tiles) {
    this.tiles = tiles;
  }

  public void setTile(int row, int col, Tile tile){
    this.tiles[row][col] = tile;
  }

  //OTHER METHODS

  public void moveChap(Direction dir){
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
    }

    if (isValidMove(newRow, newCol)){
      this.chap.setPosition(newRow, newCol);
    }
  }

  public boolean isValidMove(int row, int col){
    if(row > numRows || row < 0){
      return false;
    }else if(col > numCols || col < 0){
      return false;
    }else{
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

  public void generateMaze(){
    for (int i = 0; i < numRows; i++){
      for (int j = 0; j < numRows; j++){
        tiles[i][j] = new FreeTile();
      }
    }
  }
}
