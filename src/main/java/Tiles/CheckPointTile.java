package Tiles;

public class CheckPointTile extends Tile {
    public CheckPointTile(boolean isMoveable, boolean isRotateable) {
        super( isMoveable,  isRotateable);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 1, 0, 1};
        this.target = new int[]{0,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("checkPoint"));
    }
}
