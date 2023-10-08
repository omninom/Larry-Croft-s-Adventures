package nz.ac.wgtn.swen225.lc.renderer;

import java.awt.image.BufferedImage;


public enum Sprite {
    chap,
    wallTile,
    freeTile1,
    redKey,
    blueKey,
    greenKey,
    yellowKey,
    redLock,
    blueLock,
    greenLock,
    yellowLock,
    treasure,
    info,
  exitLock,
    exit;

  /**
   * The image to be loaded and displayed on screen.
   */
  public final BufferedImage sprite;

  /**
   * Sprite constructor to load an image.
   */
  Sprite() {
    sprite = loadImage(this.name());
  }


  static private BufferedImage loadImage(String name) {
    String filename = "resources/" + name + ".png";
    try {
      return javax.imageio.ImageIO.read(Sprite.class.getResource("/sprites/" + name + ".png"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to load image: " + filename);
    }
  }


}
