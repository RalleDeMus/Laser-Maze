public class MirrorTile extends Tile {

    public MirrorTile(AssetServer as) {
        super();
        this.mirror = new int[]{0, 2};
        this.pass = new int[]{};
        this.target = new int[]{0};
        this.setImage(as.getImage("targetMirror"));
    }


    private int[] mirror;
    private int[] pass;

    private int[] target;



}
