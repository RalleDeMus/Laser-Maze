import Model.Tiles.Tile;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Model.Tiles.*;
import Controller.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class orientationSteps {
    @Given("a LaserTile has been created")
    public void aLaserTileHasBeenCreated() {
    }

    @When("I set the orientation of the LaserTile to {int}")
    public void iSetTheOrientationOfTheLaserTileTo(int arg0) {
        tile = new Tile();
    }

    @Then("the orientation of the LaserTile should be {int}")
    public void theOrientationOfTheLaserTileShouldBe(int arg0) {
        laserTile.setOrientation(0);
        assertEquals(laser.orientation());

    }
}
