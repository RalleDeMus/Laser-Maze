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
    private static Board instance;

    public static Tile[][] tiles;
    static int boardSize;
    static int squareSize;
    private int mirrorsHit;
    private int targetsHit;

    Card card;
    static Point cursorPos;

    private static int[] game_info;
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
    private Board(int boardSize, int squareSize) { // Yes
        Board.boardSize = boardSize;
        this.card = new Card("0");
        tiles = card.getCard();
        cursorPos = new Point(0, 0);
        Board.squareSize = squareSize;
        game_info = card.getPlaceableTiles();



    }

    public static Board getInstance(){
        if (instance == null){
            instance = new Board(5, 100);
        }
        return instance;
    }


/*
Getters and setters (Maybe we can make some of these their own?)
*/

    public static Point getTilePos(int x, int y) {

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
    public static Tile getLaserTile() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    return tiles[row][col];
                }
            }
        }
        return null;
    }

    public int get_game_info(int index) {
        if (index < 0 || index > game_info.length - 1) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return game_info[index];
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


    public void setLaserWasFired(boolean b) {
        laserWasFired = b;

    }
    public static void setCursorPos(int x, int y) {
        cursorPos = getTilePos(x, y);
    }

    public Point getCursorPos() {
        return cursorPos;
    }

    public void setCardLevel(String level) {
        //System.out.println("Setting card level to: " + level);
        this.level = level;
        System.out.println("Setting card level to: " + level);
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

/*
STUFF TO CONSTRUCT THE LASER TREE
*/

    // Construct laser tree
    public List<PointStringPair>  constructLaserTree() { // REMOVE
        long time = System.nanoTime();
        if (allMirrorsUsed()) {
            List<PointStringPair> laserList = new ArrayList<>();

            for(int t = 0; t < 1; t++) {
                laserList = new ArrayList<>();
                Tile laserTile = null;

                Laser laser = new Laser(0, 0, 0);

                for (int row = 0; row < boardSize; row++) {
                    for (int col = 0; col < boardSize; col++) {
                        if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                            laserTile = tiles[row][col];
                            laser.setPos(col, row);
                            laser.setOrientation(laserTile.getOrientation());

                        }
                        //System.out.print(tiles[row][col]!=null?tiles[row][col] instanceof Tiles.LaserTile? "L" : "N":"0");

                    }
                    //System.out.println();
                }
                //System.out.println();
                if (laserTile != null) {
                    //System.out.println("Constructing laser tree");

                    targetsHit = 0;

                    Queue<Laser> lasers = new LinkedList<>();
                    Set<Laser> lasersList = new HashSet<>();



                    List<Laser> hitLasers = new LinkedList<>();
                    lasers.add(new Laser(laser.getX() + orientationToPoint(laser.getOrientation()).x, laser.getY() + orientationToPoint(laser.getOrientation()).y, laser.getOrientation()));

                    mirrorsHit = 0;

                    while (!lasers.isEmpty()) {
                        Laser current = lasers.remove();

                        // Is the laser out of bounds?
                        if (current.getX() < 0 || current.getX() >= boardSize || current.getY() < 0 || current.getY() >= boardSize) {

                            //System.out.println("Laser out of bounds");
                            continue;
                        }
                        hitLasers.add(current);

                        String fromDir = String.valueOf(current.getOrientation());
                        String toDir = String.valueOf(current.getOrientation()) + "_";

                        // Set the current as a hit on laserHasHit
                        //System.out.println("Laser at: " + current.getX() + " " + current.getY() + " orientation: " + current.getOrientation();

                        // Is there a tile at the current position?
                        if (tiles[current.getY()][current.getX()] != null) {
                            // Get the tile
                            Tile tile = tiles[current.getY()][current.getX()];
                            if (!(tile instanceof CellBlockerTile)) {
                                mirrorsHit++;
                            }


                            // Get the corrected way that the laser is facing in respect to the tile
                            int laserCorrected = subMod(tile.getOrientation(), current.getOrientation(), 4);

                            // Check if the tile is a target
                            if (tile.getTarget()[laserCorrected] == 1) {
                                // If so, increment the targets hit
                                //System.out.println("Target HIT: " + laserCorrected + " gettarget: " + Arrays.toString(tile.getTarget()));
                                targetsHit++;
                                toDir = "8_";
                            }


                            if (tile instanceof SplitterTile) {
                                // If the tile is a splitter, add a new lasers here aswell as the rotated one!
                                Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                                if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}
                            }

                            if (tile.getPass()[laserCorrected] == 0) {
                                // If the laser is not allowed to pass, stop the laser and add no more tiles
                                //System.out.println("Mirror? " + tile);
                                //System.out.println("Mirror blocked: " + Arrays.toString(tile.getPass()));
                                if (tile.getTarget()[laserCorrected] == 0) continue;

                            } else {

                                //System.out.println("Evaluating tile: " + "\n" + laserCorrected);
                                int rotateBy = tile.getMirror()[laserCorrected];
                                int nextLaserOrientation = (current.getOrientation() + rotateBy) % 4;

                                //System.out.println(rotateBy);
                                //System.out.println(nextLaserOrientation);
                                Laser adding = new Laser(current.getX() + orientationToPoint(nextLaserOrientation).x, current.getY() + orientationToPoint(nextLaserOrientation).y, nextLaserOrientation);
                                if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}                            //System.out.println("At mirror, adding next: " + adding.toString());

                                toDir = String.valueOf(nextLaserOrientation);
                                toDir += (tile instanceof SplitterTile) ? fromDir : "_";
                            }


                        } else {
                            // Add a next laser to the queue if no tile is found
                            Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                            if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}


                            //System.out.println("At empty tile, adding next: " + adding.toString());
                        }

                        laserList.add(new PointStringPair(new Point(current.getX(), current.getY()), String.valueOf(fromDir) + String.valueOf(toDir)));


                    }


                    //System.out.println("Targets hit: " + targetsHit);
                    //System.out.println("Hit lasers: " + hitLasers);


                } else {
                    //System.out.println("No laser tile found");
                }
            }
            System.out.println( "Time: " + (System.nanoTime()-time)/1000000000);

            return laserList;
        } else {

            return null;
        }


    }

    Boolean canAddLaser(Set<Laser> lasersList, Laser adding) { // REMOVE
        //Don't add if adding is already in the queue
        for (Laser laser : lasersList) {
            if (laser.Equals(adding)){
                System.out.println("Laser already exists");
                return false;
            }
        }
        return true;
    }

    int subMod(int a, int b, int mod) {
        return (a - b + mod) % mod;
    } // REMOVE

    public static Point orientationToPoint(int orientation) { // REMOVE
        switch (orientation) {
            case 1:
                return new Point(0, 1); // Up
            case 0:
                return new Point(1, 0); // Right
            case 3:
                return new Point(0, -1); // Down
            case 2:
                return new Point(-1, 0); // Left
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }


    public boolean allMirrorsUsed(){ // REMOVE
        int placeabletiles = 0;
        for (int i = 0; i < 4; i++) {
            placeabletiles += game_info[i];
        }
        return placeabletiles == 0;
    }


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
    public static void removeTile() {
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




    public static void rotateSelectedTile(boolean levelEditor) {
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

    public static void saveGameState(String filename) {
        JSONObject gameInfo = new JSONObject();
        JSONArray tilesArray = new JSONArray();

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


