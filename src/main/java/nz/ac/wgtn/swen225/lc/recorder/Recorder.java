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
    static boolean isRecording = false;
    Map<Integer, RecordItem> currentRecording;
    Map<Integer, Map<Integer, RecordItem>> allRecordings;

    // ----------------------------------- CONSTRUCTOR ----------------------------------- //

    /**
     * The constructor for the Recorder class.
     */
    public Recorder() {
        setUpRecorder();
    }

    // ----------------------------------- METHODS ----------------------------------- //

    /**
     * This method will set up the recorder.
     * It will set the replay speed to 0 (manual replay) and wait for the user to start recording.
     */
    private void setUpRecorder() {
        // ---- set up the recorder ---- //
        replaySpeed = 0;

        // ---- wait for the user to start recording ---- //
        //String userInput = App.getUserInput();
        String userInput = "";
        if (userInput.equals("START")) {
            setIsRecording(true);
            startRecording();
        }
        if (userInput.equals("LOAD_GAME")) {
            setIsRecording(false);
        }
        if (userInput.equals("MANUAL_REPLAY")) {
            setIsRecording(false);
            stepByStepReplay();
        }
        if (userInput.equals("AUTO_REPLAY")) {
            setIsRecording(false);
            autoReplay();
        }

    }

    /**
     * When called this will start recording the user's actions.
     */
    public void startRecording() {
        System.out.println("Recorder: Recording has started. \n");
        currentRecording = new HashMap<>();
        int currentSequenceNumber = 0;

        // ---- Record the initial game ---- //
        if (isRecording) {
            RecordItem initialGame = new RecordItem(currentSequenceNumber, "0,0",
                    "0,0", "INITIAL_GAME", "NONE", "NONE");
            currentRecording.put(currentSequenceNumber, initialGame);

            System.out.println("  Recorder: Initial game has been added:  [ " + initialGame + " ] \n");

            currentSequenceNumber++;
        }

        while (isRecording) {
            // ---- Some move made ---- //
            RecordItem currentChange = new RecordItem(currentSequenceNumber, "0,0",
                    "0,0", "MOVE_MADE", "NONE", "NONE");
            currentRecording.put(currentSequenceNumber, currentChange);

            System.out.println("  Recorder: Move has been added: [ " + currentChange + " ] \n");
        }

        System.out.println("Recorder: Recording has stopped. \n");
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

    public boolean getIsRecording() {
        return isRecording;
    }

    // ----------------------------------- SETTERS ----------------------------------- //

    /**
     * This method will set the isRecording variable.
     *
     * @param isRecording - the boolean value to set isRecording to.
     */
    public void setIsRecording(boolean isRecording) {
        Recorder.isRecording = isRecording;
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
