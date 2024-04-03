import java.awt.image.BufferedImage;

public class Tile implements TileInterface{
    private boolean isMoveable;

    private boolean isRotateable;
    private BufferedImage image;

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

}
/*
void getOrientation();
void setOrientation();

 */