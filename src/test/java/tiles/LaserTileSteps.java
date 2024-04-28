package tiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import Model.Tiles.LaserTile;

public class LaserTileSteps {
    private TestAssetServer testAssetServer;
    private LaserTile laserTile;
    private boolean playerCanMoveTile;

    @Given("that the AssetServer has loaded the Laser image")
    public void thatTheAssetServerHasLoadedTheLaserImage() {
        testAssetServer = new TestAssetServer();
        BufferedImage testImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        testAssetServer.addImage("laser.png", testImage);
    }

    @When("the LaserTile is created")
    public void theLaserTileIsCreated() {
        laserTile = new LaserTile(false,true);
    }

    @Then("it should have the Laser image set")
    public void itShouldHaveTheLaserImageSet() {
        assertNotNull("LaserTile should have an images set", laserTile.getImage());

    }

    @Given("a LaserTile instance")
    public void aLaserTileInstance() {
        laserTile = new LaserTile(false,true);
    }

    @When("I check its propertiies")
    public void iCheckItsPropertiies() {
        // properties already sat

    }

    @Then("it should be non-moveablee and rotateablee")
    public void itShouldBeNonMoveableeAndRotateablee() {
        assertFalse("LaserTile should be non-moveable", laserTile.getIsMovable());
        assertTrue("LaserTile should be rotateable", laserTile.getIsRotatable());
    }

    @And("its mirror directiion should be {int},{int},{int},{int}")
    public void itsMirrorDirectiionShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, laserTile.getMirror());
    }

    @And("its passable directiion should be {int},{int},{int},{int}")
    public void itsPassableDirectiionShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, laserTile.getPass());

    }

    @Given("a LaserTile instance on a game board")
    public void aLaserTileInstanceOnAGameBoard() {
        laserTile = new LaserTile(false,true);
    }

    @And("a player trying to move the LaserTile")
    public void aPlayerTryingToMoveTheLaserTile() {
        playerCanMoveTile = laserTile.getIsMovable();
    }

    @When("the player attempts to moove")
    public void thePlayerAttemptsToMoove() {
        // Not necessary for the test
    }

    @Then("the player should not be able to move the LaserTile")
    public void thePlayerShouldNotBeAbleToMoveTheLaserTile() {
        assertFalse("Player should not be able to move LaserTile", playerCanMoveTile);
    }
}
