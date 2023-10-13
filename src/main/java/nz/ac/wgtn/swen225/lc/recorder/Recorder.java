package nz.ac.wgtn.swen225.lc.recorder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import nz.ac.wgtn.swen225.lc.app.App;
import nz.ac.wgtn.swen225.lc.app.AppInput;
import org.json.JSONObject;

/**
 * Recorder.java
 *
 * <p>Recorder class is used to record the game and replay it.
 * It will record the game and save it to a JSON file.
 *
 * @author Neeraj Patel (300604056).
 */
public class Recorder {
  // ----------------------------------- VARIABLES ----------------------------------- //
  private int replaySpeed; // 1 (fastest auto replay speed) to 10 (slowest auto replay speed)

  private Map<Integer, RecordItem> currentRecording;

  private Map<Integer, RecordItem> loadedRecording;

  private int currentSequenceNumber = 0;

  private final App app;


  // ----------------------------------- CONSTRUCTOR ----------------------------------- //

  /**
   * The constructor for the Recorder class.
   * Set initial values.
   */
  public Recorder(App app) {
    replaySpeed = 5;
    this.app = app;
    currentRecording = new HashMap<>();
  }

  // ----------------------------------- METHODS ----------------------------------- //

  /**
   * When called this will start recording and save the recording to a file.
   * Reset the current recording.
   *
   *
   * @param level - the level that the user is currently playing.
   */
  public void startRecording(String level) {
    // ---- Setup recording ---- //
    System.out.println("[RECORDER DEBUG] Recorder: Recording has started.");
    currentRecording = new HashMap<>();
    currentSequenceNumber = 0;

    // ---- Record the initial game ---- //
    RecordItem currentChange = new RecordItem(currentSequenceNumber, "START", level);
    currentRecording.put(currentSequenceNumber, currentChange);
    System.out.println("[RECORDER DEBUG] Recorder: Level has been added: [ "
            + currentChange + " ] ");
    currentSequenceNumber++;
  }

  /**
   * When called this will add the data to the recording.
   *
   *
   * @param data - the data to added to the recording.
   */
  public void addToRecording(String data) {
    // ---- Check if the game has started ---- //
    if (currentRecording == null) {
      System.out.println("Error: No recording has been started.");
      return;
    }

    // ---- Split the data up ---- //
    String[] dataArray = data.split("\\|");
    String actor = dataArray[0].trim();

    // ---- Check type of data ---- //
    if (dataArray[0].equals("END")) {
      endRecording();
      return;
    }

    // ---- Record Player movement ---- //
    String move = dataArray[1].trim();
    RecordItem newItem = new RecordItem(currentSequenceNumber, actor, move);
    System.out.println("[RECORDER DEBUG] Recorder: Player has been added: [ " + newItem + " ]");
    currentRecording.put(currentSequenceNumber, newItem);
    currentSequenceNumber++;
  }

  /**
   * When called this will end the recording and save the recording to a file.*/
  private void endRecording() {
    // ---- Ask user the location and file name ---- //
    JFileChooser fileFinder = new JFileChooser();
    fileFinder.setDialogTitle("Select file to save recording to:");

    // ---- Validate the file ---- //
    int userAction = fileFinder.showSaveDialog(null);
    if (userAction == JFileChooser.APPROVE_OPTION) {
      String filePath = fileFinder.getSelectedFile().getAbsolutePath();

      // ---- Save the current game in JSON format ---- //
      System.out.println("[RECORDER DEBUG] Recorder: saved json; c.r. size: "
              + currentRecording.size());
      try {
        JSONObject gameJson = new JSONObject(currentRecording);
        Files.writeString(Paths.get(filePath), gameJson.toString());
      } catch (IOException e) {
        System.out.println("Error: Saving Json file: " + e.getMessage());
      }
    }
  }


