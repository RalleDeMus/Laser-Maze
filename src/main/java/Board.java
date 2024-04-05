import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        Point laserPos = null;


        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    laserTile = tiles[row][col];
                    laserPos = new Point(col, row);
                }
                //System.out.print(tiles[row][col]!=null?tiles[row][col] instanceof LaserTile? "L" : "N":"0");

            }
            //System.out.println();
        }
        System.out.println();
        if (laserTile != null) {
            System.out.println("Constructing laser tree");
            //System.out.println(distMod(0,2,4));

            int[][] laserHasHit = new int[boardSize][boardSize];
            laserHasHit[laserPos.y][laserPos.x] = 1;

            int orientation = laserTile.getOrientation();

            int targetsHit = 0;

            while (true) {
                if (laserPos.x+orientationToPoint(orientation).x < 0 || laserPos.x+orientationToPoint(orientation).x >= boardSize || laserPos.y+orientationToPoint(orientation).y < 0 || laserPos.y+orientationToPoint(orientation).y >= boardSize) {
                    System.out.println("Laser out of bounds");
                    break;
                }
                laserPos = new Point(laserPos.x+orientationToPoint(orientation).x, laserPos.y+orientationToPoint(orientation).y);


                laserHasHit[laserPos.y][laserPos.x] = 1;

                if (tiles[laserPos.y][laserPos.x] != null) {

                    Tile mirrorTile = tiles[laserPos.y][laserPos.x];

                    int laserCorrected = subMod(mirrorTile.getOrientation(),orientation,4);

                    System.out.println(orientation + " " + mirrorTile.getOrientation() + " " +laserCorrected + " " + mirrorTile.getPass()[laserCorrected]);

                    if (mirrorTile.getTarget()[laserCorrected] == 1) {
                        System.out.println("Target HIT!");
                    }

                    if (mirrorTile.getPass()[laserCorrected] == 0) {
                        System.out.println("Mirror blocked");
                        break;
                    }

                    int rotateBy = mirrorTile.getMirror()[laserCorrected];
                    orientation = (orientation + rotateBy) % 4;
                    System.out.println("Mirror" + rotateBy);

                }

            }



            for(int i = 0; i < boardSize; i++) {
                for(int j = 0; j < boardSize; j++) {
                    System.out.print(laserHasHit[i][j]);
                }
                System.out.println();
            }

            System.out.println("Targets hit: " + targetsHit);


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
        //System.out.println("Tile clicked: " + cursorPos.x + " " + cursorPos.y);
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


