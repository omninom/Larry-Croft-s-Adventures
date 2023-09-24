package nz.ac.wgtn.swen225.lc.app;

import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

/**
 * The main window.
 */
class AppWindow extends JFrame {
  private final App app;
  private final Renderer renderer;

  public AppWindow(App app) {
    this.app = app;
    this.renderer = new Renderer();

    // Set up the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addKeyListener(createKeyListener());

    // Set up the window contents
    addMenuBar();
    add(this.renderer);
    pack();
  }

  private KeyListener createKeyListener() {
    return new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_UP:
            app.handleInput(AppInput.MOVE_UP);
            break;
          case KeyEvent.VK_DOWN:
            app.handleInput(AppInput.MOVE_DOWN);
            break;
          case KeyEvent.VK_LEFT:
            app.handleInput(AppInput.MOVE_LEFT);
            break;
          case KeyEvent.VK_RIGHT:
            app.handleInput(AppInput.MOVE_RIGHT);
            break;
          case KeyEvent.VK_SPACE:
            app.handleInput(AppInput.PAUSE);
            break;
          case KeyEvent.VK_ESCAPE:
            app.handleInput(AppInput.UNPAUSE);
            break;
          default:
            break;
        }
      }
    };
  }

  private void addMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu gameMenu = new JMenu("Game");

    JMenuItem level1 = new JMenuItem("Level 1");
    level1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
    level1.addActionListener(event -> app.newGame(1));

    JMenuItem level2 = new JMenuItem("Level 2");
    level2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));
    level2.addActionListener(event -> app.newGame(2));

    JMenuItem resume = new JMenuItem("Resume");
    resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
    resume.addActionListener(event -> {
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        if (!app.loadGame(fileChooser.getSelectedFile())) {
          JOptionPane.showMessageDialog(this, "Failed to load game", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    JMenuItem exitWithSave = new JMenuItem("Save and Exit");
    exitWithSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
    exitWithSave.addActionListener(event -> {
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        if (!app.saveGame(fileChooser.getSelectedFile())) {
          JOptionPane.showMessageDialog(this, "Failed to save game", "Error",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
      app.endGame();
      System.exit(0);
    });

    JMenuItem exitNoSave = new JMenuItem("Exit");
    exitNoSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
    exitNoSave.addActionListener(event -> {
      app.endGame();
      System.exit(0);
    });

    gameMenu.add(level1);
    gameMenu.add(level2);
    gameMenu.add(new JSeparator());
    gameMenu.add(resume);
    gameMenu.add(exitWithSave);
    gameMenu.add(exitNoSave);

    menuBar.add(gameMenu);
    setJMenuBar(menuBar);
  }
}
