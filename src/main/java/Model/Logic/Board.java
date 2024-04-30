package Model.Logic;
import Model.Tiles.*;
import Model.Tiles.GameTiles.*;

import java.awt.*;



/**
 * The Board class represents the game board.
 * It manages the game state, including tile placements, cursor position, and win conditions.
 * It also handles the logic for adding, removing, and rotating tiles.
 */
public class Board {

    private Tile[][] tiles; // Tiles on our board
    final private int boardSize; // Size of the board
    final private int squareSize; // Pixel size of the squares on the board
    private int mirrorsHit; // Number of mirrors hit by the laser
    private int targetsHit; // Number of targets hit by the laser

    private Point cursorPos; // Position of the cursor

    private GameInfo game_info; // Number of each tile type available and the number of targets
    private Tile selectedTile; // The tile currently selected by the player
    private boolean laserWasFired = false; // Whether the laser has been fired

    private boolean win; // Whether the player has won the level

    private String level = "0"; // The level of the game, can be a number for premade levels or "game_state", "temp" or custom levels.


    /**
     * Constructs a new Board instance with specified size, square size, and level.
     * Initializes the board to a starting state without win and a reset cursor position.
     *
     * @param boardSize the size of the board (number of tiles across and down)
     * @param squareSize the visual size of each tile in pixels
     * @param level the initial level configuration
     */
    public Board(int boardSize, int squareSize,String level) {
        this.boardSize = boardSize;
        this.squareSize = squareSize;
        this.win = false;
        // Set cardlevel based on level
        setCardLevel(level);

        // Reset cursorPos
        cursorPos = new Point(0, 0);


    }
    /**
     * Constructs a new Board instance with only a level. The board size and square size are set to default values.
     * Initializes the board to a starting state with no wins and a reset cursor position.
     *
     * @param level the initial level configuration
     */

    public Board(String level) {
        this.boardSize = 5;
        this.squareSize = 100;
        this.win = false;
        // Set cardlevel based on level
        setCardLevel(level);

        // Reset cursorPos
        cursorPos = new Point(0, 0);


    }


    //calculate the position of the tile
    private Point getTilePos(int x, int y) {

        return new Point(Math.min(x / squareSize,boardSize-1),  Math.min(y / squareSize,boardSize-1));
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile tile){
        if (tile != null ) {
            //if tile already selected set null
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

    // Get the laser tile on the board
    private Tile getLaserTile() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    return tiles[row][col];
                }
            }
        }
        return null;
    }

    // Get the game_info by index
