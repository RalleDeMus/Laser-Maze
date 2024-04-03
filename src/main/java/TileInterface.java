import java.awt.image.BufferedImage;

public interface TileInterface {
    boolean getIsMoveable();
    void setIsMoveable(boolean isMoveable);
    BufferedImage getImage();
    void setImage(BufferedImage image);
    boolean getIsRotateable();
    void setIsRotateable(boolean isRotateable);

}