package nz.ac.wgtn.swen225.lc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Handles all the different Tile classes.
 * TODO: Consider Static method for building a tile object based on TileType
 * TODO: Review Tile system.
 *
 * @author Riley West (300608942).
 * @author Jebadiah (300629357).
 */
public class TileX {
  private TileType type;

  /**
   * JSON-supporting general type constructor.
   *
   * @param type the TileType of this Tile.
   */
  @JsonCreator
  public TileX(@JsonProperty("type") TileType type) {
    this.type = type;
  }

  /**
   * Getter for the type of this Tile.
   *
   * @return this Tile's TileType.
   */
  public TileType getType() {
    return type;
  }

}

class WallTile extends TileX {
  public WallTile() {
    super(TileType.WALL);
  }
}

class FreeTile extends TileX {
  public FreeTile() {
    super(TileType.FREE);
  }
}

// Key Tile
class KeyTile extends TileX {
  private String color;

  public KeyTile(String color) {
    super(TileType.KEY);
    this.color = color;
  }

  public String getColor() {
    return color;
  }
}

class LockedDoorTile extends TileX {
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

class InfoTile extends TileX {
  private String helpText;

  public InfoTile(String helpText) {
    super(TileType.INFO);
    this.helpText = helpText;
  }

  public String getHelpText() {
    return helpText;
  }
}

class TreasureTile extends TileX {
  private int treasureValue;

  public TreasureTile(int treasureValue) {
    super(TileType.TREASURE);
    this.treasureValue = treasureValue;
  }

  public int getTreasureValue() {
    return treasureValue;
  }
}

class ExitLockTile extends TileX {
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

class ExitTile extends TileX {
  public ExitTile() {
    super(TileType.EXIT);
  }
}