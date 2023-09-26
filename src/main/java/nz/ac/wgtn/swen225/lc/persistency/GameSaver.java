package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Game;

/**
 * Interface for saving in-progress Games.
 */
public interface GameSaver {
  /**
   * Saves a game to file.
   *
   * @param game     The game instance to be saved.
   * @param fileName The name of the file from which the game is to be loaded.
   */
  public void saveGame(Game game, String fileName);
}