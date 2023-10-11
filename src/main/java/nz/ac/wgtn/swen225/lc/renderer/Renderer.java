package nz.ac.wgtn.swen225.lc.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JPanel;
import nz.ac.wgtn.swen225.lc.domain.*;

/**
 * Renderer.java
 * Handles the rendering of the game.
 * @author Leory Xue (300607821)
 */
public class Renderer extends JPanel implements DomainObserver {
  private static final int GRID_SIZE = 9;
  private static final HashMap<Direction, Sprite> CHAP_SPRITES = new HashMap<>();
  private static final HashMap<TileType, Sprite> TILE_SPRITES = new HashMap<>();

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
  }

  private final Domain domain;

  //sound effect
  private final Sound sound = new Sound();

  public Renderer(Domain domain) {
    this.domain = domain;
    domain.addObserver(this);
    sound.playBackgroundMusic();
    setPreferredSize(new Dimension(GRID_SIZE * 50, GRID_SIZE * 50)); // Adjust the size as needed
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int cellWidth = getWidth() / GRID_SIZE;
    int cellHeight = getHeight() / GRID_SIZE;

    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        TileType tile = domain.getTiles()[row][col];
        Sprite sprite = TILE_SPRITES.get(tile);

        if (sprite != null) {
          BufferedImage spriteImage = sprite.sprite;
          g.drawImage(spriteImage, col * cellWidth, row * cellHeight, cellWidth, cellHeight, this);
        }

        //if player is on an info tile, display a random info message FOR NOW: NEED INFO FROM DOMAIN
        if (domain.isOnInfo()){
          String infoMessage = "Move Larry with the arrow keys";
          g.setColor(Color.WHITE);
          //draw the message in the top middle of the screen in big size with white font and black outline
          g.setFont(new Font("TimesRoman", Font.BOLD, 20));
          //give font outline
          g.drawString(infoMessage, (getWidth() / 2) - (g.getFontMetrics().stringWidth(infoMessage) / 2), getHeight()/4);
        }

        if (domain.getChap().getPosition().equals(new Point(col, row))) {
          //determine which sprite to use
          Direction chapDirection = domain.getChap().getDirection();
          Sprite chapSprite = CHAP_SPRITES.get(chapDirection);
          g.drawImage(chapSprite.sprite, col * cellWidth, row * cellHeight, cellWidth, cellHeight, this);
        }
      }
    }
  }

  public void updateRenderer() {
    repaint();
  }

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
    }
  }
}