  /**
   * When called this will replay the recording in steps when the user presses a button.
   */
  public void manualReplay(HashMap<Integer, RecordItem> toLoadRecording) {
    // ---- Load the game ---- //
    this.loadedRecording = toLoadRecording;
    if (loadedRecording.isEmpty()) {
      System.out.println("Error: No recording loaded.");
      return;
    }

    // ---- Display the loaded game ---- //
    int currentSequenceNumber = 0;
    String level = loadedRecording.get(currentSequenceNumber).getMove();
    app.newGame(Integer.parseInt(level));
    currentSequenceNumber++;

    // ---- Replay actions  ---- //
    while (currentSequenceNumber < loadedRecording.size()) {
      if (loadedRecording.get(currentSequenceNumber).getActor().equals("END")) {
        app.endGame();
        break;
      }
      System.out.println("Press the 'enter' key to step through the replay.");

      // -- Wait for user input -- //
      try {
        System.in.read();
      } catch (IOException e) {
        System.out.println("Error: Waiting for user input: " + e.getMessage());
      }
      System.out.println("[RECORDER DEBUG] Recorder: User has pressed the 'enter' key.");

      // -- Do next move when button pressed-- //
      sendToApp(loadedRecording.get(currentSequenceNumber));
      currentSequenceNumber++;
    }

  }

  /**
   * When called will replay the recording at the speed set by the user.
   */
  public void autoReplay(Map<Integer, RecordItem> toLoadRecording) {
    // ---- Load the game ---- //
    this.loadedRecording = toLoadRecording;
    if (loadedRecording.isEmpty()) {
      System.out.println("Error: No recording loaded.");
      return;
    }

    // ---- Initialise the level ---- //
    int currentSequenceNumber = 0;
    RecordItem currentRecord = loadedRecording.get(0);

    if (currentRecord.getActor().equals("START")) {
      app.newGame(Integer.parseInt(currentRecord.getMove()));
      System.out.println("[RECORDER DEBUG] Recorder to App: Level [ "
              + currentRecord.getMove() + " ] ");
      currentSequenceNumber++;
    }
    else {
      System.out.println("Error: No level loaded.");
      return;
    }

    autoReplayHelper(currentSequenceNumber);
  }

  private void autoReplayHelper(int currentSequenceNumber) {
    // ---- Check if the game has ended ---- //
    if (currentSequenceNumber >= loadedRecording.size()) {
      app.endGame();
      return;
    }

    // ---- Replay the action ---- //
    RecordItem currentRecord = loadedRecording.get(currentSequenceNumber);
    try {
      Thread.sleep(400L * replaySpeed);
    } catch (InterruptedException e) {
      System.out.println("Error: Waiting for user input: " + e.getMessage());
    }
    sendToApp(currentRecord);
    autoReplayHelper(currentSequenceNumber + 1);

  }

  /**
   * When called this will send the data to App for which action to take.
   */
  private void sendToApp(RecordItem currentRecord) {
    // ---- Extract the data ---- //
    String move = currentRecord.getMove().trim();

    // ---- Send the user move data to App ---- //
    switch (move) {
      case "MOVE_UP":
        app.handleInput(AppInput.MOVE_UP);
        break;
      case "MOVE_DOWN":
        app.handleInput(AppInput.MOVE_DOWN);
        break;
      case "MOVE_LEFT":
        app.handleInput(AppInput.MOVE_LEFT);
        break;
      case "MOVE_RIGHT":
        app.handleInput(AppInput.MOVE_RIGHT);
        break;
      default:
        throw new IllegalArgumentException("Error: Invalid move: " + move);
    }

    a
  }

  // ----------------------------------- GETTERS ----------------------------------- //

  /**
   * Returns the current recording.
   *
   * @return the current recording.
   */
  public Map<Integer, RecordItem> getCurrentRecording() {
    return Collections.unmodifiableMap(currentRecording);
  }

  // ----------------------------------- SETTERS ----------------------------------- //

  /**
   * Sets the replay speed to the given value.
   *
   * @param replaySpeed - the replay speed to set.
   */
  public void setReplaySpeed(int replaySpeed) {
    this.replaySpeed = replaySpeed;
  }

}
