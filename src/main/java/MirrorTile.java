public class MirrorTile extends Tile {

    public MirrorTile() {
        super();
        this.mirror = new int[]{0, 1, 3, 0};
        this.pass = new int[]{0, 1, 1, 0};
        this.target = new int[]{1,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("targetMirror"));
    }



}
