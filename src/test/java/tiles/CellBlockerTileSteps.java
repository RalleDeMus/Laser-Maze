package tiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;

import Model.Tiles.CellBlockerTile;

public class    CellBlockerTileSteps {
    private TestAssetServer testAssetServer;
    private CellBlockerTile cellBlockerTile;
    private boolean playerCanMoveTile;

    @Given("that the AssetServer has loaded the CellBlocker image")
    public void thatTheAssetServerHasLoadedTheCellBlockerImage() {
        testAssetServer = new TestAssetServer();
        BufferedImage testImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        testAssetServer.addImage("cellBlocker.png", testImage);
    }

    @When("the CellBlockerTile is created")
    public void theCellBlockerTileIsCreated() {
        cellBlockerTile = new CellBlockerTile(false);
    }

    @Then("it should have the CellBlocker image set")
    public void itShouldHaveTheCellBlockerImageSet() {
        assertNotNull("CellBlockerTile should have an images set", cellBlockerTile.getImage());
    }

    @Given("a CellBlockerTile instance")
    public void aCellBlockerTileInstance() {
        cellBlockerTile = new CellBlockerTile(false);
    }

    @When("I check its properties")
    public void iCheckItsProperties() {
        // properties already sat
    }

    @Then("it should be non-moveable and non-rotateable")
    public void itShouldBeNonMoveableAndNonTargetable() {
        assertFalse("CellBlockerTile should be non-moveable", cellBlockerTile.getIsMovable());
        assertFalse("CellBlockerTile should be non-rotateable", cellBlockerTile.getIsRotatable());
    }

    @And("its mirror directions should be {int},{int},{int},{int}")
    public void itsMirrorDirectionsShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, cellBlockerTile.getMirror());
    }

    @And("its passable directions should be {int},{int},{int},{int}")
    public void itsPassableDirectionsShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, cellBlockerTile.getPass());
    }

    @Given("a CellBlockerTile instance on a game board")
    public void aCellBlockerTileInstanceOnAGameBoard() {
        cellBlockerTile = new CellBlockerTile(false);
    }

    @And("a player trying to move the CellBlockerTile")
    public void aPlayerTryingToMoveOntoTheCellBlockerTile() {
        playerCanMoveTile = cellBlockerTile.getIsMovable();
    }

    @When("the player attempts to move")
    public void thePlayerAttemptsToMove() {
        // Not necessary for the test
    }

    @Then("the player should not be able to move the CellBlockerTile")
    public void thePlayerShouldNotBeAbleToMoveTheCellBlockerTile() {
        assertFalse("Player should not be able to move onto CellBlockerTile", playerCanMoveTile);
    }
}
