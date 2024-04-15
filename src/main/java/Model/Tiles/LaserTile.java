package Model.Tiles;

import Controller.AssetServer;
import Controller.ImageHandler;

public class LaserTile extends Tile {

    public LaserTile(boolean isMoveable, boolean isRotateable) {
        super(isMoveable, isRotateable);
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        //System.out.println("Model.Tiles.LaserTile");
    }

    public LaserTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super(isMoveable, isRotateable, orientation);
        this.setImage(ImageHandler.rotateImage(AssetServer.getInstance().getImage("laser"), 90*orientation));
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        //System.out.println("Model.Tiles.LaserTile");
    }

}



