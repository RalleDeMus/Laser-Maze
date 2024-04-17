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
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;

//make singleton
public class Board {
    private static Board instance;



    static int boardSize;
    static int squareSize;
    public static Tile[][] tiles;

    Card card;
    static Point cursorPos;

    private static int[] game_info;
    Tile selectedTile = new LaserTile(true, true);


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
            instance = new Board(5, 120);
        }
        return instance;
    }

    // Draw the board
        // Draw the tiles: Empty or with a mirror
        // Draw the selected tile
        // Draw the cursor tile
    public void drawBoard(Graphics g) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null) {
                    // Draws the tile if it is not empty
                    g.drawImage(tiles[row][col].getImage(), col * squareSize, row * squareSize, squareSize, squareSize, null);

                } else {
                    // Draws an empty tile if empty
                    g.drawImage(AssetServer.getInstance().getImage("empty"), col * squareSize, row * squareSize, squareSize, squareSize, null);
                }


            }
        }

        BufferedImage cursorImage = ImageHandler.transImage(selectedTile.getImage(), 0.6f);
        g.drawImage(cursorImage, cursorPos.x * squareSize, cursorPos.y * squareSize, squareSize, squareSize, null);
    }

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

    // Construct laser tree
    public void constructLaserTree() {
        Tile laserTile = null;

        Laser laser = new Laser(0,0,0);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    laserTile = tiles[row][col];
                    laser.setPos(col, row);
                    laser.setOrientation(laserTile.getOrientation());

                }
                //System.out.print(tiles[row][col]!=null?tiles[row][col] instanceof Model.Tiles.LaserTile? "L" : "N":"0");

            }
            //System.out.println();
        }
        System.out.println();
        if (laserTile != null) {
            System.out.println("Constructing laser tree");

            int[][] laserHasHit = new int[boardSize][boardSize];
            laserHasHit[laser.getY()][laser.getX()] = 1;

            int targetsHit = 0;

            Queue<Laser> lasers = new LinkedList<>();
            List<Laser> hitLasers = new LinkedList<>();
            lasers.add(new Laser(laser.getX()+orientationToPoint(laser.getOrientation()).x, laser.getY()+orientationToPoint(laser.getOrientation()).y,laser.getOrientation()));


            while (!lasers.isEmpty()) {
                Laser current = lasers.remove();

                // Is the laser out of bounds?
                if (current.getX() < 0 || current.getX() >= boardSize || current.getY() < 0 || current.getY() >= boardSize) {

                    System.out.println("Model.Logic.Laser out of bounds");
                    continue;
                }
                hitLasers.add(current);

                // Set the current as a hit on laserHasHit
                laserHasHit[current.getY()][current.getX()] = 1;
                //System.out.println("Model.Logic.Laser at: " + current.getX() + " " + current.getY() + " orientation: " + current.getOrientation();

                // Is there a tile at the current position?
                if (tiles[current.getY()][current.getX()] != null) {
                    // Get the tile
                    Tile tile = tiles[current.getY()][current.getX()];

                    // Get the corrected way that the laser is facing in respect to the tile
                    System.out.println("LaserC: " + tile.getOrientation() + " " + current.getOrientation() + " " + subMod(tile.getOrientation(),current.getOrientation(),4));
                    int laserCorrected = subMod(tile.getOrientation(),current.getOrientation(),4);

                    // Check if the tile is a target
                    if (tile.getTarget()[laserCorrected] == 1) {
                        // If so, increment the targets hit
                        System.out.println("Target HIT: " + laserCorrected + " gettarget: " + Arrays.toString( tile.getTarget()));
                        targetsHit++;
                    }

                    if (tile.getPass()[laserCorrected] == 0) {
                        // If the laser is not allowed to pass, stop the laser and add no more tiles
                        System.out.println("Mirror? " + tile);
                        System.out.println("Mirror blocked: " + Arrays.toString(tile.getPass()));
                        continue;
                    } else if (tile instanceof SplitterTile) {
                        // If the tile is a splitter, add a new lasers here aswell as the rotated one!
                        Laser adding = new Laser(current.getX()+orientationToPoint(current.getOrientation()).x, current.getY()+orientationToPoint(current.getOrientation()).y,current.getOrientation());
                        lasers.add(adding);

                    }

                    System.out.println("Evaluating tile: " + "\n" + laserCorrected);
                    int rotateBy = tile.getMirror()[laserCorrected];
                    int nextLaserOrientation = (current.getOrientation() + rotateBy) % 4;
                    System.out.println(rotateBy);
                    System.out.println(nextLaserOrientation);
                    Laser adding = new Laser(current.getX()+orientationToPoint(nextLaserOrientation).x, current.getY()+orientationToPoint(nextLaserOrientation).y,nextLaserOrientation);
                    lasers.add(adding);
                    System.out.println("At mirror, adding next: " + adding.toString());

                } else {
                    // Add a next laser to the queue if no tile is found
                    Laser adding = new Laser(current.getX()+orientationToPoint(current.getOrientation()).x, current.getY()+orientationToPoint(current.getOrientation()).y,current.getOrientation());
                    lasers.add(adding);

                    System.out.println("At empty tile, adding next: " + adding.toString());
                }

            }



            for(int i = 0; i < boardSize; i++) {
                for(int j = 0; j < boardSize; j++) {
                    System.out.print(laserHasHit[i][j]);
                }
                System.out.println();
            }

            System.out.println("Targets hit: " + targetsHit);
            System.out.println("Hit lasers: " + hitLasers);


        } else {
            System.out.println("No laser tile found");
        }
    }

    int subMod(int a, int b, int mod) {
        return (a - b + mod) % mod;
    }


    // Check if win condition is met after laser tree work
    public boolean checkWinCondition() {
        return false;
    }


    // Add the cursor tile to the board and check if placement is valid
    public static void addTile(Tile t) {
        if (tiles[cursorPos.y][cursorPos.x] != null){
            System.out.println("Tile occupied");
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
                game_info[0]--;}

        } else if (t instanceof SplitterTile) {
            if (game_info[1] == 0){
                System.out.println("No more splitter tiles");
                return;
            }
            else{
                game_info[1]--;}
        } else if (t instanceof CheckPointTile) {
            if (game_info[2] == 0){
                System.out.println("No more checkpoint tiles");
                return;
            }
            else{
                game_info[2]--;}
        } else if (t instanceof DoubleTile) {
            if (game_info[3] == 0){
                System.out.println("No more double tiles");
                return;
            }
            else{
                game_info[3]--;}
        } else if (t instanceof CellBlockerTile) {
            if (game_info[4] == 0){
                System.out.println("No more cell blocker tiles");
                return;
            }
            else{
                game_info[4]--;}

        } else{
            System.out.println("Adding tile: " + (t instanceof LaserTile ? "Laser" : "Mirror"));
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


}


