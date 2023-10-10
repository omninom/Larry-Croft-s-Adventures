package nz.ac.wgtn.swen225.lc.domain;

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

  /**
   * Default Constructor. Creates an empty maze and puts chap in the top left position.
   * TODO consider other constructors. More likely, some integration with Persistency or something.
   */
  public Domain() {
    this.maze = new Maze(9, 9);
    maze.generateMaze();
    chap = new Chap(0, 0);
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
    if (maze.isTilePassable(newRow, newCol)) {
      this.chap.setPosition(newRow, newCol);
    }else{
      throw new IllegalArgumentException("Impassable wall.");
    }
  }

  /**
   * Getter method for the maze's tiles.
   *
   * @return 2d Array of the mazes Tile objects.
   */
  public Tile[][] getTiles() {
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
}
