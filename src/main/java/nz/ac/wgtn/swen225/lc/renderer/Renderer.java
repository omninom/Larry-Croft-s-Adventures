package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Renderer extends JPanel {
  private static final int GRID_SIZE = 9;
  private static final BufferedImage chapSprite = Sprite.chap.sprite;
  private static final HashMap<TileType, Sprite> TILE_SPRITES = new HashMap<>();

  static {
    TILE_SPRITES.put(TileType.WALL, Sprite.wallTile);
    TILE_SPRITES.put(TileType.FREE, Sprite.freeTile1);
    TILE_SPRITES.put(TileType.LOCKED_DOOR, Sprite.redLock);
    TILE_SPRITES.put(TileType.TREASURE, Sprite.treasure);
    TILE_SPRITES.put(TileType.INFO, Sprite.info);
    TILE_SPRITES.put(TileType.KEY, Sprite.redKey);
    TILE_SPRITES.put(TileType.EXIT, Sprite.exit);
    TILE_SPRITES.put(TileType.EXIT_LOCK, Sprite.exitLock);
  }

  private final Maze maze;

  public Renderer(Maze maze) {
    this.maze = maze;
    setPreferredSize(new Dimension(GRID_SIZE * 50, GRID_SIZE * 50)); // Adjust the size as needed
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int cellWidth = getWidth() / GRID_SIZE;
    int cellHeight = getHeight() / GRID_SIZE;

    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        Tile tile = maze.getTiles()[row][col];
        Sprite sprite = TILE_SPRITES.get(tile.getType());

        if (sprite != null) {
          BufferedImage spriteImage = sprite.sprite;
          g.drawImage(spriteImage, col * cellWidth, row * cellHeight, cellWidth, cellHeight, this);
        }

        if (maze.getChap().getPosition().equals(new Point(row, col))) {
          g.drawImage(chapSprite, col * cellWidth, row * cellHeight, cellWidth, cellHeight, this);
        }
      }
    }
  }

  public void updateRenderer() {
    repaint();
  }
}