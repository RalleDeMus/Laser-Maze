package Model.Tiles.GameTiles;

import Controller.AssetServer;
import Model.Tiles.Tile;

/**
 * Class for the splitter tile.
 * The splitter tile is a tile that can split the laser beam in two directions.
 */
public class SplitterTile extends Tile {

    public SplitterTile(boolean isMoveable, boolean isRotatable) {
        super(isMoveable, isRotatable);
    }

    public SplitterTile(boolean isMoveable, boolean isRotatable, int orientation) {
        super(isMoveable, isRotatable, orientation);
    }

    @Override
    public void SetupTile() {
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.splitter = 1;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("beamSplitter"));
        this.setRotatedImage(AssetServer.getInstance().getImage("beamSplitterFree"));
    }



}

