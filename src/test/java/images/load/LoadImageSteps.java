package images.load;

import Controller.AssetServer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class LoadImageSteps {

    private AssetServer assetServer;
    private String requestedAssetName;
    private BufferedImage loadedImage;

    // Helper Method. Takes a Buffered Image as input and converts it into an array of pixels
    private int[] bufferedImageToArray(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width*height];

        image.getRGB(0,0,width,height,pixels,0,width);
        return pixels;
    }
    @Given("the asset server is initialized")
    public void theAssetServerIsInitialized() {

        assetServer = AssetServer.getInstance();
    }

    @When("I load an image with name beamSplitter")
    public void iLoadAnImageIntoTheAssetServerWithNameBeamSplitter(String assetName) {
        assetServer.getImage("beamSplitter");

    }

    @When("I request an image named beamSplitter")
    public void iRequestAnImageNamedBeamSplitter(String assetName) {
        requestedAssetName = "beamSplitter";
        loadedImage = assetServer.getImage("beamSplitter");
    }

    @Then("I should receive the corresponding image")
    public void iShouldReceiveTheCorrespondingImage() {
        assertNotNull("Loaded image should not be null", loadedImage);
        BufferedImage expectedImage = null;
        try{
            expectedImage = ImageIO.read(new File("src/main/assets/beamSplitter.png"));
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load expected image", e);
        }
        assertNotNull("Expected image should not be null",expectedImage);

        int[] expectedPixels = bufferedImageToArray(expectedImage);
        int[] actualPixels = bufferedImageToArray(loadedImage);

        assertArrayEquals("Image pixels should match", expectedPixels,actualPixels);
    }
}
