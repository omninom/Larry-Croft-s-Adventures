package test.nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {
  private Domain domain;

  @BeforeEach
  public void setUp() {
    domain = new Domain();
  }

  @Test
  public void testMoveChapValidMove() {
    // Test moving Chap to a valid position
    assertDoesNotThrow(() -> domain.moveChap(Direction.RIGHT));
    assertEquals(1, domain.getChap().getPosition().x);
    assertEquals(0, domain.getChap().getPosition().y);
  }

  @Test
  public void testMoveChapInvalidMove() {
    // Test moving Chap out of bounds
    assertThrows(IllegalArgumentException.class, () -> domain.moveChap(Direction.LEFT));
  }

  @Test
  public void testMoveChapFailedState() {
    domain.setFailed(true);
    // Test moving Chap when in a failed state
    domain.moveChap(Direction.RIGHT);

    assertThrows(IllegalStateException.class, () -> domain.moveChap(Direction.RIGHT));
  }

  @Test
  public void testMoveChapWonState() {
    domain.setWon(true);
    // Test moving Chap when in a failed state
    domain.moveChap(Direction.RIGHT);

    assertThrows(IllegalStateException.class, () -> domain.moveChap(Direction.RIGHT));
  }

  @Test
  public void testGetWon() {
    Maze tester = domain.getMaze();
    tester.setTile(1, 0, TileType.EXIT);
    domain.setMaze(tester);

    while (!domain.getWon()) {
      domain.moveChap(Direction.RIGHT);
    }
    assertTrue(domain.getWon());
  }

  @Test
  public void pickupTest() {
    Maze tester = domain.getMaze();
    tester.setTile(1, 0, TileType.BLUE_KEY);
    domain.setMaze(tester);

    domain.moveChap(Direction.RIGHT);
    assertTrue(domain.getChap().hasKey(TileType.BLUE_KEY));
  }

  @Test
  public void doorUnlockTest() {
    Maze tester = domain.getMaze();
    tester.setTile(1, 0, TileType.BLUE_KEY);
    tester.setTile(2, 0, TileType.BLUE_DOOR);
    domain.setMaze(tester);

    domain.moveChap(Direction.RIGHT);
    domain.moveChap(Direction.RIGHT);
    assertFalse(domain.getChap().hasKey(TileType.BLUE_KEY));
  }

  @Test
  public void treasurePickupTest() {
    Maze test = domain.getMaze();
    test.setTile(1, 0, TileType.TREASURE);
    domain.setMaze(test);
    int inittreasure = domain.getTreasureRemaining();

    domain.moveChap(Direction.RIGHT);
    assertEquals(inittreasure - 1, domain.getTreasureRemaining());
  }
}
