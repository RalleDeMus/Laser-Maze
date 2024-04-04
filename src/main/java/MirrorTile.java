import java.awt.image.BufferedImage;

public class MirrorTile extends Tile {

    public MirrorTile() {
        super();
        this.mirror = new int[]{0, 2};
        this.pass = new int[]{};
        this.target = new int[]{0};
        this.setImage(AssetServer.getInstance().getImage("targetMirror"));
    }


    private int[] mirror;
    private int[] pass;

    private int[] target;



}
