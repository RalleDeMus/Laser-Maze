package tiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import Model.Tiles.MirrorTile;

public class MirrorTileSteps {
    private TestAssetServer testAssetServer;
    private MirrorTile mirrorTile;
    private boolean playerCanMoveTile;
    @Given("that the AssetServer has loaded the Mirror image")
    public void thatTheAssetServerHasLoadedTheMirrorImage() {
        testAssetServer = new TestAssetServer();
        BufferedImage testImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        testAssetServer.addImage("targetMirror.png", testImage);
    }

    @When("the MirrorTile is created")
    public void theMirrorTileIsCreated() {
        mirrorTile = new MirrorTile(false,true);
    }

    @Then("it should have the Mirror image set")
    public void itShouldHaveTheMirrorImageSet() {
        assertNotNull("MirrorTile should have an image set", mirrorTile.getImage());
    }

    @Given("a MirrorTile instance")
    public void aMirrorTileInstance() {
        mirrorTile = new MirrorTile(false,true);
    }

    @When("I check its propperties")
    public void iCheckItsPropperties() {
        // properties already sat
    }

    @Then("it should be non-moveablle and rotateable")
    public void itShouldBeNonMoveablleAndRotateable() {
        assertFalse("MirrorTile should be non-moveable", mirrorTile.getIsMovable());
        assertTrue("MirrorTile should be rotateable", mirrorTile.getIsRotatable());
    }

    @And("its mirror direction shouldd be {int},{int},{int},{int}")
    public void itsMirrorDirectionShoulddBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, mirrorTile.getMirror());

    }

    @And("its passable direction shouldd be {int},{int},{int},{int}")
    public void itsPassableDirectionShoulddBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, mirrorTile.getPass());
    }

    @Given("a MirrorTile instance on a game board")
    public void aMirrorTileInstanceOnAGameBoard() {
        mirrorTile = new MirrorTile(false,true);
    }

    @And("a player trying to move the MirrorTile")
    public void aPlayerTryingToMoveTheMirrorTile() {
        playerCanMoveTile = mirrorTile.getIsMovable();
    }

    @When("the player attemppts to move")
    public void thePlayerAttempptsToMove() {
        // Not necessary for the test
    }

    @Then("the player should not be able to move the MirrorTile")
    public void thePlayerShouldNotBeAbleToMoveTheMirrorTile() {
        assertFalse("Player should not be able to move MirrorTile", playerCanMoveTile);
    }
}
