package test.nz.ac.wgtn.swen225.lc.fuzz;

/**
 * Defines ways in which the FuzzTest class can interact with the game. Introduced to assist with
 * refactoring and allow for development of the FuzzTest module before the App module is finished.
 * TODO Introduce new methods to retrieve information for smarter testing.
 */
public interface GameEnvoy {

  public void reset();

  public void moveUp();

  public void moveDown();

  public void moveLeft();

  public void moveRight();

  public void pause();

  public void unpause();

  public boolean isStopped();

  public String printSuccessMessage();
}


