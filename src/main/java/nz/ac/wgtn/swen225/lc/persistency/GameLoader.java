package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Game;

/**
 * Interface for loading Saved Games.
 */
public interface GameLoader {
  /**
   * Loads a game from a file.
   *
   * @param fileName The name of the file from which the game is to be loaded.
   */
  public Game loadGame(String fileName);
}