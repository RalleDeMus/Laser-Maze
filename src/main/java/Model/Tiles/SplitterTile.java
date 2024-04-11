package Model.Tiles;

import Model.Logic.AssetServer;
import Model.Logic.ImageHandler;

public class SplitterTile extends Tile {
    public SplitterTile(boolean isMoveable, boolean isRotatable, int orientation) {
        super(isMoveable, isRotatable, orientation);
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.isMoveable = isMoveable;
        this.setImage(ImageHandler.rotateImage(AssetServer.getInstance().getImage("beamSplitter"), 90*orientation));
    }

    public SplitterTile(boolean isMoveable, boolean isRotatable) {
        super(isMoveable, isRotatable);
        this.mirror = new int[]{3, 1, 3, 1};
        this.pass = new int[]{1, 1, 1, 1};
        this.target = new int[]{0,0,0,0};
        this.isMoveable = isMoveable;
        this.setImage(AssetServer.getInstance().getImage("beamSplitter"));
    }

}

