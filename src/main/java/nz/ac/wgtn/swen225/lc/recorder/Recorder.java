/**
 * Recorder.java
 *
 * <p>This class is a class for the Recorder program.
 * It will crete a new instance of the Recorder at the start of the program.
 *
 */
public class Recorder {
    // ----------------------------------- VARIABLES ----------------------------------- //
    int replaySpeed = 5; // 0 = manual replay & 1 = slowest auto replay speed - 10 = fastest
    static boolean isRecording = false;


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
     * It will set the replay speed to 0 (manual replay) wait for the user to start recording.
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

    }

    /**
     * When called this will replay the recording in step when the user presses a button.
     */
    public void stepByStepReplay() {


    }

    /**
     * When called will replay the recording at the speed set by the user.
     * If the user has not set a speed, it will default to the slowest speed.
     *
     */
    public void autoReplay() {


    }


    // ----------------------------------- GETTERS ----------------------------------- //
    public boolean getIsRecording() {
        return isRecording;
    }



    // ----------------------------------- SETTERS ----------------------------------- //
    public void setIsRecording(boolean isRecording) {
        Recorder.isRecording = isRecording;
    }

    public void setReplaySpeed(int speed) {
        this.replaySpeed = speed;
    }

}
