import java.util.HashMap;
import java.util.Map;

/**
 * Recorder.java
 *
 * <p>This class is a class for the Recorder program.
 * It will create a new instance of the Recorder at the start of the program.
 */
public class Recorder {
    // ----------------------------------- VARIABLES ----------------------------------- //
    int replaySpeed = 5; // 0 = manual replay & 1 = slowest auto replay speed - 10 = fastest

    enum RecorderState { RECORDING, MANUAL_REPLAY, AUTO_REPLAY, LOAD_GAME }

    Map<Integer, RecordItem> currentRecording;

    Map<Integer, Map<Integer, RecordItem>> allRecordings;

    RecorderState state = null;


    // ----------------------------------- CONSTRUCTOR ----------------------------------- //

    /**
     * The constructor for the Recorder class.
     */
    public Recorder() {
        // ---- set up the recorder ---- //
        replaySpeed = 0;
        allRecordings = new HashMap<>();

        // ---- Set the state to what user selects ---- //
        state = RecorderState.RECORDING;

        while (true) {
            if (state == RecorderState.RECORDING) {
                startRecording();
            }
            if (state == RecorderState.MANUAL_REPLAY) {
                stepByStepReplay();
            }
            if (state == RecorderState.AUTO_REPLAY) {
                autoReplay();
            }
            if (state == RecorderState.LOAD_GAME) {
                // ---- load the game ---- //
                System.out.println("Recorder: Loading game. \n");
            }
        }
    }

    // ----------------------------------- METHODS ----------------------------------- //

    /**
     * When called this will start recording the user's actions.
     */
    public void startRecording() {
        System.out.println("Recorder: Recording has started. \n");
        currentRecording = new HashMap<>();
        int currentSequenceNumber = 0;

        // ---- Record the initial game ---- //
        RecordItem initialGame = new RecordItem(currentSequenceNumber, "0,0", "0,0", "NONE");
        currentRecording.put(currentSequenceNumber, initialGame);

        System.out.println("  Recorder: Initial game has been added:  [ " + initialGame + " ] \n");
        currentSequenceNumber++;

        while (state == RecorderState.RECORDING) {
            // ---- Some move made ---- //
            RecordItem currentChange = new RecordItem(currentSequenceNumber, "0,0", "0,0", "NONE");
            currentRecording.put(currentSequenceNumber, currentChange);

            System.out.println("  Recorder: Move has been added: [ " + currentChange + " ] \n");
        }

        // ---- Recording has stopped ---- //
        System.out.println("Recorder: Recording has stopped. \n");
        allRecordings.put(allRecordings.size(), currentRecording);
    }

    /**
     * When called this will replay the recording in steps when the user presses a button.
     */
    public void stepByStepReplay() {

    }

    /**
     * When called will replay the recording at the speed set by the user.
     * If the user has not set a speed, it will default to the slowest speed.
     */
    public void autoReplay() {

    }

    // ----------------------------------- GETTERS ----------------------------------- //

    /**
     * Returns the state of the recorder.
     *
     * @return the state of the recorder.
     */
    public String getState() {
        return state.toString();
    }

    /**
     * Returns the current recording.
     *
     * @return the current recording.
     */
    public int getReplaySpeed() {
        return replaySpeed;
    }

    // ----------------------------------- SETTERS ----------------------------------- //

    /**
     * Will set the state of the recorder to be recording.
     */
    public void setRecording() {
        state = RecorderState.RECORDING;
    }

    /**
     * Will set the state of the recorder to be manual replay.
     */
    public void setManualReplay() {
        state = RecorderState.MANUAL_REPLAY;
    }

    /**
     * Will set the state of the recorder to be auto replay.
     */
    public void setAutoReplay() {
        state = RecorderState.AUTO_REPLAY;
    }

    /**
     * Will set the state of the recorder to be load game.
     */
    public void setLoadGame() {
        state = RecorderState.LOAD_GAME;
    }

    /**
     * This method will set the replay speed.
     *
     * @param speed - the speed to set the replay speed to.
     */
    public void setReplaySpeed(int speed) {
        this.replaySpeed = speed;
    }
}
