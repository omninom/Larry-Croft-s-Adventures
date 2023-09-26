package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Level;

/**
 * Interface for loading Levels as part of new or loaded Game.
 */
public interface LevelLoader {
  public Level loadLevel(int levelNumber);
}