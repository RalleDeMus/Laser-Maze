package tiles;
import Controller.AssetServer;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TestAssetServer extends AssetServer {
    private boolean imageLoaded = false;
    private Map<String, BufferedImage> images = new HashMap<>();

    public TestAssetServer() {
        super();
    }

    public void addImage(String assetName, BufferedImage image) {

        images.put(assetName,image);
    }

    @Override
    public BufferedImage getImage(String assetName) {
        BufferedImage image = images.get(assetName);
        if (image != null) {
            imageLoaded = true;
        }
        return image;
    }

}
