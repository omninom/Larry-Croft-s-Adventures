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
    this.chap = new Chap(4, 4);
  }

  public Chap getChap() {
    return chap;
  }

  public TileType getTileType(int row, int col) {
    return tiles[row][col].getType();
  }

  public void setTile(int row, int col, TileType tileType) {
    tiles[row][col] = new Tile(tileType);
  }

}
