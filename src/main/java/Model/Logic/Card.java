package Model.Logic;

import Model.Tiles.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Card {

     private String content;
     private int targetMirrorTiles = 0;
     private int splitterTiles = 0;
     private int checkPointTiles = 0;
     private int doubleTiles = 0;
     private int targets;
     private int level;

     final private Tile[][] tiles = new Tile[5][5];;
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
                boolean rotatable = tileObject.getBoolean("rotatable");
                if(rotatable){
                    orientation = 4;
                }
                String type = tileObject.getString("type");

                switch (type) {
                    case "LaserTile":
                        this.tiles[row][col] = new LaserTile(false, rotatable, orientation);

                        break;
                    case "CellBlockerTile":
                        this.tiles[row][col] = new CellBlockerTile(false);

                        break;
                    case "CheckPointTile":
                        this.tiles[row][col] = new CheckPointTile(false, rotatable, orientation);

                        break;
                    case "SplitterTile":
                        this.tiles[row][col] = new SplitterTile(false, rotatable, orientation);

                        break;
                    case "MirrorTile":
                        this.tiles[row][col] = new MirrorTile(false, rotatable, orientation);

                        break;
                    case "DoubleTile":
                        this.tiles[row][col] = new DoubleTile(false, rotatable, orientation);

                        break;

                }
            }

            JSONObject extraTiles = jsonObject.getJSONObject("extra tiles");
            this.checkPointTiles = extraTiles.getInt("CheckPointTiles");
            this.splitterTiles = extraTiles.getInt("SplitterTiles");
            this.targetMirrorTiles = extraTiles.getInt("MirrorTiles");
            this.doubleTiles = extraTiles.getInt("DoubleTile");
            this.targets = extraTiles.getInt("targets");
            this.level = extraTiles.getInt("level");







         return this.tiles;



     }

     public int[] getPlaceableTiles(){

         int[] game_info = new int[6];
         game_info[0] = targetMirrorTiles;
         game_info[1] = splitterTiles;
         game_info[2] = checkPointTiles;
         game_info[3] = doubleTiles;
         game_info[4] = targets;
         game_info[5] = level;
         return game_info;
     }
}
