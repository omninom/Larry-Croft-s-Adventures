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
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicUrl);
      backgroundMusicClip = AudioSystem.getClip();
      backgroundMusicClip.open(audioInputStream);
      //set volume to 50%
      FloatControl gainControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-10.0f);
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
}
