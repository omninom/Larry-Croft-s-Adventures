package test.nz.ac.wgtn.swen225.lc.fuzz;

/**
 * Defines ways in which the FuzzTest class can interact with the game. Introduced to assist with
 * refactoring and allow for development of the FuzzTest module before the App module is finished.
 */
public interface AppEnvoy {

  void reset();

  void moveUp();

  void moveDown();

  void moveLeft();

  void moveRight();

  void pause();

  void unpause();

  void isStopped();

  void printSuccessMessage();
}
