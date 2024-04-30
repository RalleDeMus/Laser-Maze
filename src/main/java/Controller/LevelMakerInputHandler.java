package Controller;

import Model.Logic.Board;
import Model.Tiles.GameTiles.*;
import Model.Tiles.Tile;
import Model.Tiles.TileInfo;
import View.Pages.LevelMakerPage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Level maker input handler class.
 * This class is responsible for handling the input from the user on the level maker.
 */
public class LevelMakerInputHandler extends BoardInputHandler {

    /**
     * Constructor for the LevelMakerInputHandler class.
     * See BoardInputHandler for more information on the parameters.
     */
    public LevelMakerInputHandler(Board board, LevelMakerPage panel, int yOffset, boolean removeTileAfterPlace){
        super(board, panel, yOffset, removeTileAfterPlace);
    }

    //rotate tile when R key is pressed
    //select tiles when 1-6 key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        List<Tile> tiles = getTiles();

        if (e.getKeyCode() == KeyEvent.VK_R) {
            board.rotateSelectedTile(true);
        }

        int keyCode = e.getKeyCode();
        int index = -1;
        if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
            index = keyCode - KeyEvent.VK_1; // VK_1 maps to index 0
        } else if (keyCode == KeyEvent.VK_0) {
            index = 9; // VK_0 maps to index 9
        }
        if (index != -1 && index < getTiles().size()) {
            board.setSelectedTile(getTiles().get(index));
        }

        TileInfo.getTiles(false);
        panel.repaint();
    }

    private List<Tile> getTiles() {
        List<Tile> tiles = new ArrayList<>();
        Set<Class<?>> addedClasses = new HashSet<>();  // Set to track classes of added tiles

        // Add default tiles in specific order
        addTileIfAbsent(tiles, addedClasses, new LaserTile(true, true));
        addTileIfAbsent(tiles, addedClasses, new MirrorTile(true, true));
        addTileIfAbsent(tiles, addedClasses, new DoubleTile(true, true));
        addTileIfAbsent(tiles, addedClasses, new SplitterTile(true, true));
        addTileIfAbsent(tiles, addedClasses, new CheckPointTile(true, true));
        addTileIfAbsent(tiles, addedClasses, new CellBlockerTile(true, false));

        // Process additional tiles from TileInfo.getTiles(false)
        TileInfo.getTiles(false).forEach(tile -> {
            tile.setIsMoveable(true);
            tile.setIsRotatable(true);
            addTileIfAbsent(tiles, addedClasses, tile);
        });

        return tiles;
    }

    private void addTileIfAbsent(List<Tile> tiles, Set<Class<?>> addedClasses, Tile tile) {
        if (addedClasses.add(tile.getClass())) {  // Returns true if this class was not already in the set
            tiles.add(tile);
        }
    }


}
