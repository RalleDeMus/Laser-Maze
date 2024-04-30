package Model.Tiles.GameTiles;

import Controller.AssetServer;
import Model.Tiles.Tile;

/**
 * Class for the mirror tile.
 * The mirror tile is a tile that can reflect the laser beam on one side and has the target on another side.
 */
public class MirrorTile extends Tile {

    public MirrorTile(boolean isMoveable, boolean isRotateable) {
        super(isMoveable, isRotateable);
        this.mirror = new int[]{0, 1, 3, 0};
        this.pass = new int[]{0, 1, 1, 0};
        this.target = new int[]{1,0,0,0};
        this.splitter = 0;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("targetMirror"));
        this.setRotatedImage(AssetServer.getInstance().getImage("targetMirrorFree"));

    }

    public MirrorTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super(isMoveable, isRotateable, 0);
        this.mirror = new int[]{0, 1, 3, 0};
        this.pass = new int[]{0, 1, 1, 0};
        this.target = new int[]{1,0,0,0};
        this.splitter = 0;
        this.isMirror = true;
        this.setImage(AssetServer.getInstance().getImage("targetMirror"));
        this.setRotatedImage(AssetServer.getInstance().getImage("targetMirrorFree"));
        this.rotate(orientation,5);
    }



}
