package Model.Tiles;

import Controller.AssetServer;
import Controller.ImageHandler;

public class LaserTile extends Tile {

    public LaserTile(boolean isMoveable, boolean isRotateable) {
        super(isMoveable, isRotateable);
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.setRotatedImage(AssetServer.getInstance().getImage("laserFree"));
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        //System.out.println("Model.Tiles.LaserTile");
    }

    public LaserTile(boolean isMoveable, boolean isRotateable, int orientation) {
        super(isMoveable, isRotateable, 0);
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.setRotatedImage(AssetServer.getInstance().getImage("laserFree"));

        this.rotate(orientation,5);
        System.out.println("orr: "+orientation);
    }

}



