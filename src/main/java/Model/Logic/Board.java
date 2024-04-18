package Model.Logic;
import Controller.AssetServer;
import Controller.ImageHandler;
import Model.Tiles.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//import View.BoardPage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


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
    Tile selectedTile = new LaserTile(true, true);
    boolean laserWasFired = false;





    // Model.Logic.Laser tree ???

    // Constructor
        // Gets the board size
        // Initializes the tiles array based json file
        // Initializes the cursor tile
        // Reads from the asset server
    private Board(int boardSize, int squareSize) {
        this.boardSize = boardSize;
        this.card = new Card("0");
        this.tiles = card.getCard();
        this.cursorPos = new Point(0, 0);
        this.squareSize = squareSize;
        this.game_info = card.getPlaceableTiles();



    }

    public static Board getInstance(){
        if (instance == null){
            instance = new Board(5, 100);
        }
        return instance;
    }

    // Draw the board
        // Draw the tiles: Empty or with a mirror
        // Draw the selected tile
        // Draw the cursor tile


    public static Point getTilePos(int x, int y) {

        return new Point(Math.min(x / squareSize,boardSize-1),  Math.min(y / squareSize,boardSize-1));
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile tile){
        selectedTile = tile;
        //selectedTile.setImage(assetServer.getImage(imageName));
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

    public boolean isLaserFired() {
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


    public void fireLaser(){
        laserWasFired = true;

    }

    // Construct laser tree
    public List<PointStringPair>  constructLaserTree() {
        if (allMirrorsUsed() == true) {
            List<PointStringPair> laserList = new ArrayList<>();


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
            System.out.println();
            if (laserTile != null) {
                System.out.println("Constructing laser tree");

                //laserHasHit[laser.getY()][laser.getX()] = 1;

                targetsHit = 0;

                Queue<Laser> lasers = new LinkedList<>();
                List<Laser> hitLasers = new LinkedList<>();
                lasers.add(new Laser(laser.getX() + orientationToPoint(laser.getOrientation()).x, laser.getY() + orientationToPoint(laser.getOrientation()).y, laser.getOrientation()));

                mirrorsHit = 0;

                while (!lasers.isEmpty()) {
                    Laser current = lasers.remove();

                    // Is the laser out of bounds?
                    if (current.getX() < 0 || current.getX() >= boardSize || current.getY() < 0 || current.getY() >= boardSize) {

                        System.out.println("Laser out of bounds");
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
                        //System.out.println("LaserC: " + tile.getOrientation() + " " + current.getOrientation() + " " + subMod(tile.getOrientation(), current.getOrientation(), 4));
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
                            lasers.add(adding);
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
                            lasers.add(adding);
                            //System.out.println("At mirror, adding next: " + adding.toString());

                            toDir = String.valueOf(nextLaserOrientation);
                            toDir += (tile instanceof SplitterTile) ? fromDir : "_";
                        }


                    } else {
                        // Add a next laser to the queue if no tile is found
                        Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                        lasers.add(adding);

                        //System.out.println("At empty tile, adding next: " + adding.toString());
                    }
                    laserList.add(new PointStringPair(new Point(current.getX(), current.getY()), String.valueOf(fromDir) + String.valueOf(toDir)));


                }


                System.out.println("Targets hit: " + targetsHit);
                System.out.println("Hit lasers: " + hitLasers);


            } else {
                System.out.println("No laser tile found");
            }
            return laserList;
        } else {
            System.out.println("Not all mirrors used");
            return null;
        }

    }

    int subMod(int a, int b, int mod) {
        return (a - b + mod) % mod;
    }


    // Check if win condition is met after laser tree work
    public boolean checkWinCondition() {

        if (mirrorsHit == countMirrors() && targetsHit == game_info[5]){
            System.out.println("Win condition met");
            return true;
        } else {
            System.out.println("Win condition not met - all mirrors not used OR not all mirrors hit");
            System.out.println("Mirrors hit: " + mirrorsHit + " Count mirrors: " + countMirrors());
            System.out.println("Targets hit: " + targetsHit + " Count targets: " + game_info[5]);
            return false;
        }

    }



    // Add the cursor tile to the board and check if placement is valid
    public static void addTile(Tile t) {
        if (tiles[cursorPos.y][cursorPos.x] != null){
            //System.out.println("Tile occupied");
        } else {
        if (t instanceof LaserTile && getLaserTile() != null) {
            System.out.println("Laser already exists");
            return;
        } else if (t instanceof MirrorTile) {
            if (game_info[0] == 0){
                System.out.println("No more mirror tiles");
                return;
            }
            else{
                game_info[0]--;
            }

        } else if (t instanceof SplitterTile) {
            if (game_info[1] == 0){
                System.out.println("No more splitter tiles");
                return;
            }
            else{
                game_info[1]--;
            }
        } else if (t instanceof CheckPointTile) {
            if (game_info[2] == 0){
                System.out.println("No more checkpoint tiles");
                return;
            }
            else{
                game_info[2]--;
            }
        } else if (t instanceof DoubleTile) {
            if (game_info[3] == 0){
                System.out.println("No more double tiles");
                return;
            }
            else{
                game_info[3]--;
            }
        } else if (t instanceof CellBlockerTile) {
            if (game_info[4] == 0){
                System.out.println("No more cell blocker tiles");
                return;
            }
            else{
                game_info[4]--;
            }

        } else{
            //System.out.println("Adding tile: " + (t instanceof LaserTile ? "Laser" : "Mirror"));
        }


            tiles[cursorPos.y][cursorPos.x] = t;
        }
    }


    // Remove a tile
    public static void removeTile() {
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsMoveable()) {
            if (tiles[cursorPos.y][cursorPos.x] instanceof MirrorTile) {
                game_info[0]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof SplitterTile) {
                game_info[1]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof CheckPointTile) {
                game_info[2]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof DoubleTile) {
                game_info[3]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof CellBlockerTile) {
                game_info[4]++;
            }
            tiles[cursorPos.y][cursorPos.x] = null;
        } else{
            System.out.println("Tile is not moveable");
        }
    }


    public static void setCursorPos(int x, int y) {
        cursorPos = getTilePos(x, y);
    }

    public Point getCursorPos() {
        return cursorPos;
    }

    public static void rotateSelectedTile() {
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsRotateable()) {tiles[cursorPos.y][cursorPos.x].rotate();}else {
            System.out.println("Tile is not rotateable");
        }
    }

    public static Point orientationToPoint(int orientation) {
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
    public static void saveGameState() {
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
                    tileObject.put("rotatable", tile.getIsRotateable());

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
        game_info_JSON.put("CellBlockerTiles", game_info[4]);
        game_info_JSON.put("targets", game_info[5]);

        // Create the root JSON object to hold both "gameinfo" and "extra tiles"
        JSONObject boardState = new JSONObject();
        boardState.put("gameinfo", gameInfo);
        boardState.put("extra tiles", game_info_JSON);

        // Save the JSON object to a file
        try (FileWriter file = new FileWriter("game_state.json")) {
            file.write(boardState.toString(4)); // Write with indentation for readability
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + boardState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCardLevel(String level) {
        this.card = new Card(level);
        this.tiles = card.getCard();
        this.game_info = card.getPlaceableTiles();
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

    public boolean allMirrorsUsed(){
        int placeabletiles = 0;
        for (int i = 0; i < 5; i++) {
            placeabletiles += game_info[i];
        }
        return placeabletiles == 0;
    }



}


