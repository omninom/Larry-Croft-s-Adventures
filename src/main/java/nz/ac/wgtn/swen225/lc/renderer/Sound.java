package nz.ac.wgtn.swen225.lc.renderer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

  private Clip backgroundMusicClip;

  public Sound() {
    //initialise background music
    loadBackgroundMusic();
  }

  private void loadBackgroundMusic() {
    try {
      URL musicUrl = Sound.class.getResource("/sound/bgm.wav");
      assert musicUrl != null;
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicUrl);
      backgroundMusicClip = AudioSystem.getClip();
      backgroundMusicClip.open(audioInputStream);
      //loop the music
      backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void playBackgroundMusic() {
    if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
      backgroundMusicClip.setFramePosition(0);
      backgroundMusicClip.start();
    }
  }

  public void stopBackgroundMusic() {
    if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
      backgroundMusicClip.stop();
    }
  }

  public void playDamageSound() {
    playSoundEffect("/sound/damage.wav");
  }

  public void playLockedSound() {
    playSoundEffect("/sound/locked.wav");
  }

  public void playPickupSound() {
    playSoundEffect("/sound/treasure.wav");
  }

  public void playUnlockSound() {
    playSoundEffect("/sound/unlock.wav");
  }

  private void playSoundEffect(String soundEffectPath) {
    try {
      URL soundEffectUrl = Sound.class.getResource(soundEffectPath);
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundEffectUrl);
      Clip soundEffectClip = AudioSystem.getClip();
      soundEffectClip.open(audioInputStream);
      soundEffectClip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }
}
