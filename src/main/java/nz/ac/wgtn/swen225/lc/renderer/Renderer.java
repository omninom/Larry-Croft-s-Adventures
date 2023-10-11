package nz.ac.wgtn.swen225.lc.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import nz.ac.wgtn.swen225.lc.domain.Direction;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.DomainObserver;
import nz.ac.wgtn.swen225.lc.domain.EventType;
import nz.ac.wgtn.swen225.lc.domain.TileType;

/**
 * Renderer.java
 * Handles the rendering of the game.
 *
 * @author Leory Xue (300607821)
 */
public class Renderer extends JPanel implements DomainObserver {
  private static final HashMap<Direction, Sprite> CHAP_SPRITES = new HashMap<>();
  private static final HashMap<Direction, Sprite> ENEMY_SPRITES = new HashMap<>();
  private static final HashMap<TileType, Sprite> TILE_SPRITES = new HashMap<>();
  private static final int FOCUS_SIZE = 9;
  private int gridSize = 0;

  static {
    TILE_SPRITES.put(TileType.WALL, Sprite.wallTile);
    TILE_SPRITES.put(TileType.FREE, Sprite.freeTile1);
    TILE_SPRITES.put(TileType.RED_DOOR, Sprite.redLock);
    TILE_SPRITES.put(TileType.RED_KEY, Sprite.redKey);
    TILE_SPRITES.put(TileType.BLUE_DOOR, Sprite.blueLock);
    TILE_SPRITES.put(TileType.BLUE_KEY, Sprite.blueKey);
    TILE_SPRITES.put(TileType.TREASURE, Sprite.treasure);
    TILE_SPRITES.put(TileType.INFO, Sprite.info);
    TILE_SPRITES.put(TileType.EXIT, Sprite.exit);
    TILE_SPRITES.put(TileType.EXIT_LOCK, Sprite.exitLock);

    CHAP_SPRITES.put(Direction.UP, Sprite.chapU);
    CHAP_SPRITES.put(Direction.DOWN, Sprite.chapD);
    CHAP_SPRITES.put(Direction.LEFT, Sprite.chapL);
    CHAP_SPRITES.put(Direction.RIGHT, Sprite.chapR);

    ENEMY_SPRITES.put(Direction.UP, Sprite.actorU);
    ENEMY_SPRITES.put(Direction.DOWN, Sprite.actorD);
    ENEMY_SPRITES.put(Direction.LEFT, Sprite.actorL);
    ENEMY_SPRITES.put(Direction.RIGHT, Sprite.actorR);
  }

  private final Domain domain;
  private final Sound sound = new Sound();
  private final ArrayList<TileType> chapInventory = new ArrayList<>();


  /**
   * Constructor for the Renderer class.
   *
   * @param domain The domain to be rendered.
   */
  public Renderer(Domain domain) {
    this.domain = domain;
    domain.addObserver(this);
    sound.playBackgroundMusic();
    gridSize = domain.getTiles().length;
    setPreferredSize(new Dimension(FOCUS_SIZE * 50, (FOCUS_SIZE + 1) * 50 + 25)); //Added 25 for extra space for inventory
  }


