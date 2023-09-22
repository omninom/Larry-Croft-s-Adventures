package java.test.nz.ac.wgtn.swen225.lc.fuzz;

import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;

/**
 * Provides fuzz testing by providing semi-random inputs to the App module in order to provoke errors.
 */
public class FuzzTest {

  /**
   * Tests on level 1.
   */
  @Test
  public void test1(){
    // TODO random movement testing
    // TODO weighted movement testing
    // TODO pause/resume testing (in quick succession, make inputs while paused)
    // TODO setup/teardown, start from scratch repeatedly.
  }

  /**
   * Tests on level 2.
   * Level 2 has much added complexity due to unknown bonus actors.
   */
  @Test
  public void test2(){}
}
