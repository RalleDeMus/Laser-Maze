package Model.Tiles;

import Controller.AssetServer;
import Controller.ImageHandler;

public class CheckPointTile extends Tile {
    public CheckPointTile(boolean isMoveable, boolean isRotateable) {
        super( isMoveable,  isRotateable);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 1, 0, 1};
        this.target = new int[]{0,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("checkPoint"));
        this.setRotatedImage(AssetServer.getInstance().getImage("checkPointFree"));
    }

    public CheckPointTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super( isMoveable,  isRotateable, 0);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 1, 0, 1};
        this.target = new int[]{0,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("checkPoint"));
        this.setRotatedImage(AssetServer.getInstance().getImage("checkPointFree"));
        this.rotate(orientation,5);
    }
}