  /**
   * Overridden paintComponent method to draw the game.
   *
   * @param g The graphics object.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Set the background color to freeTile0
    setBackground(new Color(152, 106, 147, 255));

    int cellWidth = getWidth() / FOCUS_SIZE;
    // The height of each cell is the height of the panel divided by the number of rows, we also minus 25 to allow space for the inventory
    int cellHeight = (getHeight()-25) / (FOCUS_SIZE + 1); // Increase the number of "rows" to 10 this allows spacing for inventory

    // Calculate the top-left cell coordinates of the focus area based on the player's position
    int focusTopRow = Math.max(0, domain.getChap().getPosition().y - (FOCUS_SIZE / 2));
    int focusLeftCol = Math.max(0, domain.getChap().getPosition().x - (FOCUS_SIZE / 2));

    // Loop through the focus area and draw tiles
    for (int row = focusTopRow; row < focusTopRow + (FOCUS_SIZE) && row < gridSize; row++) {
      for (int col = focusLeftCol; col < focusLeftCol + FOCUS_SIZE && col < gridSize; col++) {
        TileType tile = domain.getTiles()[row][col];
        Sprite sprite = TILE_SPRITES.get(tile);

        if (sprite != null) {
          BufferedImage spriteImage = sprite.sprite;
          int drawX = (col - focusLeftCol) * cellWidth;
          int drawY = (row - focusTopRow) * cellHeight;
          g.drawImage(spriteImage, drawX, drawY, cellWidth, cellHeight, this);
        }
      }
    }

    if (domain.getFailed()) {
      // Draw a black screen over the game and display the game over message
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(Color.WHITE);
      g.drawString("Game Over", (getWidth() / 2) - (g.getFontMetrics().stringWidth("Game Over") / 2), getHeight() / 2);
    }

    // If the player is on an info tile, display a random info message FOR NOW: NEED INFO FROM DOMAIN
    if (domain.isOnInfo()) {
      String infoMessage = "Move Larry with the arrow keys";
      g.setColor(Color.WHITE);
      //draw the message in the top middle of the screen
      g.setFont(new Font("Dialog", Font.BOLD, 20));
      //give font outline
      g.drawString(infoMessage, (getWidth() / 2) - (g.getFontMetrics().stringWidth(infoMessage) / 2), getHeight() / 4);
    }


    // Draw the player character
    int chapDrawX = (domain.getChap().getPosition().x - focusLeftCol) * cellWidth;
    int chapDrawY = (domain.getChap().getPosition().y - focusTopRow) * cellHeight;
    Direction chapDirection = domain.getChap().getDirection();
    Sprite chapSprite = CHAP_SPRITES.get(chapDirection);
    g.drawImage(chapSprite.sprite, chapDrawX, chapDrawY, cellWidth, cellHeight, this);

    // Draw the enemy characters
    for (int i = 0; i < domain.getEnemyActorList().size(); i++) {
      int enemyDrawX = (domain.getEnemyActorList().get(i).getPosition().x - focusLeftCol) * cellWidth;
      int enemyDrawY = (domain.getEnemyActorList().get(i).getPosition().y - focusTopRow) * cellHeight;
      Direction enemyDirection = domain.getEnemyActorList().get(i).getDirection();
      Sprite enemySprite = ENEMY_SPRITES.get(enemyDirection);
      g.drawImage(enemySprite.sprite, enemyDrawX, enemyDrawY, cellWidth, cellHeight, this);
    }

    // Populate the last row with the inventory
    int lastRow = FOCUS_SIZE;
    for (int col = 0; col < FOCUS_SIZE; col++) {
      if (col < chapInventory.size()) {
        Sprite sprite = TILE_SPRITES.get(chapInventory.get(col));
        BufferedImage spriteImage = sprite.sprite;
        int drawX = col * cellWidth;
        int drawY = lastRow * cellHeight + 25;  // Add 25 to draw the inventory below the game
        g.drawImage(spriteImage, drawX, drawY, cellWidth, cellHeight, this);
      } else {
        // Draw freeTile in the last row if Chap's inventory is empty
        TileType freeTile = TileType.FREE;
        Sprite freeTileSprite = TILE_SPRITES.get(freeTile);

        if (freeTileSprite != null) {
          BufferedImage spriteImage = freeTileSprite.sprite;
          int drawX = col * cellWidth;
          int drawY = lastRow * cellHeight + 25; // Add 25 to draw the inventory below the game
          g.drawImage(spriteImage, drawX, drawY, cellWidth, cellHeight, this);
        }
      }
      //draw horizontal line to separate inventory from game
      g.setColor(new Color(53, 18, 46));
      g.fillRect(0, FOCUS_SIZE * cellHeight+ 8, getWidth(), 10);  // Add 8 to draw the line below the game
    }
  }

  /**
   * Method to update the renderer.
   */
  public void updateRenderer() {
    ArrayList<TileType> chapInventory = domain.getChap().getKeys(); // Get Chap's keys
    updateChapInventory(chapInventory);
    repaint();
  }

  /**
   * Method to update Chap's inventory.
   *
   * @param inventory The inventory to be updated.
   */
  public void updateChapInventory(ArrayList<TileType> inventory) {
    chapInventory.clear();
    chapInventory.addAll(inventory);
  }

  /**
   * Implementation of the handleEvent method from DomainObserver.
   *
   * @param eventType The type of event.
   * @param itemType  The type of item.
   */
  @Override
  public void handleEvent(EventType eventType, TileType itemType) {
    switch (eventType) {
      case UNLOCK_DOOR:
        sound.playUnlockSound();
        break;
      case PICKUP_ITEM:
        sound.playPickupSound();
        break;
      case LOCKED_DOOR:
        sound.playLockedSound();
        break;
      case DAMAGE:
        sound.playDamageSound();
        break;
      case DEATH:
        sound.stopBackgroundMusic();
        sound.playDeathSound();
        break;
      default:
        break;
    }
  }
}