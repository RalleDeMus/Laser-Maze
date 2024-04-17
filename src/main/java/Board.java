import Tiles.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


//make singleton
public class Board {
    // Board size
    // Tiles array
    // Cursor tile

    int boardSize;
    int squareSize;
    Tile[][] tiles;

    Tile cursorTile;
    Point cursorPos;

    Tile selectedTile = new Tile();

    boolean laserWasFired = false;


    // Laser tree ???

    // Constructor
        // Gets the board size
        // Initializes the tiles array based json file
        // Initializes the cursor tile
        // Reads from the asset server
    public Board(int boardSize, int squareSize) {
        this.boardSize = boardSize;
        this.tiles = new Tile[boardSize][boardSize];
        this.cursorTile = new Tile();
        this.cursorPos = new Point(0, 0);
        this.squareSize = squareSize;

        // Initialize the tiles array based on the json file
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                tiles[row][col] = null;
                // This needs to be based on the json file
            }
        }

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

        if(laserWasFired) {
            drawLaser(g);
        }
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


    public void fireLaser(){
        laserWasFired = true;

    }

    // Construct laser tree
    List<PointStringPair>  constructLaserTree() {
        //String[][] laserHasHit = new String[boardSize][boardSize];
        List<PointStringPair> laserList = new ArrayList<>();


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



            //laserHasHit[laser.getY()][laser.getX()] = 1;

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

                String fromDir = String.valueOf(current.getOrientation());
                String toDir = String.valueOf(current.getOrientation()) + "_";

                // Set the current as a hit on laserHasHit
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
                        toDir = "8_";
                    }


                    if (tile instanceof SplitterTile) {
                        // If the tile is a splitter, add a new lasers here aswell as the rotated one!
                        Laser adding = new Laser(current.getX()+orientationToPoint(current.getOrientation()).x, current.getY()+orientationToPoint(current.getOrientation()).y,current.getOrientation());
                        lasers.add(adding);
                    }

                    if (tile.getPass()[laserCorrected] == 0) {
                        // If the laser is not allowed to pass, stop the laser and add no more tiles
                        System.out.println("Mirror? " + tile);
                        System.out.println("Mirror blocked: " + Arrays.toString(tile.getPass()));

                    } else {

                        System.out.println("Evaluating tile: " + "\n" + laserCorrected);
                        int rotateBy = tile.getMirror()[laserCorrected];
                        int nextLaserOrientation = (current.getOrientation() + rotateBy) % 4;

                        System.out.println(rotateBy);
                        System.out.println(nextLaserOrientation);
                        Laser adding = new Laser(current.getX() + orientationToPoint(nextLaserOrientation).x, current.getY() + orientationToPoint(nextLaserOrientation).y, nextLaserOrientation);
                        lasers.add(adding);
                        System.out.println("At mirror, adding next: " + adding.toString());

                        toDir = String.valueOf(nextLaserOrientation);
                        toDir += (tile instanceof SplitterTile) ? fromDir : "_";
                    }


                } else {
                    // Add a next laser to the queue if no tile is found
                    Laser adding = new Laser(current.getX()+orientationToPoint(current.getOrientation()).x, current.getY()+orientationToPoint(current.getOrientation()).y,current.getOrientation());
                    lasers.add(adding);

                    System.out.println("At empty tile, adding next: " + adding.toString());
                }
                laserList.add(new PointStringPair(new Point(current.getX(), current.getY()), String.valueOf(fromDir) + String.valueOf(toDir)));


            }




            System.out.println("Targets hit: " + targetsHit);
            System.out.println("Hit lasers: " + hitLasers);


        } else {
            System.out.println("No laser tile found");
        }
        return laserList;
    }

    void drawLaser(Graphics g){
        List<PointStringPair> laserMap = constructLaserTree();

        for (PointStringPair pair : laserMap) {
            int j = pair.getPoint().x;
            int i = pair.getPoint().y;

            String value = pair.getValue();

            if (!value.equals("___")) {
                BufferedImage image = AssetServer.getInstance().getImage("laserRay");


                if (value.charAt(0) != '_') {
                    if (value.charAt(1) == '8') {
                        int direction = Character.getNumericValue(value.charAt(0));
                        g.drawImage(ImageHandler.rotateImage(AssetServer.getInstance().getImage("laserRayTarget"),90*direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                    } else {
                        int direction = Character.getNumericValue(value.charAt(0));
                        g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                    }
                }

                if (value.charAt(1) != '_') {
                    if (value.charAt(1) != '8') {
                        int direction = Character.getNumericValue(value.charAt(1)) + 2;
                        g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                    }
                }


                if (value.charAt(2) != '_') {
                    int direction = Character.getNumericValue(value.charAt(2))+2;
                    g.drawImage(ImageHandler.rotateImage(image,90*direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                }
            }

            System.out.println("LaserMap: " + pair.getPoint() + " " + pair.getValue());
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
        //System.out.println("Tiles.Tile clicked: " + cursorPos.x + " " + cursorPos.y);
        if (t instanceof LaserTile && getLaserTile() != null) {
            System.out.println("Laser already exists");
            return;
        } else {
            System.out.println("Adding tile: " + (t instanceof LaserTile ? "Laser" : "Mirror"));
        }
        tiles[cursorPos.y][cursorPos.x] = t;
    }


    // Remove a tile
    public void removeTile() {
        tiles[cursorPos.y][cursorPos.x] = null; //empty tile?
    }


    public void setCursorPos(int x, int y) {
        cursorPos = getTilePos(x, y);
    }

    public Point getCursorPos() {
        return cursorPos;
    }

    public void rotateSelectedTile() {
        selectedTile.rotate();
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


}


