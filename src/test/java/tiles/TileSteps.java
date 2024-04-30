package tiles;

import Model.Tiles.GameTiles.MirrorTile;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import Model.Tiles.GameTiles.LaserTile;
import Model.Tiles.Tile;

import static org.junit.Assert.*;

public class TileSteps {
    private Tile tile;
    private LaserTile laserTile;
    private LaserTile clonedLaserTile;

    @Given("a non-moveable and rotateable tile")
    public void aNonMoveableAndRotateableTile() {
        tile = new MirrorTile(false,true);
    }

    @When("the tile is created")
    public void theTileIsCreated() {
        // already created
    }

    @Then("the tile should be non-moveable and rotateable")
    public void theTileShouldBeNonMoveableAndRotateable() {
        assertFalse(tile.getIsMovable());
        assertTrue(tile.getIsRotatable());
    }

    @Given("a tile with certain properties and image")
    public void aTileWithCertainPropertiesAndImage() {
        laserTile = new LaserTile(false,true,1);
    }

    @When("the tile is cloned")
    public void theTileIsCloned() {
        try {
            clonedLaserTile = (LaserTile) laserTile.clone();
        } catch (CloneNotSupportedException e) {
            fail("Clone not supported");
        }
    }

    @Then("the cloned tile should have the same properties")
    public void theClonedTileShouldHaveTheSameProperties() {
        assertNotNull("Cloned laser tile is null", clonedLaserTile);
        assertEquals(laserTile.getIsMovable(), clonedLaserTile.getIsMovable());
        assertEquals(laserTile.getIsRotatable(), clonedLaserTile.getIsRotatable());
        assertEquals(laserTile.getImage(), clonedLaserTile.getImage());
    }

    @Given("a tile with an orientation {int}")
    public void aTileWithAnOrientation(int orientation) {
        laserTile = new LaserTile(false,true,orientation);
    }

    @When("the tile is rotated by {int} units")
    public void theTileIsRotatedByUnits(int units) {
        assertNotNull("Laser tile is null", laserTile);
        laserTile.rotate(units,4); // Rotate laserTile by 2 units, mod is 4
    }

    @Then("the tile's orientation should be {int}")
    public void theTileSOrientationShouldBe(int expectedOrientation) {
        assertNotNull("Laser tile is null", laserTile);
        assertEquals(expectedOrientation,laserTile.getOrientation());
    }


}