//    public int get_game_info_by_index(int index) {
//        if (index < 0 || index > game_info.length - 1) {
//            throw new IllegalArgumentException("Index out of bounds");
//        }
//        return game_info[index];
//    }

    public GameInfo get_game_info() {
        return game_info;
    }

    public void set_game_info(GameInfo game_info) {
        this.game_info = game_info;
    }

    public void setMirrorsHit(int mirrorsHit) {
        this.mirrorsHit = mirrorsHit;
    }

    public void setTargetsHit(int targetsHit) {
        this.targetsHit = targetsHit;
    }

    public Boolean getWin() {
        return win;
    }

    public void resetWin() {
        win = false;
    }

    // check if wincondition is met
    public void checkWinCondition() {
        System.out.println("Mirrors hit: " + mirrorsHit + " Count mirrors: " + countMirrors());
        System.out.println("Targets hit: " + targetsHit + " Count targets: " + game_info.getTargets());
        if (mirrorsHit >= countMirrors() && targetsHit == game_info.getTargets()) {
            System.out.println("Win condition met");
            win = true;
        }
    }


    public void setLaserWasFired(boolean b) {
        laserWasFired = b;

    }

    public void setCursorLocation(int x, int y) {
        cursorPos = getTilePos(x, y);
    }



    public Point getCursorPos() {
        return cursorPos;
    }

    // Set the level of the game
    public void setCardLevel(String level) {
        //System.out.println("Setting card level to: " + level);
        this.level = level;
        //System.out.println("Setting card level to: " + level);
        // in constructor
        Card card = new Card(level);
        this.tiles = card.getCard();
        this.game_info = card.getPlaceableTiles();
    }

    public String getLevel() {
        if (level.equals("game_state")) {
            level = game_info.getLevel()+"";
        }
        return level;
    }

    // Check if a laser is needed
    public boolean laserNeeded() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                    return false;
                }
            }
        }
        return true;
    }

    // count number of mirrors placed on the board
    private int countMirrors() {
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

    //Add a tile
    public void addTile(boolean unSelectSelectedTileAfterPlacement) {
        // player has to select a tile first
        if (selectedTile != null) {
            Tile t;

            try {
                t = selectedTile.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            // the position needs to be empty for a tile to be placed
            if (tiles[cursorPos.y][cursorPos.x] == null) {
                // Check if a new tile can be placed
                if (t instanceof LaserTile && getLaserTile() != null) {
                    //System.out.println("Laser already exists");
                    return;
                } else if (t instanceof MirrorTile) {
                    if (game_info.getTileFromDictionary("MirrorTile") == 0) {
                        //System.out.println("No more mirror tiles");
                        return;
                    } else {
                        game_info.decrementAtKey(t.getClass().getSimpleName());
                    }

                } else if (t instanceof SplitterTile) {
                    if (game_info.getTileFromDictionary("SplitterTile") == 0) {
                        //System.out.println("No more splitter tiles");
                        return;
                    } else {
                        game_info.decrementAtKey(t.getClass().getSimpleName());
                    }
                } else if (t instanceof CheckPointTile) {
                    if (game_info.getTileFromDictionary("CheckPointTile") == 0) {
                        //System.out.println("No more checkpoint tiles");
                        return;
                    } else {
                        game_info.decrementAtKey(t.getClass().getSimpleName());
                    }
                } else if (t instanceof DoubleTile) {
                    if (game_info.getTileFromDictionary("DoubleTile") == 0) {
                        //System.out.println("No more double tiles");
                        return;
                    } else {
                        game_info.decrementAtKey(t.getClass().getSimpleName());
                    }
                }

                // only used in levelmaker
                if(unSelectSelectedTileAfterPlacement) selectedTile = null;

                //place tile
                tiles[cursorPos.y][cursorPos.x] = t;

            }
        }
    }


    // Remove a tile from the board
    public void removeTile() {
        //check if there is a tile and if it is moveable
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsMovable()) {
            // add one extra to placeable tiles
            if (tiles[cursorPos.y][cursorPos.x] instanceof MirrorTile) {
                game_info.incrementAtKey(tiles[cursorPos.y][cursorPos.x].getClass().getSimpleName());
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof SplitterTile) {
                game_info.incrementAtKey(tiles[cursorPos.y][cursorPos.x].getClass().getSimpleName());
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof CheckPointTile) {
                game_info.incrementAtKey(tiles[cursorPos.y][cursorPos.x].getClass().getSimpleName());
            } else if (tiles[cursorPos.y][cursorPos.x] instanceof DoubleTile) {
                game_info.incrementAtKey(tiles[cursorPos.y][cursorPos.x].getClass().getSimpleName());
            }
            //remove tile
            tiles[cursorPos.y][cursorPos.x] = null;
        }
    }



    // Rotate the tile at cursor position
    public void rotateSelectedTile(boolean levelEditor) {
        //used in levelmaker to check if a tile needs to be rotatable in custom level
        int mod = levelEditor ? 5 : 4; // If we have a level editor we can rotate 5 times, otherwise 4 times. Fifth rotation is for free rotation.
        // check if there is a tile and if it is rotatable
        if (tiles[cursorPos.y][cursorPos.x] != null && tiles[cursorPos.y][cursorPos.x].getIsRotatable()) {
            tiles[cursorPos.y][cursorPos.x].rotate(1,mod);
        }
    }

}


