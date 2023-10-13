package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import nz.ac.wgtn.swen225.lc.domain.*;

/**
 * Handles file operations for saving and loading levels.
 *
 * @author Benjamin Park
 */
public class FileHandler {
  /**
   * Reads a file to a String.
   *
   * @param fileName index of the level to be loaded.
   * @return String representing the file.
   */
  public static String loadLevelJson(String fileName) {
    StringBuilder ret = new StringBuilder();
    try {
      File reader = new File(fileName);
      Scanner scanner = new Scanner(reader);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        ret.append(line).append('\n');
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Can't find json.");
    }
    return ret.toString();
  }

  /**
   * Reads a file to a JsonNode.
   *
   * @param fileName index of the level to be loaded.
   * @return JsonNode representing the file.
   */
  public static JsonNode loadLevelJsonNode(String fileName) {
    File reader = new File(fileName);
    String jsonString = loadLevelJson(fileName);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode ret;
    try {
      ret = mapper.readTree(reader);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
    return ret;
  }
}

