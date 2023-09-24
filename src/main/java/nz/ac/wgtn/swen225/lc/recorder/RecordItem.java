package nz.ac.wgtn.swen225.lc.recorder;

/**
 * RecordItem class is used to store information about the activity of the game.
 */
public class RecordItem {
    int sequenceNumber;
    String prevPostion;
    String currPostion;
    String other;

    /**
     * Constructor for RecordItem class.
     *
     * @param sequenceNumber - the turn number that the action was taken on.
     * @param currPosition - the position of the player when the action was taken.
     * @param prevPosition - the position of the player before the action was taken.
     * @param other - any other information that is needed to be stored.
     */
    public RecordItem(int sequenceNumber, String currPosition, String prevPosition, String other) {
        this.sequenceNumber = sequenceNumber;
        this.currPostion = currPosition;
        this.prevPostion = prevPosition;
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
