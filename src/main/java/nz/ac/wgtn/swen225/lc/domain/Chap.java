package nz.ac.wgtn.swen225.lc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Handles the Chap class which is the main character of the game.
 *
 * @author Riley West (300608942).
 * @author Jebadiah (300629357).
 */

public class Chap {

  //Chap's current position.
  private Point position;

  private Direction direction;

  //The keys that Chap is holding.
  private ArrayList<Tile> keys;
  private int treasure;
  private boolean alive;

  /**
   * Constructor for the Chap class.
   *
   * @param initialRow Chap's starting row.
   * @param initialCol Chap's starting column.
   */
  public Chap(int initialRow, int initialCol) {
    this.position = new Point(initialRow, initialCol);
    this.keys = new ArrayList<>();
    this.alive = true;
    this.direction = Direction.DOWN;
  }

  /**
   * JSON Constructor for the Chap class.
   *
   * @param position Chap's saved position.
   * @param keys     Chap's saved keys.
   * @param alive    Whether Chap is alive.
   * @param direction Which direction Chap is facing in.
   */
  @JsonCreator
  public Chap(@JsonProperty("position") Point position, @JsonProperty("keys") ArrayList<Tile> keys,
              @JsonProperty("alive") boolean alive, @JsonProperty("direction") Direction direction) {
    // Initialize fields with arguments
    this.position = position;
    this.keys = keys;
    this.alive = alive;
    this.direction = direction;
  }

  /**
   * Getter for position.
   *
   * @return Chap's current Position.
   */
  public Point getPosition() {
    return position;
  }


  /**
   * Setter for position.
   *
   * @param row Chap's new row.
   * @param col Chap's new column.
   */
  public void setPosition(int row, int col) {
    position.setLocation(row, col);
  }

  /**
   * Getter for keys.
   *
   * @return Chap's currently-held keys.
   */
  public ArrayList<Tile> getKeys() {
    return keys;
  }

  /**
   * Adds a key to Chap's list of held keys.
   * TODO check typing here, why is it a Tile? should it be more specific?
   *
   * @param key the new key for Chap to hold.
   */
  public void addKey(Tile key) {
    keys.add(key);
  }

  /**
   * Removes a key from Chap's list of held keys.
   * TODO check typing here, why is it a Tile? should it be more specific?
   *
   * @param key the new key for Chap to hold.
   */
  public void removeKey(Tile key) {
    keys.remove(key);
  }

  /**
   * Getter for Chap's alive state.
   * TODO expose this in Domain
   *
   * @return true if Chap is alive, false otherwise.
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Setter for Chap's alive state.
   *
   * @param k Chap's new life status.
   */
  public void setAlive(boolean k) {
    this.alive = k;
  }

  /**
   * Setter for direction.
   *
   * @param direction Chap's new direction.
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /**
   * Getter for direction.
   *
   * @return Chap's current direction.
   */
  public Direction getDirection() {
    return direction;
  }
}
