package nz.ac.wgtn.swen225.lc.persistency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.wgtn.swen225.lc.domain.*;

public class FileHandler {
  public void save(Maze maze, File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(maze);
    System.out.println(json); // DEBUG: raw json string

    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(maze));

    fileWriter.close();
    System.out.println("Save successful");
  }

  public Maze loadMaze(String fileName) {
    File file = new File(fileName);
    Maze maze;

    ObjectMapper mapper = new ObjectMapper();
    try {
      maze = mapper.readValue(file, Maze.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return maze;
  }
}
