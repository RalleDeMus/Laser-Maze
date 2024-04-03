import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class AssetServer {
    private Map<String, BufferedImage> images = new HashMap<>();

    public AssetServer() {
        try {
            loadImages("beamSplitter", "cellBlocker", "checkPoint", "doubleMirror", "empty", "laser", "targetMirror", "laserRay");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load assets", e);
        }
    }

    private void loadImages(String... assetNames) throws IOException {
        for (String assetName : assetNames) {
            BufferedImage image = ImageIO.read(new File("src/main/assets/" + assetName + ".png"));
            images.put(assetName, image);
        }
    }

    public BufferedImage getImage(String assetName) {
        return images.get(assetName);
    }
}