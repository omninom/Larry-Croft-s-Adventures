package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import nz.ac.wgtn.swen225.lc.domain.*;

/**
 * Handles file operations for saving and loading levels.
 *
 * @author Benjamin Park
 */
public class FileHandler {
  /**
   * Saves the supplied maze.
   *
   * @param maze The maze object to be saved.
   * @param file The file object to save maze to.
   * @throws IOException
   */
  public void save(Maze maze, File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(maze);
    System.out.println(json); // DEBUG: raw json string

    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(maze));

    fileWriter.close();
    System.out.println("Save successful");
  }

  /**
   * Loads a maze Level.
   *
   * @param fileName the name of the JSON level file.
   * @return Maze level
   */
  public Maze loadMaze(String fileName) {
    Maze maze;

    try (InputStream inputStream = getClass().getResourceAsStream(String.format("/levels/%s", fileName))) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      maze = mapper.readValue(inputStream, Maze.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return maze;
  }
}
