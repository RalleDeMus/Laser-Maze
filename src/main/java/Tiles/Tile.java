package Tiles;

import java.awt.image.BufferedImage;

public class Tile implements TileInterface, Cloneable{


    protected boolean isMoveable;

    protected boolean isRotateable;
    private BufferedImage image;

    private int orientation;

    protected int[] mirror;
    protected int[] pass;

    protected int[] target;

    public Tile(boolean isMoveable, boolean isRotateable){
        this.orientation = 0;
        this.mirror = new int[]{};
        this.pass = new int[]{};
        this.target = new int[]{};
        this.isMoveable = isMoveable;
        this.isRotateable = isRotateable;
    }


    @Override
    public Tile clone() throws CloneNotSupportedException {
        try {
            Tile cloned = (Tile) super.clone();
            // Clone mutable fields to ensure a deep clone
            cloned.mirror = this.mirror.clone();
            cloned.pass = this.pass.clone();
            cloned.target = this.target.clone();
            // For BufferedImage, decide based on whether it needs to be unique among clones
            // This example simply copies the reference. For a deep clone, you'll need a custom solution.
            cloned.image = this.image;
            return cloned;
        } catch (CloneNotSupportedException e) {
            // This shouldn't happen since we are Cloneable
            throw new AssertionError(e);
        }
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
        return target;
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