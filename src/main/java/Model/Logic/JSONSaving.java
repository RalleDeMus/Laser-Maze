package Model.Logic;

import Model.Tiles.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling saving of game states to JSON files.
 */
public class JSONSaving {


    /**
     * Saves the current game state to a JSON file with the specified filename.
     * The JSON file will contain the positions and types of all tiles on the board.
     * The JSON file will also contain the number of each type of tile available to the player.
     * The JSON file will be saved in the "src/main/levels/custom" directory.
     */
    public static void saveGameState(String filename, Board board) {
        JSONObject gameInfo = new JSONObject();
        JSONArray tilesArray = new JSONArray();

        int boardSize = board.getBoardSize();
        Tile[][] tiles = board.getTiles();
        GameInfo game_info = board.get_game_info();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Tile tile = tiles[i][j];
                if (tile != null) {
                    JSONObject tileObject = new JSONObject();
                    tileObject.put("row", i);
                    tileObject.put("col", j);
                    tileObject.put("type", tile.getClass().getSimpleName());
                    tileObject.put("orientation", tile.getOrientation());
                    tileObject.put("rotatable", tile.getIsRotatable());

                    tilesArray.put(tileObject);
                }
            }
        }

        // Add the tiles array to the "gameinfo" object
        gameInfo.put("tiles", tilesArray);

        // Create "extra tiles" object
        JSONObject game_info_JSON = new JSONObject();
        game_info_JSON.put("MirrorTiles", game_info.getTileFromDictionary("MirrorTile"));
        game_info_JSON.put("SplitterTiles", game_info.getTileFromDictionary("SplitterTile"));
        game_info_JSON.put("CheckPointTiles", game_info.getTileFromDictionary("CheckPointTile"));
        game_info_JSON.put("DoubleTiles", game_info.getTileFromDictionary("DoubleTile"));
        game_info_JSON.put("targets", game_info.getTargets());
        game_info_JSON.put("level", game_info.getLevel());

        // Create the root JSON object to hold both "gameinfo" and "extra tiles"
        JSONObject boardState = new JSONObject();
        boardState.put("gameinfo", gameInfo);
        boardState.put("extra tiles", game_info_JSON);

        // Save the JSON object to a file
        //System.out.println("Saving game state to " + filename + ".json");
        try (FileWriter file = new FileWriter("src/main/levels/custom/"+filename+ ".json")) {
            file.write(boardState.toString(4)); // Write with indentation for readability
            //System.out.println("Successfully Copied JSON Object to File...");
            //System.out.println("\nJSON Object: " + boardState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the game info from the JSON file with the temp.json filename.
     * The this info is essentially just copied over to a new file with the specified filename.
     * Used when making costum levels.
     */
    public static void saveTempAs(String file_name) {
        String sourcePath = "src/main/levels/custom/temp.json"; // Path to the source file
        String destinationPath = "src/main/levels/custom/" + file_name + ".json"; // Path to the destination file

        try {
            // Read the contents of the source file
            String content = new String(Files.readAllBytes(Paths.get(sourcePath)));

            // Write the contents to the destination file
            try (FileWriter file = new FileWriter(destinationPath)) {
                file.write(content); // Write the content without modification
                System.out.println("Successfully copied contents to " + destinationPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifies the board to allow for free rotations when user rotates tiles in the level editor to the fifth "free" rotation
     * This is done by setting the isRotatable attribute of the tiles to true if the orientation is 4.
     * The tile counts and targets are also set to the specified values by the user in the level editor. Level is set to 0
     * Then we save this new Tile array to a JSON file with the name "temp.json".
     */

    public static void saveLevelWithFreeRotations(Board board, int[] tileCounts, int targets) {
        Tile[][] tiles = board.getTiles();


        // Set rotateable based on orientation
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (tiles[i][j] != null) {

                    System.out.println("Tile at: " +i + " " + j + " rotation: " + tiles[i][j].getOrientation());
                    if (tiles[i][j].getOrientation() == 4) {
                        tiles[i][j].setIsRotatable(true);
                        System.out.println("TILE AT: " + i + " " + j + " is rotateable");
                    } else {
                        tiles[i][j].setIsRotatable(false);
                    }
                }
            }
        }

        // Set game info to match

        Map<String, Integer> tilesInfo = new HashMap<>();
        tilesInfo.put("MirrorTile", tileCounts[0]);
        tilesInfo.put("SplitterTile", tileCounts[1]);
        tilesInfo.put("CheckPointTile", tileCounts[2]);
        tilesInfo.put("DoubleTile", tileCounts[3]);

        GameInfo game_info = new GameInfo(0,targets,tilesInfo);
        board.set_game_info(game_info);

        saveGameState("temp",board);

    }

}
