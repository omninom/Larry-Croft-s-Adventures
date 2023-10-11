package test.nz.ac.wgtn.swen225.lc.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import nz.ac.wgtn.swen225.lc.domain.Chap;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.EnemyActor;
import nz.ac.wgtn.swen225.lc.domain.Maze;
import nz.ac.wgtn.swen225.lc.domain.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests domain's BuildNewLevel method, which is used for loading levels and gamestates.
 *
 * @author Riley West (300608942).
 * @author Jebadiah (300629357).
 */
public class MazeCreationTests {

  private TileType[][] insertionGrid;
  private Domain domain;

  /**
   * Setup function.
   */
  @BeforeEach
  public void setUp() {
    domain = new Domain();
    insertionGrid = new Maze(7, 9).getTiles();
  }

  /**
   * Tests the creation of a maze without enemies and a single wall.
   */
  @Test
  public void testValidCreation() {
    insertionGrid[0][3] = TileType.WALL;
    Chap ourChap = new Chap(0, 1);
    List<EnemyActor> enemies = new ArrayList<>();
    Domain d = new Domain();
    Assertions.assertEquals(new Point(0, 0), d.getChap().getPosition());
    Assertions.assertTrue(d.getEnemyActorList().isEmpty());
    d.buildNewLevel(insertionGrid, ourChap, enemies, "Short");
    assert d.getTiles() == insertionGrid;
    Assertions.assertEquals(new Point(0, 1), d.getChap().getPosition());
  }

  /**
   * Tests the creation of various invalid mazes.
   */
  @Test
  public void testInvalidCreation() {
    insertionGrid[0][3] = TileType.WALL;
    Chap ourChap;
    ourChap = new Chap(0, 1);
    List<EnemyActor> enemies;
    enemies = new ArrayList<>();
    Domain d;
    d = new Domain();

    TileType[][] badGrid = new TileType[3][];
    for (int i = 0; i < 2; i++) {
      badGrid[i] = new TileType[3];
      for (int j = 0; j < 3; j++) {
        badGrid[i][j] = TileType.FREE;
      }
    }
    badGrid[2] = new TileType[1];
    badGrid[2][0] = TileType.EXIT;
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> d.buildNewLevel(badGrid, ourChap, enemies, "Y"));

    Chap badChap = new Chap(99, 99);
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> d.buildNewLevel(insertionGrid, badChap, enemies, "Y"));
  }
}
