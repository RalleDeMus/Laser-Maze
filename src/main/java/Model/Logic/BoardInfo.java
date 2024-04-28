package Model.Logic;

import Model.Tiles.*;

public class BoardInfo {
    public static Tile intToTile (int i) {
        switch(i) {
            case 0:
                return new MirrorTile(true,true);
            case 1:
                return new SplitterTile(true, true);
            case 2:
                return new CheckPointTile(true, true);
            case 3:
                return new DoubleTile(true,true);
            case 4:
                return new LaserTile(true,true);
            default:
                return null;
        }
    }
}
