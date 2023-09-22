package test.nz.ac.wgtn.swen225.lc.fuzz;

import org.junit.jupiter.api.Test;

/**
 * Provides fuzz testing by providing semi-random inputs to the App module in order to provoke
 * errors.
 */
public class FuzzTest {

  //Number of random movement tests to carry out on each level.
  private final int iterations = 100;
  //Number of random steps to take on each level.
  private final int steps = 100;

  /**
   * Tests on level 1.
   */
  @Test
  public void test1() {
    // TODO random movement testing
    // TODO weighted movement testing
    // TODO pause/resume testing (in quick succession, make inputs while paused)
    // TODO setup/teardown, start from scratch repeatedly.
    for (int i = 0; i < iterations; i++) {
      //TODO Setup
      AppEnvoy envoy = getNewEnvoy();
      for (int step = 0; step < steps; step++) {
        //TODO randomisation
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
      }
    }
  }

  /**
   * Tests on level 2. Level 2 has much added complexity due to unknown bonus actors.
   */
  @Test
  public void test2() {
    for (int i = 0; i < iterations; i++) {
      //TODO Setup
      for (int step = 0; step < steps; step++) {

      }
    }
  }

  private AppEnvoy getNewEnvoy() {
    return new MockEnvoy();
  }
}
