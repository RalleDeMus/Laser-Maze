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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                board.rotateSelectedTile(true);
                break;
            case KeyEvent.VK_1:
                board.setSelectedTile(getTiles().get(0));
                break;
            case KeyEvent.VK_2:
                board.setSelectedTile(getTiles().get(1));
                break;
            case KeyEvent.VK_3:
                board.setSelectedTile(getTiles().get(2));
                break;
            case KeyEvent.VK_4:
                board.setSelectedTile(getTiles().get(3));
                break;
            case KeyEvent.VK_5:
                board.setSelectedTile(getTiles().get(4));
                break;
            case KeyEvent.VK_6:
                board.setSelectedTile(getTiles().get(5));
                break;
            case KeyEvent.VK_7:
                if (tiles.size() > 6) {
                    board.setSelectedTile(tiles.get(6));
                }
                break;
            case KeyEvent.VK_8:
                if (tiles.size() > 7) {
                    board.setSelectedTile(tiles.get(7));
                }
                break;
            case KeyEvent.VK_9:
                if (tiles.size() > 8) {
                    board.setSelectedTile(tiles.get(8));
                }
                break;
            case KeyEvent.VK_0:
                if (tiles.size() > 9) {
                    board.setSelectedTile(tiles.get(9));
                }
                break;
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
