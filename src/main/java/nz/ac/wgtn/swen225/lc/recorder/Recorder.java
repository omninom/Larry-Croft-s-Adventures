
package nz.ac.wgtn.swen225.lc.recorder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
// import org.json.JSONObject;


/**
 * Recorder.java
 *
 * <p>Recorder class is used to record the game and replay it.
 * It will record the game and save it to a JSON file.
 *
 * @Author: Neeraj Patel (300604056).
 */
public class Recorder {
  // ----------------------------------- VARIABLES ----------------------------------- //
  int replaySpeed; // 1 (slowest auto replay speed) to 10 (fastest auto replay speed)

  enum RecorderState { RECORDING, MANUAL_REPLAY, AUTO_REPLAY, LOAD_GAME, WAITING }

  Map<Integer, RecordItem> currentRecording;

  RecorderState state;

  String data;

  // ----------------------------------- CONSTRUCTOR ----------------------------------- //

  /**
   * The constructor for the Recorder class.
   */
  public Recorder() {
    // ---- Set up the initial variables ---- //
    state = RecorderState.WAITING;
    replaySpeed = 0;

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
        loadGame();
      }
      if (state == RecorderState.WAITING) {
        // ---- Do nothing ---- //
      }
    }
  }

  // ----------------------------------- METHODS ----------------------------------- //

  /**
   * When called this will start recording the user's actions.
   */
  public void startRecording() {
    // ---- Setup recording ---- //
    System.out.println("Recorder: Recording has started. \n");
    currentRecording = new HashMap<>();
    int currentSequenceNumber = 0;

    // ---- Record the initial game ---- //
    RecordItem currentChange = new RecordItem(currentSequenceNumber, data, null, null, null);
    currentRecording.put(currentSequenceNumber, currentChange);
    System.out.println("  Recorder: Level has been added: [ " + currentChange + " ] \n");
    currentSequenceNumber++;
    data = null;

    // ---- Record the rest of the game ---- //
    while (state == RecorderState.RECORDING && data != null) {
      String[] dataArray = data.split("\\|");
      String actor = dataArray[0];
      String currPos = dataArray[1];
      String prevPos = dataArray[2];
      String other = dataArray[3];

      RecordItem newItem = new RecordItem(currentSequenceNumber, actor, currPos, prevPos, other);
      currentRecording.put(currentSequenceNumber, newItem);

      System.out.println("  Recorder: Move has been added: [ " + newItem + " ] \n");
      currentSequenceNumber++;
      data = null;
    }

    // ---- Stop recording and save the current game in jason format ---- //

    /*#
    System.out.println("Recorder: Recording has stopped & Saving game. \n");
    try {
        JSONObject jsonRecording = new JSONObject(currentRecording);
        String jsonString = jsonRecording.toString();
        Files.writeString(Paths.get("gameRecording.json"), jsonString);
        System.out.println("Recorder: Game recording has been saved as gameRecording.json\n");
    } catch (IOException e) {
        System.out.println("Error: Unable to save game recording. " + e.getMessage());
    }

     */

  }

  /**
   * When called this will replay the recording in steps when the user presses a button.
   */
  public void stepByStepReplay() {
    System.out.println("Recorder: Step by step replaying. \n");


  }

  /**
   * When called will replay the recording at the speed set by the user.
   * If the user has not set a speed, it will default to the slowest speed.
   */
  public void autoReplay() {
    System.out.println("Recorder: Auto replaying. \n");


  }

  /**
   * When called will load the game from a file.
   */
  public void loadGame() {
    System.out.println("Recorder: Loading game. \n");

    // ---- load the game from files ---- //

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
   * Will set the state of the recorder to be waiting.
   */
  public void setWaiting() {
    state = RecorderState.WAITING;
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

  // ----------------------------------- HELPER METHODS ----------------------------------- //

  /**
   * This method will send data to the current recording to the game.
   */
  public void sendToRecording(String data) {
    this.data = data;
  }


  // ----------------------------------- MAIN ----------------------------------- //

  /**
   * The main method for the Recorder program.
   *
   * @param args - the arguments for the main method.
   */
  public static void main(String[] args) {

    // ---- Create a new instance of the Recorder ---- //
    Recorder recorder = new Recorder();

    // ---- Start recording after game is started---- //
    recorder.sendToRecording("1"); // Send the current game level to the recorder
    recorder.setRecording();

    // ---- Record the rest of the game and send when player inputs something---- //
    recorder.sendToRecording("Player| 0,0 | 0,0"); // (actor | currPos | prevPos | other)

    // ---- Stop recording after game is finished ---- //
    recorder.setWaiting();
  }


}
