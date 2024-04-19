package Model.Tiles;

import Controller.AssetServer;
import Controller.ImageHandler;

public class SplitterTile extends Tile {

    public SplitterTile(boolean isMoveable, boolean isRotatable) {
        super(isMoveable, isRotatable);
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.isMoveable = isMoveable;
        this.setImage(AssetServer.getInstance().getImage("beamSplitter"));
        this.setRotatedImage(AssetServer.getInstance().getImage("beamSplitterFree"));
    }

    public SplitterTile(boolean isMoveable, boolean isRotatable, int orientation) {
        super(isMoveable, isRotatable, 0);
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.isMoveable = isMoveable;
        this.setImage(AssetServer.getInstance().getImage("beamSplitter"));
        this.setRotatedImage(AssetServer.getInstance().getImage("beamSplitterFree"));
        this.rotate(orientation,5);
    }



}

