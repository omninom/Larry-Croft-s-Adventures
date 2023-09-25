package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.*;

import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

/**
 * The Renderer for the game.
 *
 * author: Leory Xue 300607821
 */
public class Renderer extends JPanel {
  private static final int GRID_SIZE = 9;
  private static final Color PLAYER_COLOR = Color.RED;
  private int playerX; // Column position of the player
  private int playerY; // Row position of the player
  private static final HashMap<TileType, Color> TILE_COLORS = new HashMap<>();    // Map of tile types to colors

  static {
    TILE_COLORS.put(TileType.WALL, Color.BLACK);
    TILE_COLORS.put(TileType.FREE, Color.WHITE);
    TILE_COLORS.put(TileType.KEY, Color.YELLOW);
    TILE_COLORS.put(TileType.LOCKED_DOOR, Color.GRAY);
    TILE_COLORS.put(TileType.INFO, Color.BLUE);
    TILE_COLORS.put(TileType.TREASURE, Color.ORANGE);
    TILE_COLORS.put(TileType.EXIT_LOCK, Color.PINK);
    TILE_COLORS.put(TileType.EXIT, Color.GREEN);

  }

  private final Maze maze;

  /**
   * Constructor for the renderer.
   *
   * @param maze the maze to render
   */
  public Renderer(Maze maze) {
    this.maze = maze;
    setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
    initializeGrid();
    drawPlayer();
  }

  /**
   * Initialize the grid.
   */
  private void initializeGrid() {
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        JPanel cell = new JPanel();
        cell.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        cell.setLayout(new BorderLayout()); // Use BorderLayout for each cell
        add(cell);
      }
    }
  }

  /**
   * Get the color of the tile.
   *
   * @param type the type of the tile
   * @return the color of the tile
   */
  private Color getTileColor(TileType type) {
    return TILE_COLORS.getOrDefault(type, Color.WHITE); // Default to white for unknown tile types
  }


  /**
   * Update the renderer.
   */
  public void updateRenderer() {
    // Clear the current player position
    clearPlayer();

    // Update the player's position
    playerX = maze.getChap().getPosition().x;
    playerY = maze.getChap().getPosition().y;

    // Draw the player at the new position
    drawPlayer();

    // Repaint the grid with updated tile types/colors
    for (int i = 0; i < GRID_SIZE; i++) {
      for (int j = 0; j < GRID_SIZE; j++) {
        JPanel cell = (JPanel) getComponentAtPosition(i, j);
        assert cell != null;
        //cell.setBackground(getTileColor(maze.getTileType(i, j)));
      }
    }
    repaint();
  }


  private void drawPlayer() {
    // Create a label with a circular background
    JLabel playerLabel = new JLabel("P");
    playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    playerLabel.setVerticalAlignment(SwingConstants.CENTER);
    playerLabel.setForeground(Color.WHITE);
    playerLabel.setOpaque(true);
    playerLabel.setBackground(PLAYER_COLOR);
    playerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

    // Get the panel at the player's position
    JPanel cell = (JPanel) getComponentAtPosition(playerY, playerX);
    assert cell != null;
    cell.add(playerLabel, BorderLayout.CENTER);
  }

  private void clearPlayer() {
    // Remove the player label from the current position
    JPanel cell = (JPanel) getComponentAtPosition(playerY, playerX);
    if (cell != null) {
      cell.removeAll();
      //cell.setBackground(getTileColor(maze.getTileType(playerY, playerX)));
    }
  }

  /**
   * Get the component at the given position.
   *
   * @param row the row of the component
   * @param col the column of the component
   * @return the component at the given position
   */
  private Component getComponentAtPosition(int row, int col) {
    int componentIndex = (row * GRID_SIZE) + col;
    if (componentIndex >= 0 && componentIndex < getComponentCount()) {
      return getComponent(componentIndex);
    }
    return null;
  }

  //testing purposes
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Maze maze = new Maze(9, 9); // Create a Maze instance
      JFrame frame = new JFrame("Renderer");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new Renderer(maze));
      frame.setSize(500, 500);
      frame.setResizable(false);
      frame.setVisible(true);
    });
  }
}
