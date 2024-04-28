package tiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import Model.Tiles.DoubleTile;

public class DoubleTileSteps {
    private TestAssetServer testAssetServer;
    private DoubleTile doubleTile;
    private boolean playerCanMoveTile;
    @Given("that the AssetServer has loaded the Double image")
    public void thatTheAssetServerHasLoadedTheDoubleImage() {
        testAssetServer = new TestAssetServer();
        BufferedImage testImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        testAssetServer.addImage("doubleMirror.png", testImage);
    }

    @When("the DoubleTile is created")
    public void theDoubleTileIsCreated() {
        doubleTile = new DoubleTile(false,true);
    }

    @Then("it should have the Double image set")
    public void itShouldHaveTheDoubleImageSet() {
        assertNotNull("DoubleTile should have an images set", doubleTile.getImage());
    }

    @Given("a DoubleTile instance")
    public void aDoubleTileInstance() {
        doubleTile = new DoubleTile(false,true);
    }

    @When("I check its propertiess")
    public void iCheckItsPropertiess() {
        // properties already sat
    }

    @Then("it should be non-moveablee and rotateable")
    public void itShouldBeNonMoveableeAndRotateable() {
        assertFalse("DoubleTile should be non-moveable", doubleTile.getIsMovable());
        assertTrue("DoubleTile should be rotateable", doubleTile.getIsRotatable());
    }

    @And("its mirror directionn should be {int},{int},{int},{int}")
    public void itsMirrorDirectionnShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, doubleTile.getMirror());

    }

    @And("its passable directionn should be {int},{int},{int},{int}")
    public void itsPassableDirectionnShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, doubleTile.getPass());
    }

    @Given("a DoubleTile instance on a game board")
    public void aDoubleTileInstanceOnAGameBoard() {
        doubleTile = new DoubleTile(false,true);
    }

    @And("a player trying to move the DoubleTile")
    public void aPlayerTryingToMoveTheDoubleTile() {
        playerCanMoveTile = doubleTile.getIsMovable();
    }

    @When("the player attempts to movve")
    public void thePlayerAttemptsToMovve() {
        // Not necessary for the test
    }

    @Then("the player should not be able to move the DoubleTile")
    public void thePlayerShouldNotBeAbleToMoveTheDoubleTile() {
        assertFalse("Player should not be able to move DoubleTile", playerCanMoveTile);
    }
}
