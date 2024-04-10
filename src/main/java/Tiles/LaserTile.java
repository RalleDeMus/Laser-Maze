package Tiles;

public class LaserTile extends Tile {

    public LaserTile(boolean isMoveable, boolean isRotateable) {
        super(isMoveable, isRotateable);
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.mirror = new int[]{0, 0, 0, 0};
        this.pass = new int[]{0, 0, 0, 0};
        this.target = new int[]{0,0,0,0};
        System.out.println("Tiles.LaserTile");
    }

}



