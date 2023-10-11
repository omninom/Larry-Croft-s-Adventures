package nz.ac.wgtn.swen225.lc.domain;

import java.awt.Point;
import java.util.ArrayList;

/**
 * The Domain Module's public interface (for lack of a better word), through which it exposes
 * certain values.
 * TODO craft implementation here, make it an implementation of interface DomainView and refactor
 * TODO other modules to fit
 *
 * @author Riley West (300608942).
 * @author Jebadiah (300629357).
 */
public class Domain {

  private Maze maze;
  private Chap chap;
  private int treasureRemaining;
  private boolean won;
  private boolean failed;

  private String info;


  /**
   * Default Constructor. Creates an empty maze and puts chap in the top left position.
   * TODO consider other constructors. More likely, some integration with Persistency or something.
   */
  public Domain() {
    this.maze = new Maze(9, 9);
    maze.generateEmptyMaze();
    chap = new Chap(0, 0);
    this.treasureRemaining = 10;  //TEST VALUE
    won = false;
    failed = false;
    info = "Default Info String";
  }

  private final ArrayList<DomainObserver> observers = new ArrayList<>();

  public void addObserver(DomainObserver observer) {
    observers.add(observer);
  }

  private void notifyObservers(EventType eventType, TileType itemType) {
    for (DomainObserver observer : observers) {
      observer.handleEvent(eventType, itemType);
    }
  }

  /**
   * Attempts to move chap in the given Direction. Silently fails if it's an invalid move.
   * TODO move this to dedicated game logic class.
   * TODO throw errors if the move is invalid.
   *
   * @param dir the Direction of movement.
   * @throws IllegalArgumentException if move is invalid.
   */
  public void moveChap(Direction dir) {
    if (failed) {
      throw new IllegalStateException("Chap has failed.");
    }
    if (won) {
      throw new IllegalStateException("Chap has won.");
    }
    int newRow = this.chap.getPosition().y;
    int newCol = this.chap.getPosition().x;
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
    //Regardless of whether Chap is actually supposed to move, his facing should change.
    this.chap.setDirection(dir);
    TileType targetTile;
    try {
      targetTile = maze.getTiles()[newRow][newCol];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Can't move out of bounds.");
    }
    switch (targetTile) {
      case FREE:
        break;
      case INFO:
        break;
      case WALL:
        throw new IllegalArgumentException("Impassable wall.");
      case RED_KEY:
      case BLUE_KEY:
        notifyObservers(EventType.PICKUP_ITEM, targetTile);
        maze.setTile(newRow, newCol, TileType.FREE);
        chap.addKey(targetTile);
        break;
      case RED_DOOR:
        if (chap.hasKey(TileType.RED_KEY)) {
          notifyObservers(EventType.UNLOCK_DOOR, TileType.RED_DOOR);
          maze.setTile(newRow, newCol, TileType.FREE);
          chap.removeKey(TileType.RED_KEY);
        } else {
          notifyObservers(EventType.LOCKED_DOOR, TileType.RED_DOOR);
          throw new IllegalArgumentException("Can't unlock this red door.");
        }
        break;
      case BLUE_DOOR:
        if (chap.hasKey(TileType.BLUE_KEY)) {
          notifyObservers(EventType.UNLOCK_DOOR, TileType.BLUE_DOOR);
          maze.setTile(newRow, newCol, TileType.FREE);
          chap.removeKey(TileType.BLUE_KEY);
        } else {
          notifyObservers(EventType.LOCKED_DOOR, TileType.BLUE_DOOR);
          throw new IllegalArgumentException("Can't unlock this blue door.");
        }
        break;
      case TREASURE:
        notifyObservers(EventType.PICKUP_ITEM, TileType.TREASURE);
        maze.setTile(newRow, newCol, TileType.FREE);
        treasureRemaining--;
        break;
      case EXIT_LOCK:
        if (treasureRemaining >= 0) {
          throw new IllegalArgumentException("Can't unlock this red door.");
        }
        maze.setTile(newRow, newCol, TileType.FREE);
        break;
      case EXIT:
        this.won = true;
        break;
      default:
        throw new IllegalArgumentException("Unhandled TileType in movement");
    }
    this.chap.setPosition(newCol, newRow);
  }

  /**
   * Getter method for the maze's tiles.
   *
   * @return 2d Array of the mazes Tile objects.
   */
  public TileType[][] getTiles() {
    return maze.getTiles();
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
   * Getter for checking if Info should be displayed.
   *
   * @return true if Chap is on a tile of type INFO, false otherwise.
   */
  public boolean isOnInfo() {
    Point chapPos = chap.getPosition();
    return maze.getTiles()[chapPos.y][chapPos.x] == TileType.INFO;
  }

  /**
   * Getter for info
   *
   * @return infotext for the currently loaded level.
   */
  public String getInfo() {
    return info;
  }


  /**
   * Returns whether or not chap has won by reaching the exit.
   *
   * @return true if chap has won, false otherwise
   */
  public boolean getWon() {
    return won;
  }

  /**
   * Returns whether or not chap has failed: been eaten, etc.
   *
   * @return true if Chap has failed, false otherwise.
   */
  public boolean getFailed() {
    return failed;
  }

  //FUNCTIONS FOR JUNIT TESTS
  public void setFailed(boolean set) {
    this.failed = set;
  }

  public void setWon(boolean set) {
    this.won = set;
  }

  public Maze getMaze() {
    return this.maze;
  }

  public void setMaze(Maze m) {
    this.maze = m;
  }

  public int getTreasureRemaining() {
    return this.treasureRemaining;
  }

}
