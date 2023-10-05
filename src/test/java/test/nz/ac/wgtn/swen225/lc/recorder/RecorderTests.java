package test.nz.ac.wgtn.swen225.lc.recorder;

import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This is a collection of tests for the Recorder class.
 * It will test the methods in the Recorder class to ensure that they are working as expected.
 * It will test the following methods:
 * TODO add methods to test
 *
 * @author Neeraj Patel (300604056).
 */
public class RecorderTests {
  /**
   * Test that the JUnit tests are working.
   */
  @Test
  void test_Junit_Working() {
    Assertions.assertTrue(true);
  }


  /**
   * Test the initialisation and states of startRecording() method.
   */
  @Test
  void test_startRecording() {
    Recorder test = new Recorder();
    String level = "1";

    Assertions.assertEquals("WAITING", test.getState());

    test.setRecording(level);
    Assertions.assertEquals("RECORDING", test.getState());
  }

  /**
   * Test the adding elements of addToRecording() method.
   */
  @Test
  void test_addToRecording() {
    Recorder test = new Recorder();
    String level = "1";
    int sequenceNumber = 0;

    test.setRecording(level);

    test.addToRecording("PLAYER | MOVE_LEFT");
    test.addToRecording("ACTOR | MOVE_LEFT");
    test.addToRecording("ACTOR | MOVE_RIGHT");
    test.addToRecording("PLAYER | MOVE_RIGHT");

    if (test.getCurrentRecording().size() != 5) {
      Assertions.fail("The size of the current recording is not 4");
    }
    for (int i = 0; i < test.getCurrentRecording().size(); i++) {
      Assertions.assertEquals(sequenceNumber,
              test.getCurrentRecording().get(i).getSequenceNumber());
      sequenceNumber++;
    }

  }



  /**
   * Test that the stepByStepReplay() method is working as expected.
   * This will test that the stepByStepReplay() method is working as expected.
   */
  @Test
  void test_stepByStepReplay() {
    // TODO: Implement
  }

  /**
   * Test that the autoReplay() method is working as expected.
   * This will test that the autoReplay() method is working as expected.
   */
  @Test
  void test_autoReplay() {
    // TODO: Implement
  }

  /**
   * Test that the loadGame() method is working as expected.
   * This will test that the loadGame() method is working as expected.
   */
  @Test
  void test_loadGame() {
    // TODO: Implement
  }

  /**
   * Test that the setIsRecording() method is working as expected.
   * This will test that the setIsRecording() method is working as expected.
   */
  @Test
  void test_setIsRecording() {
    // TODO: Implement
  }

  /**
   * Test that the getIsRecording() method is working as expected.
   * This will test that the getIsRecording() method is working as expected.
   */
  @Test
  void test_getIsRecording() {
    // TODO: Implement
  }





}

