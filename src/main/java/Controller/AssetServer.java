package Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Singleton class to handle the assets of the game.
 * Facade to easily get the assets when needed.
 */
public class AssetServer {

    private static AssetServer instance;
    final private Map<String, BufferedImage> images = new HashMap<>();

    /**
     * Load the images being used in the game. We use BufferedImage as the type.
     */
    private AssetServer() {
        try {
            //loadImages("beamSplitter", "cellBlocker", "checkPoint", "doubleMirror", "empty", "laser", "targetMirror", "laserRay", "rotateBeamSplitter", "rotateCheckpoint", "rotateDoubleMirror", "rotateLaser", "rotateTargetMirror", "spotRotateTargetMirror", "spotTargetMirror");
            ArrayList<String> assets = listFilesForFolder(new File("src/main/assets"));
            loadImages(assets);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load assets", e);
        }

    }

    // Singleton pattern
    public static AssetServer getInstance(){
        if (instance == null){
            instance = new AssetServer();
        }
        return instance;
    }

    // Load images from the assets folder
    private void loadImages(ArrayList<String> assetNames) throws IOException {
        for (String assetName : assetNames) {
            BufferedImage image = ImageIO.read(new File("src/main/assets/" + assetName));
            images.put(assetName, image);

        }

    }

    // We get an image based on a string name plus the .png extension
    public BufferedImage getImage(String assetName) {
        return images.get(assetName+".png");
    }


    // List all files in a folder as an ArrayList of strings
    private ArrayList<String> listFilesForFolder(final File folder) {

        ArrayList<String> filenames = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                filenames.add(fileEntry.getName());
            }
        }
        return filenames;

    }
}
