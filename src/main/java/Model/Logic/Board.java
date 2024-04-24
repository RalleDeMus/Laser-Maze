package Model.Logic;
import Model.Tiles.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.util.List;


//make singleton
public class Board {

    public Tile[][] tiles;
    int boardSize;
    int squareSize;
    private int mirrorsHit;
    private int targetsHit;

    Card card;
    Point cursorPos;

    private int[] game_info;
    Tile selectedTile;
    boolean laserWasFired = false;

    boolean win;

    String level = "0";





    // Model.Logic.Laser tree ???

    // Constructor
        // Gets the board size
        // Initializes the tiles array based json file
        // Initializes the cursor tile
        // Reads from the asset server
    public Board(int boardSize, int squareSize,String level) { // Yes
        this.boardSize = boardSize;
        this.squareSize = squareSize;

        // Set cardlevel based on level
        setCardLevel(level);

        // Reset cursorPos
        cursorPos = new Point(0, 0);


    }


/*
Getters and setters (Maybe we can make some of these their own?)
*/

    public Point getTilePos(int x, int y) {

        return new Point(Math.min(x / squareSize,boardSize-1),  Math.min(y / squareSize,boardSize-1));
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile tile){
        if (tile != null ) {
            if(selectedTile != null) {
                if (selectedTile.getClass() == tile.getClass()) {
                    selectedTile = null;
                } else {
                    selectedTile = tile;
                }
            } else {
                selectedTile = tile;

            }
        }

    }
    public Tile[][] getTiles() {
        return tiles;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public boolean getLaserFired() {
        return laserWasFired;
    }

    // Get the first tile with tag
    public Tile getLaserTile() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    return tiles[row][col];
                }
            }
        }
        return null;
    }

    public int get_game_info_by_index(int index) {
        if (index < 0 || index > game_info.length - 1) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return game_info[index];
    }

    public int[] get_game_info() {

        return game_info;
    }

    public void set_game_info(int[] _game_info) {
        game_info = _game_info;
    }


    public Boolean getWin() {
        return win;
    }

    public void resetWin() {
        win = false;
    }

    public void setWin() {

        if (mirrorsHit >= countMirrors() && targetsHit == game_info[4]){
            System.out.println("Win condition met");
            win = true;
        }
    }


        public void setLaserWasFired(boolean b) {
        laserWasFired = b;

    }
    public void setCursorPos(int x, int y) {
        cursorPos = getTilePos(x, y);
    }

    public Point getCursorPos() {
        return cursorPos;
    }

    public void setCardLevel(String level) {
        //System.out.println("Setting card level to: " + level);
        this.level = level;
        //System.out.println("Setting card level to: " + level);
        this.card = new Card(level);
        tiles = card.getCard();
        game_info = card.getPlaceableTiles();
    }

    public String getLevel() {
        if (level.equals("game_state")) {
            level = game_info[5]+"";
        }
        return level;
    }

    public boolean laserExists() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    return false;
                }
            }
        }
        return true;
    }

    public int countMirrors() {
        int mirrors = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] != null && !(tiles[i][j] instanceof CellBlockerTile || tiles[i][j] instanceof LaserTile)) {
                    mirrors++;
                }
            }
        }
        return mirrors;

    }


/*
STUFF TO CONSTRUCT THE LASER TREE
*/

    // Construct laser tree



/*
HERE WE HAVE ADD TILES AND REMOVE TILES AND ROTATE TILES
 */



    // Add the cursor tile to the board and check if placement is valid
    public void addTile(Tile t,boolean unSelectSelectedTileAfterPlacement) {
        if (t != null) {
            if (tiles[cursorPos.y][cursorPos.x] == null) {
                if (t instanceof LaserTile && getLaserTile() != null) {
                    System.out.println("Laser already exists");
                    return;
                } else if (t instanceof MirrorTile) {
                    if (game_info[0] == 0) {
                        System.out.println("No more mirror tiles");
                        return;
                    } else {
                        game_info[0]--;
                    }

                } else if (t instanceof SplitterTile) {
                    if (game_info[1] == 0) {
                        System.out.println("No more splitter tiles");
                        return;
                    } else {
                        game_info[1]--;
                    }
                } else if (t instanceof CheckPointTile) {
                    if (game_info[2] == 0) {
                        System.out.println("No more checkpoint tiles");
                        return;
                    } else {
                        game_info[2]--;
                    }
                } else if (t instanceof DoubleTile) {
                    if (game_info[3] == 0) {
                        System.out.println("No more double tiles");
                        return;
                    } else {
                        game_info[3]--;
                    }
                }


                if(unSelectSelectedTileAfterPlacement) selectedTile = null;
                tiles[cursorPos.y][cursorPos.x] = t;

            }
        }
    }


    // Remove a tile
    public void removeTile() {
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsMovable()) {
            if (tiles[cursorPos.y][cursorPos.x] instanceof MirrorTile) {
                game_info[0]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof SplitterTile) {
                game_info[1]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof CheckPointTile) {
                game_info[2]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof DoubleTile) {
                game_info[3]++;
            }
            tiles[cursorPos.y][cursorPos.x] = null;
        } else{
            System.out.println("Tile is not moveable");
        }
    }




    public void rotateSelectedTile(boolean levelEditor) {
        int mod = levelEditor ? 5 : 4;
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsRotatable()) {
            tiles[cursorPos.y][cursorPos.x].rotate(1,mod);
        }else {
            System.out.println("Tile is not rotateable");
        }
    }

/*
JSON STUFF
 */

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

    public void saveTempAs(String file_name) {
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


