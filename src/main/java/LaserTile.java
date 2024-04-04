public class LaserTile extends Tile {

    public LaserTile() {
        super();
        this.setImage(AssetServer.getInstance().getImage("laser"));
        this.isLaser = true;
    }

    boolean isLaser;



    @Override
    public boolean getLaser() {
        return isLaser;
    }



}



