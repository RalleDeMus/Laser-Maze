package Model.Tiles.GameTiles;

import Controller.AssetServer;
import Model.Tiles.Tile;

/**
 * Class for the laser tile.
 * The laser tile is the tile that shoots the laser.
 */

public class LaserTile extends Tile {

    public LaserTile(boolean isMoveable, boolean isRotateable) {
        super(isMoveable, isRotateable);
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.setRotatedImage(AssetServer.getInstance().getImage("laserFree"));
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = false;
        //System.out.println("Model.Tiles.GameTiles.LaserTile");
    }

    public LaserTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super(isMoveable, isRotateable, 0);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        this.splitter = 0;
        this.isMirror = false;
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.setRotatedImage(AssetServer.getInstance().getImage("laserFree"));

        this.rotate(orientation,5);
    }

}



