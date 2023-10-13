package nz.ac.wgtn.swen225.lc.app;

import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;

import java.io.File;

/**
 * The App module.
 *
 * @author Jack Gallagher (300615528)
 */
public class App {

  private final Domain domain;
  private final Recorder recorder;

  /**
   * Constructor.
   */
  public App() {
    this.domain = new Domain();
    recorder = new Recorder(this);
  }

  /**
   * Gets the domain.
   *
   * @return the Domain object.
   */
  public Domain getDomain() {
    return domain;
  }

  /**
   * Gets the recorder.
   *
   * @return the Recorder object.
   */
  public Recorder getRecorder() {
    return recorder;
  }

  /**
   * Handles an input.
   *
   * @param inputType the input to handle.
   * @return whether the input was valid.
   */
  public boolean handleInput(AppInput inputType) {
    System.out.println("[APP DEBUG] Recieved input '" + inputType + "'");

    // TODO: Translate / Validate input with Domain
    try {
      switch (inputType) {
        case MOVE_UP:
          domain.moveChap(Direction.UP);
          break;
        case MOVE_DOWN:
          domain.moveChap(Direction.DOWN);
          break;
        case MOVE_LEFT:
          domain.moveChap(Direction.LEFT);
          break;
        case MOVE_RIGHT:
          domain.moveChap(Direction.RIGHT);
          break;
        default:
          break;
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      return false;
    }

    recorder.addToRecording("CHAP" + "|" + inputType);
    System.out.print("");
    // e.g. recorder.addToRecording("CHAP | MOVE_LEFT"); Send [currentPlayer | move]


    return true;
  }

  /**
   * Starts a new game.
   *
   * @param level - the level to start at.
   */
  public void newGame(int level) {
    System.out.println("[APP DEBUG] New game: Level " + level);

    // TODO: Move this somewhere else
    recorder.startRecording(Integer.toString(level));
  }

  /**
   * Saves the game.
   *
   * @param file the file to save to.
   * @return whether saving was successful.
   */
  public boolean saveGame(File file) {
    System.out.println("[APP DEBUG] Saving game to " + file.getAbsolutePath());

    // TODO: Call persistency module
    return true;
  }

  /**
   * Loads the game.
   *
   * @param file the file to load.
   * @return whether loading was successful.
   */
  public boolean loadGame(File file) {
    System.out.println("[APP DEBUG] Loading game from " + file.getAbsolutePath());

    // TODO: Call persistency module
    return true;
  }

  /**
   * Ends the game.
   */
  public void endGame() {
    System.out.println("[APP DEBUG] Ending game");
    recorder.addToRecording("END"); // Lets recorder know when to stop
  }

  /**
   * Entry Point.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    App app = new App();
    AppWindow window = new AppWindow(app);

    window.setVisible(true);
  }
}
