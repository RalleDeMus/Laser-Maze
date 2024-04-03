public class LaserTile extends Tile {

    public LaserTile(AssetServer as) {
        super();
        this.setImage(as.getImage("laser"));
        this.isLaser = true;
    }

    boolean isLaser;



    @Override
    public boolean getLaser() {
        return isLaser;
    }



}



