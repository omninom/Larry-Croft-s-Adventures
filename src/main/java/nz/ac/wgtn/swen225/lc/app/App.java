package nz.ac.wgtn.swen225.lc.app;

import java.io.File;

/**
 * The App module.
 */
public class App {

  /**
   * Constructor.
   */
  public App() {

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
    // TODO: Record input

    return true;
  }

  /**
   * Starts a new game.
   *
   * @param level - the level to start at.
   */
  public void newGame(int level) {
    System.out.println("[APP DEBUG] New game: Level " + level);
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
