package tiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import Model.Tiles.CheckPointTile;

public class CheckPointTileSteps {
    private TestAssetServer testAssetServer;
    private CheckPointTile checkPointTile;
    private boolean playerCanMoveTile;
    @Given("that the AssetServer has loaded the CheckPoint image")
    public void thatTheAssetServerHasLoadedTheCheckPointImage() {
        testAssetServer = new TestAssetServer();
        BufferedImage testImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        testAssetServer.addImage("checkPoint.png", testImage);
    }

    @When("the CheckPointTile is created")
    public void theCheckPointTileIsCreated() {
        checkPointTile = new CheckPointTile(false,true);
    }

    @Then("it should have the CheckPoint image set")
    public void itShouldHaveTheCheckPointImageSet() {
        assertNotNull("CheckPointTile should have an images set", checkPointTile.getImage());
    }

    @Given("a CheckPointTile instance")
    public void aCheckPointTileInstance() {
        checkPointTile = new CheckPointTile(false,true);
    }

    @When("I check its propertiees")
    public void iCheckItsPropertiees() {
        // properties already sat
    }

    @Then("it should be non-moveable and rotateable")
    public void itShouldBeNonMoveableAndRotateable() {
        assertFalse("CheckPointTile should be non-moveable", checkPointTile.getIsMovable());
        assertTrue("CheckPointTile should be rotateable", checkPointTile.getIsRotatable());
    }

    @And("its mirror direction should be {int},{int},{int},{int}")
    public void itsMirrorDirectionShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, checkPointTile.getMirror());
    }

    @And("its passable direction should be {int},{int},{int},{int}")
    public void itsPassableDirectionShouldBe(int arg0, int arg1, int arg2, int arg3) {
        assertArrayEquals(new int[]{arg0, arg1, arg2, arg3}, checkPointTile.getPass());
    }

    @Given("a CheckPointTile instance on a game board")
    public void aCheckPointTileInstanceOnAGameBoard() {
        checkPointTile = new CheckPointTile(false,true);

    }

    @And("a player trying to move the CheckPointTile")
    public void aPlayerTryingToMoveTheCheckPointTile() {
        playerCanMoveTile = checkPointTile.getIsMovable();
    }

    @When("the player attempts to movee")
    public void thePlayerAttemptsToMovee() {
        // Not necessary for the test
    }

    @Then("the player should not be able to move the CheckPointTile")
    public void thePlayerShouldNotBeAbleToMoveTheCheckPointTile() {
        assertFalse("Player should not be able to move CheckPointTile", playerCanMoveTile);

    }
}
