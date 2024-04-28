package tiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import Model.Tiles.SplitterTile;

public class SplitterTileSteps {
    private TestAssetServer testAssetServer;
    private SplitterTile splitterTile;
    private boolean playerCanMoveTile;
    @Given("that the AssetServer has loaded the Splitter image")
    public void thatTheAssetServerHasLoadedTheSplitterImage() {
        testAssetServer = new TestAssetServer();
        BufferedImage testImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        testAssetServer.addImage("beamSplitter.png", testImage);
    }

    @When("the SplitterTile is created")
    public void theSplitterTileIsCreated() {
        splitterTile = new SplitterTile(false,true);
    }

    @Then("it should have the Splitter image set")
    public void itShouldHaveTheSplitterImageSet() {
        assertNotNull("SplitterTile should have an image set", splitterTile.getImage());
    }

    @Given("a SplitterTile instance")
    public void aSplitterTileInstance() {
        splitterTile = new SplitterTile(false,true);

    }

    @When("I check its prooperties")
    public void iCheckItsProoperties() {
        // properties already sat
    }

    @Then("it should be non-mooveable and rotateable")
    public void itShouldBeNonMooveableAndRotateable() {
        assertFalse("SplitterTile should be non-moveable", splitterTile.getIsMovable());
        assertTrue("SplitterTile should be rotateable", splitterTile.getIsRotatable());
    }

    @And("its mirror direction shoould be {int},{int},{int},{int}")
    public void itsMirrorDirectionShoouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, splitterTile.getMirror());
    }

    @And("its passable direction shoould be {int},{int},{int},{int}")
    public void itsPassableDirectionShoouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, splitterTile.getPass());
    }

    @Given("a SplitterTile instance on a game board")
    public void aSplitterTileInstanceOnAGameBoard() {
        splitterTile = new SplitterTile(false,true);

    }

    @And("a player trying to move the SplitterTile")
    public void aPlayerTryingToMoveTheSplitterTile() {
        playerCanMoveTile = splitterTile.getIsMovable();
    }

    @When("the player atteempts to move")
    public void thePlayerAtteemptsToMove() {
        // Not necessary for the test
    }

    @Then("the player should not be able to move the SplitterTile")
    public void thePlayerShouldNotBeAbleToMoveTheSplitterTile() {
        assertFalse("Player should not be able to move SplitterTile", playerCanMoveTile);
    }
}
