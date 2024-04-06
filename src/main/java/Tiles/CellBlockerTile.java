package Tiles;

public class CellBlockerTile extends Tile {
    public CellBlockerTile() {
        super(false, false);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("cellBlocker"));
    }
}
