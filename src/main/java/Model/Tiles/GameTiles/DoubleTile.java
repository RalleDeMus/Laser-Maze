package Model.Tiles.GameTiles;

import Controller.AssetServer;
import Model.Tiles.Tile;

/**
 * Class for the double mirror tile.
 * The double mirror tile is a tile that can reflect the laser beam on two sides.
 */
public class DoubleTile extends Tile {
    public DoubleTile(boolean isMoveable, boolean isRotateable) {
        super( isMoveable,  isRotateable);
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("doubleMirror"));
        this.setRotatedImage(AssetServer.getInstance().getImage("doubleMirrorFree"));
    }

    public DoubleTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super( isMoveable,  isRotateable, 0);
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("doubleMirror"));
        this.setRotatedImage(AssetServer.getInstance().getImage("doubleMirrorFree"));
        this.rotate(orientation,5);
    }
}
