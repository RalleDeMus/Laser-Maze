package Model.Tiles.GameTiles;

import Controller.AssetServer;
import Model.Tiles.Tile;

/**
 * Class for the check point tile.
 * The check point tile is a tile the laser has to pass through in a specific direction.
 */

public class CheckPointTile extends Tile {
    public CheckPointTile(boolean isMoveable, boolean isRotateable) {
        super( isMoveable,  isRotateable);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 1, 0, 1};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("checkPoint"));
        this.setRotatedImage(AssetServer.getInstance().getImage("checkPointFree"));
    }

    public CheckPointTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super( isMoveable,  isRotateable, 0);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 1, 0, 1};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("checkPoint"));
        this.setRotatedImage(AssetServer.getInstance().getImage("checkPointFree"));
        this.rotate(orientation,5);
    }


}




