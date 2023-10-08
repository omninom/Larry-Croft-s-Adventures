package test.nz.ac.wgtn.swen225.lc.fuzz;

/**
 * Defines ways in which the FuzzTest class can interact with the game. Introduced to assist with
 * refactoring and allow for development of the FuzzTest module before the App module is finished.
 * TODO Introduce new methods to retrieve information for smarter testing.
 *
 * @author Jebadiah Dudfield 300629357
 */
public interface GameEnvoy {

  boolean reset();

  boolean moveUp();

  boolean moveDown();

  boolean moveLeft();

  boolean moveRight();

  boolean pause();

  boolean unpause();

  boolean isStopped();

  String printSuccessMessage();
}


