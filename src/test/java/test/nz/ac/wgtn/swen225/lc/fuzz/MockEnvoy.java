package java.test.nz.ac.wgtn.swen225.lc.fuzz;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock envoy used before App is developed enough for a true envoy.
 */
public class MockEnvoy implements AppEnvoy {

  public void reset(){

  }

  List<int[]> positions = new ArrayList<>();

  MockEnvoy() {
    positions.add(new int[] {0, 0});
  }

  @Override
  public void moveUp() {
    int[] oldP = positions.get(positions.size() - 1);
    int[] newP = {oldP[0], oldP[1] - 1};
    positions.add(newP);
  }

  @Override
  public void moveDown() {
    int[] oldP = positions.get(positions.size() - 1);
    int[] newP = {oldP[0], oldP[1] + 1};
    positions.add(newP);
  }

  @Override
  public void moveLeft() {
    int[] oldP = positions.get(positions.size() - 1);
    int[] newP = {oldP[0] - 1, oldP[1]};
    positions.add(newP);
  }

  @Override
  public void moveRight() {
    int[] oldP = positions.get(positions.size() - 1);
    int[] newP = {oldP[0] + 1, oldP[1]};
    positions.add(newP);
  }

  @Override
  public void pause() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void unpause() {
    throw new UnsupportedOperationException();

  }

  @Override
  public void isStopped() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void printSuccessMessage() {
    int minX = 0;
    int minY = 0;
    int maxX = 0;
    int maxY = 0;
    for (int[] pos : positions) {
      if (pos[0] < minX) {
        minX = pos[0];
      } else if (pos[0] > maxX) {
        maxX = pos[0];
      }
      if (pos[1] < minY) {
        minY = pos[1];
      } else if (pos[1] > maxY) {
        maxY = pos[1];
      }
    }
    maxX = maxX - minX;
    maxY = maxY - minY;
    for (int[] pos : positions) {
      pos[0] = pos[0] - minX;
      assert pos[0] >= 0;
      pos[1] = pos[1] - minY;
      assert pos[1] >= 0;
    }
    char[][] outArr = new char[maxY][maxX];
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        outArr[y][x] = '.';
      }
    }
    for (int[] pos : positions) {
      outArr[pos[1]][pos[0]] = '#';
    }
    StringBuilder out = new StringBuilder();
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        out.append(outArr[y][x]);
      }
      out.append('\n');
    }
    System.out.println(out);
  }
}
