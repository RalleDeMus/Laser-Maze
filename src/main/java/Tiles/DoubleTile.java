package Tiles;

public class DoubleTile extends Tile {
    public DoubleTile() {
        super();
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("doubleMirror"));
    }
}
