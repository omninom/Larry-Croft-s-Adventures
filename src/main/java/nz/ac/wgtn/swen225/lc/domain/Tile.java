package nz.ac.wgtn.swen225.lc.domain;
import java.util.*;

/**
 * Tile.java
 *
 * Handles all the different Tile classes
 *
 * @Author: Riley West (300608942).
 */
public class Tile {
  private TileType type;

  public Tile(TileType type) {
    this.type = type;
  }

  public TileType getType() {
    return type;
  }

}

class WallTile extends Tile {
  public WallTile() {
    super(TileType.WALL);
  }
}

class FreeTile extends Tile {
  public FreeTile() {
    super(TileType.FREE);
  }
}

// Key Tile
class KeyTile extends Tile {
  private String color;

  public KeyTile(String color) {
    super(TileType.KEY);
    this.color = color;
  }

  public String getColor() {
    return color;
  }
}

class LockedDoorTile extends Tile {
  private String requiredKeyColor;
  private boolean isLocked;

  public LockedDoorTile(String requiredKeyColor) {
    super(TileType.LOCKED_DOOR);
    this.requiredKeyColor = requiredKeyColor;
    this.isLocked = true;
  }

  public String getRequiredKeyColor() {
    return requiredKeyColor;
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void unlock() {
    isLocked = false;
  }
}

class InfoTile extends Tile {
  private String helpText;

  public InfoTile(String helpText) {
    super(TileType.INFO);
    this.helpText = helpText;
  }

  public String getHelpText() {
    return helpText;
  }
}

class TreasureTile extends Tile {
  private int treasureValue;

  public TreasureTile(int treasureValue) {
    super(TileType.TREASURE);
    this.treasureValue = treasureValue;
  }

  public int getTreasureValue() {
    return treasureValue;
  }
}

class ExitLockTile extends Tile {
  private boolean canPass;

  public ExitLockTile() {
    super(TileType.EXIT_LOCK);
    this.canPass = false;
  }

  public boolean canPass() {
    return canPass;
  }

  public void openExit() {
    canPass = true;
  }
}

class ExitTile extends Tile {
  public ExitTile() {
    super(TileType.EXIT);
  }
}