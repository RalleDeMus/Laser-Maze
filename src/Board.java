public class Board {
    // Board size
    // Tiles array
    // Cursor tile

    int boardSize;
    Tile[][] tiles;

    Tile cursorTile;

    // Laser tree ???


    // Constructor (json file)
        // Gets the board size
        // Initializes the tiles array based json file
        // Initializes the cursor tile
    public Board(int boardSize, int squareSize, assetServer) {
        this.boardSize = boardSize;
        this.tiles = new Tile[boardSize][boardSize];
        this.cursorTile = new Tile();
    }

    // Draw the board
        // Draw the tiles: Empty or with a mirror
        // Draw the selected tile
        // Draw the cursor tile


    // Construct laser tree

    // Check if win condition is met after laser tree

    // Add the cursor tile to the board and check if placement is valid
    public void addTile(int x, int y) {
        tiles[x,y] = cursorTile;
    }

    // Remove a tile
    public void removeTile(int x, int y) {
        tiles[x,y] = null; //empty tile?
    }

    // Rotate the cursor tile
}


