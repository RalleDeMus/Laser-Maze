package Model.Tiles;

import java.awt.image.BufferedImage;
/**
 * Interface for the tiles.
 * The tiles are the building blocks of the game.
 */
public interface TileInterface {

    int[] getMirror();
    int[] getPass();
    int[] getTarget();

    boolean getIsMovable();

    boolean getIsRotatable();
    void setIsMoveable(boolean isMoveable);
    BufferedImage getImage();
    void setImage(BufferedImage image);

    void setRotatedImage(BufferedImage rotatedImage);

    BufferedImage getRotatedImage();
    void setIsRotatable(boolean isRotatable);



}