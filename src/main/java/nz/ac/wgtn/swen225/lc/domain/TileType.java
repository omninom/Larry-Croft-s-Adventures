package nz.ac.wgtn.swen225.lc.domain;

/**
 * Enum for the different types of tile in-game. Used for quick checking of a tile's type without
 * instanceof calls.
 */
public enum TileType {
  /**
   * Impassable wall.
   */
  WALL,
  /**
   * Empty tile.
   */
  FREE,
  /**
   * Tile with a key in it.
   */
  KEY,
  /**
   * Initially-locked door.
   * TODO Rename?
   */
  LOCKED_DOOR,
  /**
   * Tile that displays information when Chap steps on it.
   */
  INFO,
  /**
   * Tile with a treasure on it.
   */
  TREASURE,
  /**
   * Tile that blocks exit until there are no treasures remaining.
   */
  EXIT_LOCK,
  /**
   * Tile that ends the level when entered.
   */
  EXIT
}
