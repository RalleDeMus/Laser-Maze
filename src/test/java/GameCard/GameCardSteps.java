package GameCard;

import Model.Logic.*;
import Model.Tiles.Tile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Model.Tiles.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class GameCardSteps {
    private Board board;
    private Tile[][] tiles;

    @Given("I have started a game with multiple {string}")
    public void iHaveStartedAGameWithMultiple(String level) {
        try {
            board = new Board(level);
        } catch (Exception e ){
            System.err.println("Error initializing the game card: " + e.getMessage());
            throw new RuntimeException("Failed to initialize the Card with level: " + level, e);
        }
    }

    @When("I load the level")
    public void iLoadTheLevel() {
        tiles = board.getTiles();
        assertNotNull("Tiles should not be null after loading the card", tiles);
    }

    @Then("the card should load with the configuration of the level")
    public void theCardShouldLoadWithTheConfigurationOfTheLevel() {
        assertNotNull("Card should be initialized", board);
        assertNotNull( "Tiles array should be initialized",board.getTiles());  // Ensure the tiles are loaded
        int[] placeableTiles = board.get_game_info();


        assertEquals(placeableTiles[0], 0, "Mirror tiles should match the expected count");
        assertEquals(placeableTiles[1], 0, "Splitter tiles should match the expected count");
        assertEquals(placeableTiles[2], 0, "Checkpoint tiles should match the expected count");
        assertEquals(placeableTiles[3], 1, "Double tiles should match the expected count");
        assertEquals(placeableTiles[4], 1, "Targets should match the expected count");
        Tile[][] tiles = board.getTiles();
        boolean foundLaserTile = false;
        boolean foundMirrorTile = false;

        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                Tile tile = tiles[i][j];
                if (tile != null) {
                    if (tile instanceof LaserTile && i == 1 && j == 1 && !tile.getIsRotatable() && tile.getOrientation() == 1) {
                        foundLaserTile = true;
                    } else if (tile instanceof MirrorTile && i == 3 && j == 3 && !tile.getIsRotatable() && tile.getOrientation() == 0) {
                        foundMirrorTile = true;
                    }
                }
            }
        }

        assertTrue(foundLaserTile, "LaserTile should be correctly placed at row 1, col 1, orientation 1, non-rotatable");
        assertTrue(foundMirrorTile, "MirrorTile should be correctly placed at row 3, col 3, orientation 0, non-rotatable");
    }

    @Given("I have loaded the card {string}")
    public void iHaveLoadedTheCard(String level) {
        if (board == null) {
            board = new Board(level); // Create a new Board instance with the specific level
        } else {
            board.setCardLevel(level); // Or set the new level on an existing board
        }
    }

    @When("I retrieve the card configuration")
    public void iRetrieveTheCardConfiguration() {

    }
    @Then("the card should have the specified number of special tiles for that level")
    public void theCardShouldHaveTheSpecifiedNumberOfSpecialTilesForThatLevel(int targetMirrorTiles, int splitterTiles, int checkPointTiles, int doubleTiles, int cellBlockerTiles) {
        int[] placeableTiles = board.get_game_info();
        assertAll(
                () -> assertEquals(targetMirrorTiles, placeableTiles[0]),
                () -> assertEquals(splitterTiles, placeableTiles[1]),
                () -> assertEquals(checkPointTiles, placeableTiles[2]),
                () -> assertEquals(doubleTiles, placeableTiles[3]),
                () -> assertEquals(cellBlockerTiles, placeableTiles[4])
        );

    }


    @When("I request the array of placeable tiles")
    public void iRequestTheArrayOfPlaceableTiles() {
        
    }

    @Then("the array should reflect counts of {int} for targetMirrorTiles, {int} for splitterTiles, {int} for checkPointTiles, {int} for doubleTiles, and {int} for cellBlockerTiles")
    public void theArrayShouldReflectCountsOfForTargetMirrorTilesForSplitterTilesForCheckPointTilesForDoubleTilesAndForCellBlockerTiles(int targetMirrorTiles, int splitterTiles, int checkPointTiles, int doubleTiles, int cellBlockerTiles) {
        int[] placeableTiles = board.get_game_info();
        assertEquals(targetMirrorTiles, placeableTiles[0]);
        assertEquals(splitterTiles, placeableTiles[1]);
        assertEquals(checkPointTiles, placeableTiles[2]);
        assertEquals(doubleTiles, placeableTiles[3]);
        assertEquals(cellBlockerTiles, placeableTiles[4]);
    }

    @Given("I have initialized a game card with level {string}")
    public void iHaveInitializedAGameCardWithLevel(String level) {
        board.setCardLevel(level);
    }

    @Then("the card should contain a LaserTile at position \\({int},{int})")
    public void theCardShouldContainALaserTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof LaserTile);
    }

    @And("the card should contain a CellBlockerTile at position \\({int},{int})")
    public void theCardShouldContainACellBlockerTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof CellBlockerTile);
    }

    @And("the card should contain a CheckPointTile at position \\({int},{int})")
    public void theCardShouldContainACheckPointTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof CheckPointTile);

    }

    @And("the card should contain a SplitterTile at position \\({int},{int})")
    public void theCardShouldContainASplitterTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof SplitterTile);

    }

    @And("the card should contain a MirrorTile at position \\({int},{int})")
    public void theCardShouldContainAMirrorTileAtPosition(int row, int col) {
        assertTrue(tiles[row][col] instanceof MirrorTile);

    }

}

