import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

///hu
public class AssetServer {
    public BufferedImage beamSplitterImage;
    public BufferedImage cellBlockerImage;
    public BufferedImage checkPointImage;
    public BufferedImage doubleMirrorImage;
    public BufferedImage emptyImage;
    public BufferedImage laserImage;
    public BufferedImage targetMirrorImage;

    public AssetServer() {
        try {
            this.beamSplitterImage = ImageIO.read(new File("src/assets/beamSplitter.png"));
            this.cellBlockerImage = ImageIO.read(new File("src/assets/cellBlocker.png"));
            this.checkPointImage = ImageIO.read(new File("src/assets/checkPoint.png"));
            this.doubleMirrorImage = ImageIO.read(new File("src/assets/doubleMirror.png"));
            this.emptyImage = ImageIO.read(new File("src/assets/empty.png"));
            this.laserImage = ImageIO.read(new File("src/assets/laser.png"));
            this.targetMirrorImage = ImageIO.read(new File("src/assets/targetMirror.png"));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load assets", e);
        }
    }
}
