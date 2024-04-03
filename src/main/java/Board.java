import java.awt.*;
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

    AssetServer assetServer;

    // Laser tree ???

    // Constructor
        // Gets the board size
        // Initializes the tiles array based json file
        // Initializes the cursor tile
        // Reads from the asset server
    public Board(int boardSize, int squareSize, AssetServer assetServer) {
        this.boardSize = boardSize;
        this.tiles = new Tile[boardSize][boardSize];
        this.cursorTile = new Tile();
        this.assetServer = assetServer;
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
                    g.drawImage(assetServer.getImage("empty"), col * squareSize, row * squareSize, squareSize, squareSize, null);
                }


            }
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
                if (tiles[row][col] != null && tiles[row][col].getLaser()) {
                    return tiles[row][col];
                }
                //System.out.print(tiles[row][col]!=null?tiles[row][col].getLaser()? "L" : "N":"0");
            }
            //System.out.println();
        }
        return null;
    }

    // Construct laser tree
    public void constructLaserTree() {
        Tile laserTile = getLaserTile();
        if (laserTile != null) {
            // Construct the laser tree
            System.out.println("Constructing laser tree");

        } else {
            System.out.println("No laser tile found");
        }
    }


    // Check if win condition is met after laser tree work
    public boolean checkWinCondition() {
        return false;
    }


    // Add the cursor tile to the board and check if placement is valid
    public void addTile(Tile t) {
        System.out.println("Tile clicked: " + cursorPos.x + " " + cursorPos.y);

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
}


