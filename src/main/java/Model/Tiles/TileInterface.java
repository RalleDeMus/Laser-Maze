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
    void setIsRotatable(boolean isRotatable);





}