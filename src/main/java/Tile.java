import java.awt.image.BufferedImage;

public class Tile implements TileInterface{

    public Tile() {
        this.orientation = 0;
    }

    private boolean isMoveable;

    private boolean isRotateable;
    private BufferedImage image;

    private int orientation;

    private boolean isLaser;

    private int[] mirror;
    private int[] pass;

    private int[] target;

    @Override
    public boolean getLaser() {
        return isLaser;
    }

    @Override
    public int[] getMirror() {
        return mirror;
    }
    @Override
    public int[] getPass() {
        return pass;
    }
    @Override
    public int[] getTarget() {
        return pass;
    }


    // getter and setter of if the tile is moveable or not
    @Override
    public boolean getIsMoveable() {
        return isMoveable;
    }

    @Override
    public void setIsMoveable(boolean isMoveable) {
        this.isMoveable = isMoveable;

    }
    // getter and setter of image for tile
    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    //getter and setter to make tile rotateable
    @Override
    public boolean getIsRotateable() {
        return isRotateable;
    }

    @Override
    public void setIsRotateable(boolean isRotateable) {
        this.isRotateable = isRotateable;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }



    public void rotate(){
        orientation = (orientation + 1) % 4;
        image = ImageHandler.rotateImage(image, 90);
    }

}