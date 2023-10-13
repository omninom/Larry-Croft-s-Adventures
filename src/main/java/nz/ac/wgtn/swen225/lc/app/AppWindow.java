package nz.ac.wgtn.swen225.lc.app;

import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import nz.ac.wgtn.swen225.lc.recorder.RecordItem;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The main window.
 *
 * @author Jack Gallagher (300615528)
 */
class AppWindow extends JFrame {
  private final App app;
  private final Renderer renderer;

  public AppWindow(App app) {
    this.app = app;
    this.renderer = new Renderer(app.getDomain());

    this.app.setUpdateCallback(this::onUpdateCallback);

    // Set up the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addKeyListener(createKeyListener());

    // Set up the window contents
    addMenuBar();
    add(this.renderer);
    pack();
  }

  /**
   * Called when the game needs to redraw.
   */
  public void onUpdateCallback() {
    renderer.updateRenderer();
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

        // TODO: Move this
        app.markUpdated();
      }
    };
  }

  private void askReplaySpeed() {
    String userSpeed = JOptionPane.showInputDialog("Enter replay speed (1(fast) to 10(slow)):");

    // Validate the input speed
    int newSpeed = 5; // Default speed
    try {
      newSpeed = Integer.parseInt(userSpeed);
    } catch (NumberFormatException e) {
      System.out.println("Error: Invalid replay speed: " + e.getMessage());
      askReplaySpeed();
    }

    app.getRecorder().setReplaySpeed(newSpeed);
  }

  private HashMap<Integer, RecordItem> getLoadedRecording() {
    HashMap<Integer, RecordItem> loadedRecording;

    // ---- Ask user for the file to load ---- //
    JFileChooser fileFinder = new JFileChooser();
    fileFinder.setDialogTitle("Select file to load recording from:");
    int userAction = fileFinder.showOpenDialog(null);
    loadedRecording = new HashMap<>();

    // ---- Validate the file ---- //
    if (userAction == JFileChooser.APPROVE_OPTION) {
      try {
        // ---- Read the json file ---- //
        String filePath = fileFinder.getSelectedFile().getAbsolutePath();
        String jsonData = Files.readString(Paths.get(filePath));
        JSONObject json = new JSONObject(jsonData);

        // -- Break down the json data -- //
        for (String key : json.keySet()) {
          int sequenceNumber = Integer.parseInt(key);
          JSONObject recordData = json.getJSONObject(key);
          String move = recordData.getString("move");
          String actor = recordData.getString("actor");

          // -- Check if the game has ended -- //
          if (actor.equals("END")) {
            break;
          }

          // -- Add the data -- //
          RecordItem newRecordItem = new RecordItem(sequenceNumber, actor, move);
          loadedRecording.put(sequenceNumber, newRecordItem);
        }
      } catch (IOException | JSONException e) {
        System.out.println("Error: Loading Json file: " + e.getMessage());
      }
    }

    // ---- Verify selected file ---- //
    if (loadedRecording.isEmpty()) {
      System.out.println("Error: No recording loaded.");
      getLoadedRecording();
    } else {
      fileFinder.setVisible(false);
    }

    app.markUpdated();
    return loadedRecording;
  }

  private void addMenuBar() {
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

    JMenu gameMenu = new JMenu("Game");
    gameMenu.add(level1);
    gameMenu.add(level2);
    gameMenu.add(new JSeparator());
    gameMenu.add(resume);
    gameMenu.add(exitWithSave);
    gameMenu.add(exitNoSave);

    JMenuItem manualReplayNext = new JMenuItem("Next Action Replay");
    manualReplayNext.addActionListener(event -> {
      app.getRecorder().manualStepReplay();
    });

    JMenuItem manualReplay = new JMenuItem("Manual Replay");
    manualReplay.addActionListener(event -> {
      HashMap<Integer, RecordItem> loadedRecording = getLoadedRecording();
      app.getRecorder().stepUpManualReplay(loadedRecording);
      manualReplayNext.setVisible(true);
      System.out.println("For manual replay, press 'Next Action Replay' in the"
              + " menu bar to step through the replay.");
    });

    JMenuItem autoReplay = new JMenuItem("Auto Replay");
    autoReplay.addActionListener(event -> {
      askReplaySpeed();
      HashMap<Integer, RecordItem> loadedRecording = getLoadedRecording();
      app.getRecorder().autoReplay(loadedRecording);
    });

    JMenu recorderMenu = new JMenu("Recorder");
    recorderMenu.add(manualReplay);
    recorderMenu.add(autoReplay);

    JMenuBar menuBar = new JMenuBar();
    menuBar.add(gameMenu);
    menuBar.add(recorderMenu);
    menuBar.add(manualReplayNext);
    manualReplayNext.setVisible(false);

    setJMenuBar(menuBar);
  }
}
