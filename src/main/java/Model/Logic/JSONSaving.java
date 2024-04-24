package Model.Logic;

import Model.Tiles.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONSaving {


    public static void saveGameState(String filename, Board board) {
        JSONObject gameInfo = new JSONObject();
        JSONArray tilesArray = new JSONArray();

        int boardSize = board.getBoardSize();
        Tile[][] tiles = board.getTiles();
        int[] game_info = board.get_game_info();

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

                    // Add other properties for different tile types if needed

                    tilesArray.put(tileObject);
                }
            }
        }

        // Add the tiles array to the "gameinfo" object
        gameInfo.put("tiles", tilesArray);

        // Create "extra tiles" object
        JSONObject game_info_JSON = new JSONObject();
        game_info_JSON.put("MirrorTiles", game_info[0]);
        game_info_JSON.put("SplitterTiles", game_info[1]);
        game_info_JSON.put("CheckPointTiles", game_info[2]);
        game_info_JSON.put("DoubleTile", game_info[3]);
        game_info_JSON.put("targets", game_info[4]);
        game_info_JSON.put("level", game_info[5]);

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

}
