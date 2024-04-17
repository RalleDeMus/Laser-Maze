import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Tiles.Tile;
public class gamecardSteps {
    private Card card;
    private Tile[][] tiles;
    int level;
    @Given("I have initialized a game card with level <level>")
    public void iHaveInitializedAGameCardWithLevelLevel() {
        card = new Card(level);
    }

    @When("I retrieve the card configuration")
    public void iRetrieveTheCardConfiguration() {
        tiles = card.getCard();
    }

    @Then("the card should have the specified number of special tiles for that level")
    public void theCardShouldHaveTheSpecifiedNumberOfSpecialTilesForThatLevel(int targetMirrorTiles, int splitterTiles, int checkPointTiles, int doubleTiles, int cellBlockerTiles) {
        int[] placeableTiles = card.getPlaceableTiles();
        assertAll(
                () -> assertEquals(targetMirrorTiles, placeableTiles[0]),
                () -> assertEquals(splitterTiles, placeableTiles[1]),
                () -> assertEquals(checkPointTiles, placeableTiles[2]),
                () -> assertEquals(doubleTiles, placeableTiles[3]),
                () -> assertEquals(cellBlockerTiles, placeableTiles[4])
        );

    }

    @Given("I have initialized a game card with level {int}")
    public void iHaveInitializedAGameCardWithLevel(int arg0) {
    }

    @Then("the card should contain a LaserTile at position \\({int}, {int})")
    public void theCardShouldContainALaserTileAtPosition(int arg0, int arg1) {
    }

    @And("the card should contain a CellBlockerTile at position \\({int}, {int})")
    public void theCardShouldContainACellBlockerTileAtPosition(int arg0, int arg1) {
    }

    @And("the card should contain a CheckPointTile at position \\({int}, {int})")
    public void theCardShouldContainACheckPointTileAtPosition(int arg0, int arg1) {
    }

    @And("the card should contain a SplitterTile at position \\({int}, {int})")
    public void theCardShouldContainASplitterTileAtPosition(int arg0, int arg1) {
    }

    @And("the card should contain a MirrorTile at position \\({int}, {int})")
    public void theCardShouldContainAMirrorTileAtPosition(int arg0, int arg1) {
    }

    @When("I request the array of placeable tiles")
    public void iRequestTheArrayOfPlaceableTiles() {
    }

    @Then("the array should reflect counts of {int} for targetMirrorTiles, {int} for splitterTiles, {int} for checkPointTiles, {int} for doubleTiles, and {int} for cellBlockerTiles")
    public void theArrayShouldReflectCountsOfForTargetMirrorTilesForSplitterTilesForCheckPointTilesForDoubleTilesAndForCellBlockerTiles(int arg0, int arg1, int arg2, int arg3, int arg4) {
    }
}
