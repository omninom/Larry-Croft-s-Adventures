package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

/**
 * Interface for saving in-progress Games.
 */
public interface GameSaver {
  /**
   * Saves a game to file.
   *
   * @param maze     The game maze instance to be saved.
   * @param fileName The name of the file from which the game is to be loaded.
   */
  public void saveGame(Maze maze, String fileName);
}