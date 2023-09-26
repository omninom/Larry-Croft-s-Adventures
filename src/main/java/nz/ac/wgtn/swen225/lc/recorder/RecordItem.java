// @Author:  Neeraj Patel (300604056)

package nz.ac.wgtn.swen225.lc.recorder;

/**
 * RecordItem.java.
 *
 * <p>RecordItem class is used to store information about the activity of the game.
 *
 * @author Neeraj Patel (300604056).
 */
public class RecordItem {
  // ----------------------------------- VARIABLES ----------------------------------- //
  int sequenceNumber; // the turn number that the action was taken on
  String actor; // the actor that took the action
  String currPostion; // the position of the player when the action was taken
  String prevPostion; // the position of the player before the action was taken
  String other; // any other information that is needed to be stored

  /**
   * Constructor for RecordItem class.
   *
   * @param seqNumber - the sequence number of the action.
   * @param actor - the actor that took the action.
   * @param currPos - the current position of the player.
   * @param prevPos - the previous position of the player.
   * @param other - any other information that is needed to be stored.
   */
  public RecordItem(int seqNumber, String actor, String currPos, String prevPos, String other) {
    this.sequenceNumber = seqNumber;
    this.actor = actor;
    this.currPostion = currPos;
    this.prevPostion = prevPos;
    this.other = other;
  }

  /**
   * Returns the sequence number of the action.
   *
   * @return sequenceNumber - the sequence number of the action.
   */
  public int getSequenceNumber() {
    return sequenceNumber;
  }

  /**
   * Returns the actor that took the action.
   *
   * @return actor - the actor that took the action.
   */
  public String getActor() {
    return actor;
  }

  /**
   * Returns the current position of the player.
   *
   * @return currPosition - the current position of the player.
   */
  public String getCurrPosition() {
    return currPostion;
  }

  /**
   * Returns the previous position of the player.
   *
   * @return prevPosition - the previous position of the player.
   */
  public String getPrevPosition() {
    return prevPostion;
  }

  /**
   * Returns any other information that is needed to be stored.
   *
   * @return other - any other information that is needed to be stored.
   */
  public String getOther() {
    return other;
  }

  /**
   * Returns the current position of the player.
   *
   * @return currPosition - the current position of the player.
   */
  public String toString() {
    return "Sequence Number: " + sequenceNumber + ", Current Position: " + currPostion
            + ", Previous Position: " + prevPostion + " Other: " + other;
  }

}
