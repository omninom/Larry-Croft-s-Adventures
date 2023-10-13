package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nz.ac.wgtn.swen225.lc.domain.Chap;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.TileType;

/**
 * Handles initial loading of base level files from json.
 * GameLoaderImp and GameSaverImp handle loading and saving user-created savegames.
 */
public class ParsingLevelLoader implements LevelLoader {

  /**
   * Legacy code, used to produce an example level json file
   * @return String for level json
   */
  public static String writeDefaultMazeJson() {
    StringBuilder retS = new StringBuilder();
    retS.append("{\n")
        .append("  \"levelnum\" : 1,")
        .append("  \"info\" : \"testInfo\",")
        .append("  \"tiles\" : [\n");
    TileType[][] maze = new TileType[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        maze[i][j] = TileType.FREE;
      }
    }

    for (int i = 0; i < TILE_TYPES.length; i++) {
      int x = i % 9;
      int y = (i - i % 9) / 9;
      maze[y][x] = TILE_TYPES[i];
    }
    StringBuilder mazeStr = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      mazeStr.append("    [");
      mazeStr.append(maze[i][0].ordinal());
      for (int j = 1; j < 9; j++) {
        mazeStr.append(',');
        mazeStr.append(maze[i][j].ordinal());
      }
      mazeStr.append("],\n");
    }
    retS.append(mazeStr.substring(0, mazeStr.length() - 2));


    retS.append("],\n")
        .append("  \"numRows\" : 9,\n")
        .append("  \"numCols\" : 9,\n")
        .append("  \"chap\" : {\n")
        .append("    \"position\" : {\n")
        .append("      \"x\" : 4,\n")
        .append("      \"y\" : 4\n")
        .append("    }")
        .append("  },\n")
        .append("  \"entities\" : [],\n")
        .append("  \"inventory\" : [").append(TileType.GREEN_KEY.ordinal()).append("],\n")
        .append("  \"isAlive\" : true\n")
        .append("}");
    return retS.toString();
  }

  /**
   * Used for converting ordinal values into enum values.
   */
  private static final TileType[] TILE_TYPES = TileType.values();

  /**
   * Used for converting ordinal values into enum values.
   */
  private static final String[] EXPECTED_FIELDS = {
      "levelNum","info", "tiles", "numRows", "numCols", "chap", "inventory", "isAlive", "entities"};

  @Override
  public void loadLevel(Domain domain, int levelNumber) throws JsonParseException {
    if (levelNumber <= 0) {
      throw new IllegalArgumentException("Levels must have a positive index.");
    }
    JsonNode levelJsonRoot = FileHandler.loadLevelJsonNode("src/main/resources/levels/level" + levelNumber + ".json");
    for (String expectedField : EXPECTED_FIELDS) {
      if (!levelJsonRoot.hasNonNull(expectedField)) {
        throw new JsonParseException("Couldn't find top-level field " + expectedField);
      }
    }

    //Validate level num

    if (!levelJsonRoot.get("levelNum").isIntegralNumber()) {
      throw new JsonParseException("Expected int for field \"levelNum\"");
    }
    if (levelNumber != levelJsonRoot.get("levelNum").asInt(-1)) {
      throw new JsonParseException(
          String.format("Level numbers don't match: expected %d, got %d", levelNumber,
                        levelJsonRoot.get("levelNum").asInt(-1)
          ));
    }

    //Load Info
    JsonNode infoNode = levelJsonRoot.get("info");
    String infoString = parseInfo(infoNode);

    //Load Tiles
    int rows = parseNumRows(levelJsonRoot.get("numRows"));
    int cols = parseNumCols(levelJsonRoot.get("numCols"));

    JsonNode tileGridRoot = levelJsonRoot.get("tiles");
    TileType[][] mazeTiles = parseTileTypes(tileGridRoot, rows, cols);


    //load Chap, Keys, and
    JsonNode chapNode = levelJsonRoot.get("chap");
    Chap chap = parseChap(chapNode);

    //load keys
    JsonNode inventoryNode = levelJsonRoot.get("inventory");
    List<TileType> keys = parseKeys(inventoryNode);

    //load alive status
    JsonNode aliveNode = levelJsonRoot.get("isAlive");
    if (!aliveNode.isBoolean()) {
      throw new JsonParseException("Expected boolean for field \"isAlive\"");
    }

    //load Entities
    //TODO entities


    domain.buildNewLevel(mazeTiles,chap,List.of(),keys,infoString,levelNumber);
  }

  private String parseInfo(JsonNode infoNode) throws JsonParseException {
    if(!infoNode.isTextual()){
      throw new JsonParseException("Info field should contain text.");
    }
    return infoNode.asText("ERROR");
  }

  private int parseNumRows(JsonNode numRowsNode) throws JsonParseException {
    if (!numRowsNode.isIntegralNumber()) {
      throw new JsonParseException("Expected int for field \"numRows\"");
    }
    int rows = numRowsNode.asInt(-1);

    if (rows <= 0) {
      throw new JsonParseException("Must have a positive number of rows.");
    }

    return rows;
  }

  private int parseNumCols(JsonNode numColsNode) throws JsonParseException {
    if (!numColsNode.isIntegralNumber()) {
      throw new JsonParseException("Expected int for field \"numCols\"");
    }
    int cols = numColsNode.asInt(-1);

    if (cols <= 0) {
      throw new JsonParseException("Must have a positive number of rows.");
    }

    return cols;
  }

  private TileType[][] parseTileTypes(JsonNode tileGridRoot, int rows, int cols)
      throws JsonParseException {

    TileType[][] mazeTiles = new TileType[rows][cols];

    if (!tileGridRoot.isArray()) {
      throw new JsonParseException("Expected 2D array in \"tiles\" field.");
    }
    if (tileGridRoot.size() != rows) {
      throw new JsonParseException("\"numRows\" and the size of \"tiles\" don't match.");
    }

    for (int i = 0; i < rows; i++) {
      JsonNode rowNode = tileGridRoot.get(i);

      if (!rowNode.isArray()) {
        throw new JsonParseException("Expected 2D array in \"tiles\" field.");
      }
      if (rowNode.size() != cols) {
        throw new JsonParseException("\"numCols\" and the size of \"tiles\" don't match.");
      }

      for (int j = 0; j < cols; j++) {
        JsonNode tileNode = rowNode.get(j);

        if (!tileNode.isIntegralNumber()) {
          throw new JsonParseException(
              "Expected int (to be parsed as enum) for value in \"tiles\" field at "
              + "(" + j + "," + i + ")."
          );
        }

        int tileVal = tileNode.asInt();

        if (tileVal < 0 || tileVal > TILE_TYPES.length) {
          throw new JsonParseException("Out of bound TileType integer at "
                                       + "(" + j + "," + i + ")."
          );
        }

        mazeTiles[i][j] = TILE_TYPES[tileVal];
      }

    }

    return mazeTiles;
  }

  private Chap parseChap(JsonNode chapNode) throws JsonParseException {
    if (!chapNode.hasNonNull("position")) {
      throw new JsonParseException("Couldn't find chap's position field.");
    }
    JsonNode posNode = chapNode.get("position");
    if (!posNode.hasNonNull("x")) {
      throw new JsonParseException("Couldn't find chap's x position field.");
    }
    if (!posNode.get("x").isIntegralNumber()) {
      throw new JsonParseException("Chap's x position field must be an integer.");
    }
    int chapX = posNode.get("x").asInt();
    if (!posNode.hasNonNull("y")) {
      throw new JsonParseException("Couldn't find chap's y position field.");
    }
    if (!posNode.get("y").isIntegralNumber()) {
      throw new JsonParseException("Chap's y position field must be an integer.");
    }
    int chapY = posNode.get("y").asInt();
    Chap ret = new Chap(chapX, chapY);
    return ret;
  }

  private List<TileType> parseKeys(JsonNode inventoryNode) throws JsonParseException {
    List<TileType> ret = new ArrayList<>();
    if (!inventoryNode.isArray()) {
      throw new JsonParseException("Expected array in \"inventory\" field.");
    }
    for (int i = 0; i < inventoryNode.size(); i++) {
      JsonNode keyNode = inventoryNode.get(i);
      if (!keyNode.isIntegralNumber()) {
        throw new JsonParseException(
            "Expected int (to be parsed as enum) in inventory field at " + "index " + i + "."
        );
      }
      int keyVal = keyNode.asInt();
      if (keyVal < 0 || keyVal > TILE_TYPES.length) {
        throw new JsonParseException("Invalid TileType ordinal at index " + i + "in inventory.");
      }
      TileType keyType = TILE_TYPES[keyVal];
      if (keyType != TileType.BLUE_KEY && keyType != TileType.RED_KEY
          && keyType != TileType.GREEN_KEY && keyType != TileType.YELLOW_KEY) {
        throw new JsonParseException("Found non-key ordinal in inventory.");
      }
      ret.add(keyType);
    }
    return ret;
  }

  public static void main(String... args){
    try {
      File outF = new File("src/main/resources/levels/level1.json");
      outF.createNewFile();
      FileWriter out = new FileWriter(outF);
      out.write(ParsingLevelLoader.writeDefaultMazeJson());
      out.flush();
    }catch(IOException e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}


