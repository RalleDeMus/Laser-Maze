package Model.Tiles;

import java.awt.image.BufferedImage;

public interface TileInterface {

    int[] getMirror();
    int[] getPass();
    int[] getTarget();

    //boolean getLaser();

    boolean getIsMoveable();

    boolean getIsRotateable();
    void setIsMoveable(boolean isMoveable);
    BufferedImage getImage();
    void setImage(BufferedImage image);

    void setRotatedImage(BufferedImage rotatedImage);

    BufferedImage getRotatedImage();
    void setIsRotateable(boolean isRotateable);



}