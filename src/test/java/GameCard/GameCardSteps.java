package GameCard;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import Model.Tiles.*;
import Model.Logic.Card;
public class GameCardSteps {
    private Card card;
    private Tile[][] tiles;
    private String level;

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


    @Then("the card should contain a LaserTile at position ({int}, {int})")
    public void theCardShouldContainALaserTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof LaserTile);
    }
    @And("the card should contain a CellBlockerTile at position ({int}, {int})")
    public void theCardShouldContainACellBlockerTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof CellBlockerTile);
    }

    @And("the card should contain a CheckPointTile at position ({int}, {int})")
    public void theCardShouldContainACheckPointTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof CheckPointTile);
    }

    @And("the card should contain a SplitterTile at position ({int}, {int})")
    public void theCardShouldContainASplitterTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof SplitterTile);
    }

    @And("the card should contain a MirrorTile at position ({int}, {int})")
    public void theCardShouldContainAMirrorTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof MirrorTile);
    }

    @When("I request the array of placeable tiles")
    public void iRequestTheArrayOfPlaceableTiles() {
        // This method is implemented with the assumption that getPlaceableTiles is called
        // In reality, the response is handled in the corresponding Then step.
    }

    @Then("the array should reflect counts of {int} for targetMirrorTiles, {int} for splitterTiles, {int} for checkPointTiles, {int} for doubleTiles, and {int} for cellBlockerTiles")
    public void theArrayShouldReflectCountsOfForTargetMirrorTilesForSplitterTilesForCheckPointTilesForDoubleTilesAndForCellBlockerTiles(int targetMirrorTiles, int splitterTiles, int checkPointTiles, int doubleTiles, int cellBlockerTiles) {
        int[] placeableTiles = card.getPlaceableTiles();
        assertEquals(targetMirrorTiles, placeableTiles[0]);
        assertEquals(splitterTiles, placeableTiles[1]);
        assertEquals(checkPointTiles, placeableTiles[2]);
        assertEquals(doubleTiles, placeableTiles[3]);
        assertEquals(cellBlockerTiles, placeableTiles[4]);
    }
}

