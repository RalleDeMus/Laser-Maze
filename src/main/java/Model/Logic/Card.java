package Model.Logic;

import Model.Tiles.*;
import Model.Tiles.GameTiles.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling the card and its contents.
 * The card is the level configuration and contains the tiles and the number of placeable tiles as well as the number of targets.
 */
public class Card {

     private String content; // The level being used to create the card. We use this to load from the json levels.
     private Map<String, Integer> placeableTiles; // The number of placeable tiles for the current level
     private int targets; // The number of targets in the level
     private int level; // The level number

     final private Tile[][] tiles = new Tile[5][5]; // The tiles on the board

    /**
     * Constructs a new Card instance with specified level.
     *
     * @param level the level of the game, can be a number for premade levels or "game_state", "temp" or custom levels.
     */
     public Card(String level){


         try {
             try {
                 // Attempt to parse the level as an integer
                 Integer.parseInt(level);
                 // If parsing succeeds, load the level using an integer-based filename
                 this.content = new String(Files.readAllBytes(Paths.get("src/main/levels/level_" + level + ".json")));
             } catch (NumberFormatException e) {
                 // If parsing fails, load the custom level instead
                 this.content = new String(Files.readAllBytes(Paths.get("src/main/levels/custom/"+level+".json")));
             }
         } catch (IOException | JSONException e) {
             e.printStackTrace();
         }
     }



    /**
     * Gets a card from the level file and returns the tiles.
     */
    public Tile[][] getCard(){

            JSONObject jsonObject = new JSONObject(content);
            JSONObject gameInfo = jsonObject.getJSONObject("gameinfo");
            JSONArray tilesArray = gameInfo.getJSONArray("tiles");

            for (int i = 0; i < tilesArray.length(); i++) {
                JSONObject tileObject = tilesArray.getJSONObject(i);
                int col = tileObject.getInt("col");
                int row = tileObject.getInt("row");
                int orientation = tileObject.getInt("orientation");
                boolean rotatable = tileObject.getBoolean("rotatable");
                if(rotatable){
                    orientation = 4;
                }
                String type = tileObject.getString("type");

                this.tiles[row][col] = TileInfo.TileFromKey(type);

                this.tiles[row][col].rotate(orientation,5);

                this.tiles[row][col].setIsRotatable(rotatable);
                this.tiles[row][col].setIsMoveable(false);


            }

            JSONObject extraTiles = jsonObject.getJSONObject("extra tiles");


            this.placeableTiles = new HashMap<>();
            // Foreach tile type, add the number of placeable tiles to the map
            TileInfo.getTiles(true).forEach(tile -> {
                    System.out.println(tile.getClass().getSimpleName()+"s");
                    if (extraTiles.has(tile.getClass().getSimpleName() + "s")){

                        this.placeableTiles.put(tile.getClass().getSimpleName(), extraTiles.getInt(tile.getClass().getSimpleName() + "s"));
                    }
                    else{
                        this.placeableTiles.put(tile.getClass().getSimpleName(), 0);
                    }

            });

            this.targets = extraTiles.getInt("targets");
            this.level = extraTiles.getInt("level");

         return this.tiles;

     }

    /**
     * Gets the number of placeable tiles and targets for the current level aswell as the level number.
     */
     public GameInfo getPlaceableTiles(){


        GameInfo gameInfo = new GameInfo(level, targets, placeableTiles);


         return gameInfo;
     }
}
