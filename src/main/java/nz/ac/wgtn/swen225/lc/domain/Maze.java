package nz.ac.wgtn.swen225.lc.domain;

public class Maze {
  private Tile[][] tiles;
  private int numRows;
  private int numCols;
  private int treasureRemaining;
  private Chap chap;

  public Maze(int numRows, int numCols) {
    this.numRows = numRows;
    this.numCols = numCols;
    this.tiles = new Tile[numRows][numCols];
    this.treasureRemaining = 0;
    this.chap = new Chap(0, 0);
  }

  public Chap getChap() {
    return chap;
  }

  public Tile getTileType(int row, int col) {
    return tiles[row][col];
  }
}
