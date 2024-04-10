package images.load;

import static org.junit.Assert.assertArrayEquals;

import java.awt.image.BufferedImage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Tiles.AssetServer;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoadImagesSteps {

    private AssetServer assetServer;

    private BufferedImage loadedImage;

    private String assetName;

    // Helper Method. Takes a BufferedImage as input and converts it into an array of pixels
    private int[] bufferedImageToArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];

        image.getRGB(0,0,width,height,pixels,0,width);
        return pixels;
    }

    // Sets the asset name
    @Given("assetName {string}")
    public void assetname(String assetName) {
        this.assetName = assetName;
    }

    // Initializes assetServer
    @When("the method loadImages\\(\\) is executed") // Using backslashes to escape parenthesis
    public void theMethodLoadImagesIsExecuted() {
        assetServer = new AssetServer();
    }

    // Loads the "beamSplitter.png" image and compares it with an expected image
    @Then("the image beamSplitter.png is loaded")
    public void theImageBeamSplitterPngIsLoaded() {
        loadedImage = assetServer.getImage("beamSplitter");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/beamSplitter.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "cellBlocker.png" image and compares it with an expected image
    @Then("the image cellBlocker.png is loaded")
    public void theImageCellBlockerPngIsLoaded() {
        loadedImage = assetServer.getImage("cellBlocker");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/cellBlocker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "checkPoint.png" image and compares it with an expected image
    @Then("the image checkPoint.png is loaded")
    public void theImageCheckPointPngIsLoaded() {
        loadedImage = assetServer.getImage("checkPoint");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/checkPoint.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "doubleMirror.png" image and compares it with an expected image
    @Then("the image doubleMirror.png is loaded")
    public void theImageDoubleMirrorPngIsLoaded() {
        loadedImage = assetServer.getImage("doubleMirror");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/doubleMirror.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "empty.png" image and compares it with an expected image
    @Then("the image empty.png is loaded")
    public void theImageEmptyPngIsLoaded() {
        loadedImage = assetServer.getImage("empty");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "laser.png" image and compares it with an expected image
    @Then("the image laser.png is loaded")
    public void theImageLaserPngIsLoaded() {
        loadedImage = assetServer.getImage("laser");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/laser.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "targetMirror.png" image and compares it with an expected image
    @Then("the image targetMirror.png is loaded")
    public void theImageTargetMirrorPngIsLoaded() {
        loadedImage = assetServer.getImage("targetMirror");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/targetMirror.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }

    // Loads the "laserRay.png" image and compares it with an expected image
    @Then("the image laserRay.png is loaded")
    public void theImageLaserRayPngIsLoaded() {
        loadedImage = assetServer.getImage("laserRay");
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File("src/main/assets/laserRay.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals("The loaded image should match the expected image", bufferedImageToArray(expectedImage), bufferedImageToArray(loadedImage));
    }
}
