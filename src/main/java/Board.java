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

    Tile selectedTile = new Tile();
    Cursor cursor;

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
        this.cursor = new Cursor();

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

    public Point getTile(int x, int y) {

        return new Point(Math.min(x / squareSize,boardSize-1),  Math.min(y / squareSize,boardSize-1));
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(String imageName){
        selectedTile = new Tile();
        selectedTile.setImage(assetServer.getImage(imageName));
    }

    // Construct laser tree
    public void constructLaserTree() {
    }


    // Check if win condition is met after laser tree work
    public boolean checkWinCondition() {
        return false;
    }


    // Add the cursor tile to the board and check if placement is valid
    public void addTile(int x, int y, Tile t) {
        Point p = getTile(x, y);
        System.out.println("Tile clicked: " + p);

        tiles[p.y][p.x] = t;
    }


    // Remove a tile
    public void removeTile(int x, int y) {
        Point p = getTile(x, y);
        tiles[p.y][p.x] = null; //empty tile?
    }


    // Rotate the cursor tile
}


