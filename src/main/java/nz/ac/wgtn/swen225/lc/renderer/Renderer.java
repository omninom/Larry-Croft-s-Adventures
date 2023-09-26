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

  /**
   * Constructor for the renderer.
   *
   * @param maze the maze to render
   */
  public Renderer(Maze maze) {
    this.maze = maze;
    //createTestMaze();
    setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
    initializeGrid();
    updateRenderer();
  }

  /**
   * Initialize the grid.
   */
  private void initializeGrid() {
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        JPanel cell = new JPanel();
        cell.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
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
    drawTile(playerX, playerY, "Chap", PLAYER_COLOR);

    // Repaint the grid with updated tile types/colors
      Tile[][] tiles = maze.getTiles();
      for (int row = 0; row < GRID_SIZE; row++) {
          for (int col = 0; col < GRID_SIZE; col++) {
              JPanel cell = (JPanel) getComponentAtPosition(row, col);
              assert cell != null;
              cell.setBackground(getTileColor(tiles[row][col].getType()));
              if (tiles[row][col].getType() == TileType.KEY) {
                  drawTile(row, col, "Key", getTileColor(tiles[row][col].getType()));
              }
              if (tiles[row][col].getType() == TileType.LOCKED_DOOR) {
                  drawTile(row, col, "Lock", getTileColor(tiles[row][col].getType()));
              }
              if (tiles[row][col].getType() == TileType.INFO) {
                  drawTile(row, col, "Info", getTileColor(tiles[row][col].getType()));
              }
          }
      }




      /* OLD CODE
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        JPanel cell = (JPanel) getComponentAtPosition(row, col);
        assert cell != null;
        cell.setBackground(getTileColor(maze.getTileType(row, col)));
        if (maze.getTileType(row, col) == TileType.KEY) {
          drawTile(row, col, "Key", getTileColor(maze.getTileType(row, col)));
        }
        if (maze.getTileType(row, col) == TileType.LOCKED_DOOR) {
          drawTile(row, col, "Lock", getTileColor(maze.getTileType(row, col)));
        }
        if (maze.getTileType(row, col) == TileType.INFO) {
          drawTile(row, col, "Info", getTileColor(maze.getTileType(row, col)));
        }
      }
    }

       */

    repaint();
  }


  /**
   * Draw a tile at the given position.
   *
   * @param row   the row of the tile
   * @param col   the column of the tile
   * @param label the label of the tile
   * @param color the color of the tile
   */
  private void drawTile(int row, int col, String label, Color color) {
    // Create a label with a background
    JLabel playerLabel = new JLabel(label);
    playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    playerLabel.setVerticalAlignment(SwingConstants.CENTER);
    playerLabel.setForeground(Color.WHITE);
    playerLabel.setOpaque(true);
    playerLabel.setBackground(color);
    playerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

    // Get the panel at the player's position
    JPanel cell = (JPanel) getComponentAtPosition(row, col);
    assert cell != null;
    cell.add(playerLabel, BorderLayout.CENTER);
  }

  private void clearPlayer() {
    // Remove the player label from the current position
    JPanel cell = (JPanel) getComponentAtPosition(playerY, playerX);
    if (cell != null) {
      cell.removeAll();
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


  /**
   * Create a test maze. Purely for testing purposes of the renderer.
   */
    /*
  private void createTestMaze() {
    // Set some tiles in the maze to create a test configuration

    //set all tiles to free tiles
    for (int i = 0; i < GRID_SIZE; i++) {
      for (int j = 0; j < GRID_SIZE; j++) {
        maze.setTile(i, j, TileType.FREE);
      }
    }
    //maze.setTile(0, 0, TileType.FREE);
    maze.setTile(0, 1, TileType.TREASURE);
    //maze.setTile(0, 2, TileType.FREE);
    maze.setTile(0, 3, TileType.WALL);
    maze.setTile(0, 4, TileType.EXIT);
    maze.setTile(0, 5, TileType.WALL);
    //maze.setTile(0, 6, TileType.FREE);
    maze.setTile(0, 7, TileType.TREASURE);
    //maze.setTile(0, 8, TileType.FREE);
    maze.setTile(1, 0, TileType.WALL);
    maze.setTile(1, 1, TileType.WALL);
    maze.setTile(1, 2, TileType.LOCKED_DOOR);  //GREEN LOCK
    maze.setTile(1, 3, TileType.WALL);
    maze.setTile(1, 4, TileType.EXIT_LOCK);
    maze.setTile(1, 5, TileType.WALL);
    maze.setTile(1, 6, TileType.LOCKED_DOOR);  //GREEN LOCK
    maze.setTile(1, 7, TileType.WALL);
    maze.setTile(1, 8, TileType.WALL);
    maze.setTile(2, 1, TileType.LOCKED_DOOR);   //CYAN LOCK
    maze.setTile(2, 7, TileType.LOCKED_DOOR);    //RED LOCK
    maze.setTile(3, 1, TileType.WALL);
    maze.setTile(3, 2, TileType.KEY);      //CYAN KEY
    maze.setTile(3, 4, TileType.INFO);
    maze.setTile(3, 6, TileType.KEY);     //RED KEY
    maze.setTile(3, 7, TileType.WALL);
    maze.setTile(4, 0, TileType.WALL);
    maze.setTile(4, 1, TileType.WALL);
    maze.setTile(4, 2, TileType.TREASURE);
    maze.setTile(4, 6, TileType.TREASURE);
    maze.setTile(4, 7, TileType.WALL);
    maze.setTile(4, 8, TileType.WALL);
    maze.setTile(5, 1, TileType.WALL);
    maze.setTile(5, 2, TileType.KEY);    //CYAN KEY
    maze.setTile(5, 6, TileType.KEY);   //RED KEY
    maze.setTile(5, 7, TileType.WALL);
    maze.setTile(6, 1, TileType.LOCKED_DOOR);    //RED LOCK
    maze.setTile(6, 4, TileType.TREASURE);
    maze.setTile(6, 7, TileType.LOCKED_DOOR);   //CYAN LOCK
    maze.setTile(7, 0, TileType.WALL);
    maze.setTile(7, 1, TileType.WALL);
    maze.setTile(7, 2, TileType.WALL);
    maze.setTile(7, 3, TileType.LOCKED_DOOR);   //YELLOW LOCK
    maze.setTile(7, 4, TileType.WALL);
    maze.setTile(7, 5, TileType.LOCKED_DOOR);   //YELLOW LOCK
    maze.setTile(7, 6, TileType.WALL);
    maze.setTile(7, 7, TileType.WALL);
    maze.setTile(7, 8, TileType.WALL);
    maze.setTile(8, 1, TileType.WALL);
    maze.setTile(8, 4, TileType.WALL);
    maze.setTile(8, 7, TileType.WALL);
  }

     */

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
