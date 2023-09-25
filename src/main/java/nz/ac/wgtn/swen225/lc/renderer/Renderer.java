package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.*;

import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

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

    private Board maze; // We need something like this to get the level to render

    public Renderer(Board maze) {       // Constructor would need to take in a level to render
        this.maze = maze;
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        initializeGrid();
        playerX = GRID_SIZE / 2; // Put player in center for now
        playerY = GRID_SIZE / 2;
        drawPlayer();
    }

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

    private Color getTileColor(TileType type) {
        return TILE_COLORS.getOrDefault(type, Color.WHITE); // Default to white for unknown tile types
    }

    // Update the renderer when the game state changes, e.g., after player moves
    public void updateRenderer(Board updatedGameBoard) {
        this.maze = updatedGameBoard;
        // Repaint the grid with updated tile types/colors
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel cell = (JPanel) getComponentAtPosition(i, j);
                //cell.setBackground( somehow get the tile type from the level );
            }
        }
        // Remove the player from the old position
        JPanel oldCell = (JPanel) getComponentAtPosition(playerY, playerX);
        assert oldCell != null;
        oldCell.removeAll();
        // Draw the player at the new position
        drawPlayer();

        repaint();
    }

    // Method to draw the player
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

    // Method to get the component at a specific grid position
    private Component getComponentAtPosition(int row, int col) {
        int componentIndex = (row * GRID_SIZE) + col;
        if (componentIndex >= 0 && componentIndex < getComponentCount()) {
            return getComponent(componentIndex);
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Renderer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.getContentPane().add(new Renderer());
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
