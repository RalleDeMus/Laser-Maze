import Tiles.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class Card {
     private String level;

     private String content;
     private int targetMirrorTiles = 0;
     private int splitterTiles = 0;
     private int checkPointTiles = 0;
     private int doubleTiles = 0;
     private int cellBlockerTiles = 0;
     private Tile[][] tiles = new Tile[5][5];;
     public Card(String level){
         this.level = level;

         try {
             if (level.equals("game_state")) {
                 this.content = new String(Files.readAllBytes(Paths.get( level + ".json")));
             }
             else{
                 this.content = new String(Files.readAllBytes(Paths.get("src/main/levels/level_" + level + ".json")));
             }
         }

         catch (IOException | JSONException e) {
             e.printStackTrace();
         }
         //load json file
            //parse json file

         //initialize tiles
         for (int row = 0; row < 5; row++) {
             for (int col = 0; col < 5; col++) {
                 this.tiles[row][col] = null;

             }
         }
         //add json implementation








     }


    public Tile[][] getCard(){



            JSONObject jsonObject = new JSONObject(content);
            JSONObject gameInfo = jsonObject.getJSONObject("gameinfo");
            JSONArray tilesArray = gameInfo.getJSONArray("tiles");

            for (int i = 0; i < tilesArray.length(); i++) {
                JSONObject tileObject = tilesArray.getJSONObject(i);
                int col = tileObject.getInt("col");
                int row = tileObject.getInt("row");
                int orientation = tileObject.getInt("orientation");
                String type = tileObject.getString("type");

                switch (type) {
                    case "LaserTile":
                        this.tiles[row][col] = new LaserTile(false, true, orientation);

                        break;
                    case "CellBlockerTile":
                        this.tiles[row][col] = new CellBlockerTile();

                        break;
                    case "CheckPointTile":
                        this.tiles[row][col] = new CheckPointTile(false, true, orientation);

                        break;
                    case "SplitterTile":
                        this.tiles[row][col] = new SplitterTile(false, true, orientation);

                        break;
                    case "MirrorTile":
                        this.tiles[row][col] = new MirrorTile(false, true, orientation);

                        break;
                    case "DoubleTile":
                        this.tiles[row][col] = new DoubleTile(false, true, orientation);

                        break;

                }
            }

            JSONObject extraTiles = jsonObject.getJSONObject("extra tiles");
            this.cellBlockerTiles = extraTiles.getInt("CellBlockerTiles");
            this.checkPointTiles = extraTiles.getInt("CheckPointTiles");
            this.splitterTiles = extraTiles.getInt("SplitterTiles");
            this.targetMirrorTiles = extraTiles.getInt("MirrorTiles");
            this.doubleTiles = extraTiles.getInt("DoubleTile");







         return this.tiles;



     }

     public int[] getPlaceableTiles(){

         int[] placeableTiles = new int[5];
         placeableTiles[0] = targetMirrorTiles;
         placeableTiles[1] = splitterTiles;
         placeableTiles[2] = checkPointTiles;
         placeableTiles[3] = doubleTiles;
         placeableTiles[4] = cellBlockerTiles;
         return placeableTiles;
     }
}
