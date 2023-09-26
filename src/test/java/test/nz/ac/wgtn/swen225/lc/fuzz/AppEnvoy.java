package test.nz.ac.wgtn.swen225.lc.fuzz;

import nz.ac.wgtn.swen225.lc.app.App;
import nz.ac.wgtn.swen225.lc.app.AppInput;

/**
 * The real-deal Envoy that deals with App.
 *
 * @author Jebadiah Dudfield 300629357
 */
public class AppEnvoy implements GameEnvoy {

  App app;
  final int level;

  AppEnvoy(int level) {
    this.level = level;
    reset();
  }

  @Override
  public void reset() {
    app = new App();
    app.newGame(level);
  }

  @Override
  public void moveUp() {
    app.handleInput(AppInput.MOVE_UP);
  }

  @Override
  public void moveDown() {
    app.handleInput(AppInput.MOVE_DOWN);

  }

  @Override
  public void moveLeft() {
    app.handleInput(AppInput.MOVE_LEFT);

  }

  @Override
  public void moveRight() {
    app.handleInput(AppInput.MOVE_RIGHT);

  }

  @Override
  public void pause() {
    app.handleInput(AppInput.PAUSE);
  }

  @Override
  public void unpause() {
    app.handleInput(AppInput.UNPAUSE);
  }

  @Override
  public boolean isStopped() {
    return false;
  }

  @Override
  public String printSuccessMessage() {
    return "Not Implemented Yet";
  }
}
