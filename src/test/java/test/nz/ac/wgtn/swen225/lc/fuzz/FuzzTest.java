package test.nz.ac.wgtn.swen225.lc.fuzz;

import org.junit.jupiter.api.Test;

/**
 * Provides fuzz testing by providing semi-random inputs to the App module in order to provoke
 * errors.
 */
public class FuzzTest {

  //Number of random movement tests to carry out on each level.
  private static final int iterations = 100;
  //Number of random steps to take on each level.
  private static final int steps = 100;
  //Number of pause/unpause cycles to execute in one wave.
  private static final int pauses = 1;

  /**
   * Tests on level 1.
   */
  @Test
  public void test1() {
    testLevel(1);
  }

  /**
   * Tests on level 2. Level 2 has much added complexity due to unknown bonus actors.
   */
  @Test
  public void test2() {
    testLevel(2);
  }

  /**
   * Tests an arbitrary level, if it exists, otherwise throws an error.
   *
   * @param level the index of the level we wish to test.
   */
  public void testLevel(int level) {
    // random movement testing
    // TODO intelligent movement testing
    // pause/resume testing (in quick succession, make inputs while paused)
    // TODO setup/teardown, level loading and detection of success.
    for (int i = 0; i < iterations; i++) {
      //TODO Setup
      GameEnvoy envoy = getNewEnvoy(level);
      envoy.reset();
      for (int step = 0; step < steps; step++) {
        //5% chance of pausespam test.
        if (Math.random() < 0.05) {
          for (int j = 0; j < pauses; j++) {
            envoy.pause();
            envoy.unpause();
          }
        }
        switch ((int) Math.floor(Math.random() * 4)) {
          case 0:
            envoy.moveUp();
            break;
          case 1:
            envoy.moveDown();
            break;
          case 2:
            envoy.moveLeft();
            break;
          case 3:
            envoy.moveRight();
            break;
          default:
            throw new IllegalArgumentException();
        }
        if (envoy.isStopped()) {
          envoy.printSuccessMessage();
          return;
        }
      }
    }
  }

  /**
   * Generates a GameEnvoy for the tests to use to interact with the App.
   *
   * @param level the level to load. An error should be thrown if this is an invalid level.
   * @return An GameEnvoy.
   */
  private GameEnvoy getNewEnvoy(int level) {
    // TODO check level is real.
    return new AppEnvoy(level);
  }
}
