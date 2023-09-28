package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Renderer extends JPanel {
  private static final int GRID_SIZE = 9;
  private static final Color PLAYER_COLOR = Color.RED;
  private static final HashMap<TileType, Color> TILE_COLORS = new HashMap<>();

  static {
    TILE_COLORS.put(TileType.WALL, Color.GRAY);
    TILE_COLORS.put(TileType.FREE, Color.WHITE);
    TILE_COLORS.put(TileType.KEY, Color.BLACK);
    TILE_COLORS.put(TileType.LOCKED_DOOR, Color.GRAY);
    TILE_COLORS.put(TileType.INFO, Color.MAGENTA);
    TILE_COLORS.put(TileType.TREASURE, Color.ORANGE);
    TILE_COLORS.put(TileType.EXIT_LOCK, Color.PINK);
    TILE_COLORS.put(TileType.EXIT, Color.BLUE);
  }

  private final Maze maze;

  public Renderer(Maze maze) {
    this.maze = maze;
    setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
    initializeGrid();
    updateRenderer();
  }

  private void initializeGrid() {
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        JPanel cell = new JPanel();
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(cell);
      }
    }
  }

  public void updateRenderer() {
    removeAll(); // Clear the existing grid

    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        JPanel cell = new JPanel();
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Tile tile = maze.getTiles()[row][col];
        cell.setBackground(TILE_COLORS.get(tile.getType()));

        if (maze.getChap().getPosition().equals(new Point(row, col))) {
          cell.setBackground(PLAYER_COLOR);
          JLabel chapLabel = new JLabel("Chap");
          chapLabel.setForeground(Color.WHITE);
          chapLabel.setHorizontalAlignment(JLabel.CENTER);
          chapLabel.setVerticalAlignment(JLabel.CENTER);
          cell.add(chapLabel);
        }
        add(cell);
      }
    }

    revalidate(); // Refresh the panel
    repaint();
  }


}
