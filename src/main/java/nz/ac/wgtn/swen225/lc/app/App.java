package nz.ac.wgtn.swen225.lc.app;

import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;

import java.io.File;

/**
 * The App module.
 * 
 * @author Jack Gallagher (300615528)
 */
public class App {

  private final Domain domain;
  //private final Recorder recorder;

  // TODO: Move to DOMAIN!!!
  private final Maze maze;

  /**
   * Constructor.
   */
  public App() {
    this.domain = new Domain();
    //this.recorder = new Recorder();

    this.maze = new Maze(9, 9);
  }

  /**
   * Gets the domain.
   * @return the Domain object.
   */
  public Domain getDomain() {
    return domain;
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
    switch (inputType) {
      case MOVE_UP:
        maze.moveChap(Direction.UP);
        break;
      case MOVE_DOWN:
        maze.moveChap(Direction.DOWN);
        break;
      case MOVE_LEFT:
        maze.moveChap(Direction.LEFT);
        break;
      case MOVE_RIGHT:
        maze.moveChap(Direction.RIGHT);
        break;
      default:
        break;
    }

    //recorder.sendToRecording("Player | 0,0 | 0,0 | " + inputType);  // TODO: Actually store the positions?

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
    //recorder.sendToRecording(Integer.toString(level));
    //recorder.setRecording();
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
    //recorder.setWaiting();
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
