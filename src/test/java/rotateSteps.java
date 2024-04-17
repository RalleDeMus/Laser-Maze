//default

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

//added
import static org.junit.jupiter.api.Assertions.assertEquals;
import Model.Tiles.Tile;


public class rotateSteps {
    private Tile tile;
    private int initialOrientation;

    @Given("I have a rotatable game tile with an initial orientation of <initialOrientation>")
    public void iHaveARotatableGameTileWithAnInitialOrientationOf(int initialOrientation) {
        this.tile = new Tile(false, true);
        this.initialOrientation = initialOrientation;
        this.tile.setOrientation(initialOrientation);
    }

    @When("i rotate the tile once")
    public void iRotateTheTileOnce() {
        this.tile.rotate();
    }

    @Then("the tiles orientation should be initial +1")
    public void theTilesOrientationShouldBeInitial() {
        int expectedOrientation = (this.initialOrientation + 1) % 4;
        assertEquals(expectedOrientation, this.tile.getOrientation());
    }


}
