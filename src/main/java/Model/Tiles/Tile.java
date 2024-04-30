package Model.Tiles;

import Controller.ImageHandler;

import java.awt.image.BufferedImage;
/**
 * Abstract class for the tiles.
 * The tiles are the building blocks of the game.
 * They are placed on the board and the laser is reflected by them.
 */
abstract public class Tile implements TileInterface, Cloneable{


    protected boolean isMoveable; // If the tile is moveable or not. Defines if you can remove it from the board and add it again.

    protected boolean isRotateable; // If the tile rotatable or not.
    private BufferedImage image; // Image of the tile
    private BufferedImage rotatedImage; // Image of the tile if it is rotatable

    private int orientation; // Orientation of the tile. 0 is right, 1 is down, 2 is left, 3 is up, 4 is free rotation.

    protected int[] mirror; // Mirror values of the tile. Used to define the laser path. 1 is rotate 90 degrees, 3 is rotate 270 degrees.
    protected int[] pass; // Pass values of the tile. Used to define the laser path.

    protected int[] target; // Target values of the tile. Used to check if we hit a target.

    protected int splitter; // Splitter value of the tile. Used to define the laser path.
    protected boolean isMirror; // If the tile is a mirror or laser/cell blocker

    public Tile(boolean isMoveable, boolean isRotateable, int orientation){
        this.orientation = orientation;
        this.mirror = new int[]{};
        this.pass = new int[]{};
        this.target = new int[]{};
        this.isMoveable = isMoveable;
        this.isRotateable = isRotateable;

    }

    public Tile(boolean isMoveable, boolean isRotateable){
        this.orientation = 0;
        this.mirror = new int[]{};
        this.pass = new int[]{};
        this.target = new int[]{};
        this.isMoveable = isMoveable;
        this.isRotateable = isRotateable;
    }

    // Clone method for the tile
    // Clones the selected tile for placement on the board
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
            cloned.rotatedImage = this.rotatedImage;
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

    @Override
    public int getIsSplitter() {
        return splitter;
    }

    @Override
    public boolean getIsMirror() {
        return isMirror;
    }



    // getter and setter of if the tile is moveable or not
    @Override
    public boolean getIsMovable() {
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
        if (orientation == 4){
            return rotatedImage;
        }
        return image;
    }

    @Override
    public void setRotatedImage(BufferedImage rotatedImage) {
        this.rotatedImage = rotatedImage;
    }

    @Override
    public BufferedImage getRotatedImage() {
        return rotatedImage;
    }

    //getter and setter to make tile rotateable
    @Override
    public boolean getIsRotatable() {
        return isRotateable;
    }

    @Override
    public void setIsRotatable(boolean isRotatable) {
        this.isRotateable = isRotatable;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }



    /**
     * Rotate the tile by howMuch times.
     * If mod is 4 we can rotate it 4 times.
     * If mod is 5 we can rotate it 5 times where the fifth time is free rotation for the level maker.
     */
    public void rotate(int howMuch, int mod){
        if (orientation == 4 && mod == 4) {
            orientation = 0;
            image = ImageHandler.rotateImage(image, 90);
            howMuch--;


        }

        for(int i = 0; i < howMuch; i++){
            orientation = (orientation + 1) % mod;
            if(orientation != 4){
                image = ImageHandler.rotateImage(image, 90);


            }
        }


    }

}