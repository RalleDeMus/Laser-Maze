package Tiles;

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
    void setIsRotateable(boolean isRotateable);



}