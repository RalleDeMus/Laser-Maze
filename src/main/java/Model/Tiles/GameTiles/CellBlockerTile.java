package Model.Tiles.GameTiles;

import Controller.AssetServer;
import Model.Tiles.Tile;

/**
 * Class for the cell blocker tile.
 * The cell blocker tile is used to block the placement of another tile.
 */
public class CellBlockerTile extends Tile {
    public CellBlockerTile(boolean isMoveable, boolean isRotateable) {
        super(isMoveable, isRotateable);
    }

    @Override
    public void SetupTile() {
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = false;
        this.setImage(AssetServer.getInstance().getImage("cellBlocker"));
    }
}


