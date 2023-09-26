package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Maze;

/**
 * Interface for loading Levels as part of new or loaded Game.
 */
public interface LevelLoader {
  public Maze loadLevel(int levelNumber);
}