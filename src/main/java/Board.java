import Tiles.*;

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
    // Board size
    // Tiles array
    // Cursor tile

    int boardSize;
    int squareSize;
    Tile[][] tiles;

    Card card;
    Point cursorPos;

    private int placeableTiles[];
    Tile selectedTile = new LaserTile(true, true);


    // Laser tree ???

    // Constructor
        // Gets the board size
        // Initializes the tiles array based json file
        // Initializes the cursor tile
        // Reads from the asset server
    public Board(int boardSize, int squareSize) {
        this.boardSize = boardSize;
        this.card = new Card(1);
        this.tiles = card.getCard();
        this.cursorPos = new Point(0, 0);
        this.squareSize = squareSize;
        placeableTiles = card.getPlaceableTiles();



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

    public Point getTilePos(int x, int y) {

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
                //System.out.print(tiles[row][col]!=null?tiles[row][col] instanceof Tiles.LaserTile? "L" : "N":"0");

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

                    System.out.println("Laser out of bounds");
                    continue;
                }
                hitLasers.add(current);

                // Set the current as a hit on laserHasHit
                laserHasHit[current.getY()][current.getX()] = 1;
                //System.out.println("Laser at: " + current.getX() + " " + current.getY() + " orientation: " + current.getOrientation();

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
    public void addTile(Tile t) {

        if (t instanceof LaserTile && getLaserTile() != null) {
            System.out.println("Laser already exists");
            return;
        } else if (t instanceof MirrorTile) {
            if (placeableTiles[0] == 0){
                System.out.println("No more mirror tiles");
                return;
            }
            else{placeableTiles[0]--;}

        } else if (t instanceof SplitterTile) {
            if (placeableTiles[1] == 0){
                System.out.println("No more splitter tiles");
                return;
            }
            else{placeableTiles[1]--;}
        } else if (t instanceof CheckPointTile) {
            if (placeableTiles[2] == 0){
                System.out.println("No more checkpoint tiles");
                return;
            }
            else{placeableTiles[2]--;}
        } else if (t instanceof DoubleTile) {
            if (placeableTiles[3] == 0){
                System.out.println("No more double tiles");
                return;
            }
            else{placeableTiles[3]--;}
        } else if (t instanceof CellBlockerTile) {
            if (placeableTiles[4] == 0){
                System.out.println("No more cell blocker tiles");
                return;
            }
            else{placeableTiles[4]--;}

        } else{
            System.out.println("Adding tile: " + (t instanceof LaserTile ? "Laser" : "Mirror"));
        }
        tiles[cursorPos.y][cursorPos.x] = t;
    }


    // Remove a tile
    public void removeTile() {
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsMoveable()) {
            if (tiles[cursorPos.y][cursorPos.x] instanceof MirrorTile) {
                placeableTiles[0]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof SplitterTile) {
                placeableTiles[1]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof CheckPointTile) {
                placeableTiles[2]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof DoubleTile) {
                placeableTiles[3]++;
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof CellBlockerTile) {
                placeableTiles[4]++;
            }
            tiles[cursorPos.y][cursorPos.x] = null;
        } else{
            System.out.println("Tile is not moveable");
        }
    }


    public void setCursorPos(int x, int y) {
        cursorPos = getTilePos(x, y);
    }

    public Point getCursorPos() {
        return cursorPos;
    }

    public void rotateSelectedTile() {
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
    public void saveGameState() {
        JSONObject boardState = new JSONObject();
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
                    // hmmm can add other properties for different tile types
                    //

                    tilesArray.put(tileObject);
                }
            }
        }

        boardState.put("tiles", tilesArray);

        // Save the JSON object to a file
        try (FileWriter file = new FileWriter("level_1.json")) {
            file.write(boardState.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + boardState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


