import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class AssetServer {
    public BufferedImage tileImage;
    public BufferedImage beamSplitterImage;
    public BufferedImage cellBlockerImage;
    public BufferedImage checkPointImage;
    public BufferedImage doubleMirrorImage;
    public BufferedImage emptyImage;
    public BufferedImage laserImage;
    public BufferedImage targetMirrorImage;

    public AssetServer() {
        try {
            this.tileImage = ImageIO.read(new File("assets/tile.png"));
            this.beamSplitterImage = ImageIO.read(new File("assets/beamSplitter.png"));
            this.cellBlockerImage = ImageIO.read(new File("assets/cellBlocker.png"));
            this.checkPointImage = ImageIO.read(new File("assets/checkPoint.png"));
            this.doubleMirrorImage = ImageIO.read(new File("assets/doubleMirror.png"));
            this.emptyImage = ImageIO.read(new File("assets/empty.png"));
            this.laserImage = ImageIO.read(new File("assets/laser.png"));
            this.targetMirrorImage = ImageIO.read(new File("assets/targetMirror.png"));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load assets", e);
        }
    }
}
