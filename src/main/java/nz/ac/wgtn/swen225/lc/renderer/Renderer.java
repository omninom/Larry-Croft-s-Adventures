package main.java.nz.ac.wgtn.swen225.lc.renderer;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    private static final int GRID_SIZE = 9;
    private static final Color PLAYER_COLOR = Color.RED;
    private int playerX; // Column position of the player
    private int playerY; // Row position of the player

    public Renderer() {
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
            frame.getContentPane().add(new Renderer());
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
