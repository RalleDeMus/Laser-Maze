package GameCard;


import Model.Logic.Board;
import Model.Tiles.Tile;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class PickLevelSteps {
    private Board board;
    private Tile[][] tiles;
    private static final Map<String, int[]> levelConfigs = new HashMap<>();
    private static final Map<String, List<Map<String, Object>>> tilePlacements = new HashMap<>();

    static {
        //count loose tiles
        levelConfigs.put("1", new int[]{0, 0, 0, 1, 1});// Mirror, Splitter, Checkpoint, Double, Targets
        levelConfigs.put("3", new int[]{0, 0, 0, 0, 1});
        levelConfigs.put("4", new int[]{2, 0, 0, 0, 1});
        levelConfigs.put("8", new int[]{1, 1, 0, 0, 2});


        //configuration of levels
        tilePlacements.put("1", Arrays.asList(
                createTileConfig(1, 1, "LaserTile", false, 1),
                createTileConfig(3, 3, "MirrorTile", false, 0)
        ));
        tilePlacements.put("3", Arrays.asList(
                createTileConfig(3, 0, "MirrorTile", true, 4),
                createTileConfig(3, 4, "MirrorTile", false, 1),
                createTileConfig(4, 0, "MirrorTile", true, 4),
                createTileConfig(4, 1, "MirrorTile", true, 4)
        ));
        tilePlacements.put("4", Arrays.asList(
                createTileConfig(0, 1, "LaserTile", false, 1),
                createTileConfig(1, 0, "CheckPointTile", false, 0),
                createTileConfig(4, 0, "MirrorTile", true, 4)
        ));
        tilePlacements.put("8", Arrays.asList(
                createTileConfig(0, 0, "LaserTile", true, 4),
                createTileConfig(0, 4, "MirrorTile", true, 4),
                createTileConfig(1, 3, "DoubleTile", false, 1)
        ));
    }
    private static Map<String, Object> createTileConfig(int row, int col, String type, boolean rotatable, int orientation) {
        Map<String, Object> config = new HashMap<>();
        config.put("row", row);
        config.put("col", col);
        config.put("type", type);
        config.put("rotatable", rotatable);
        config.put("orientation", orientation);
        return config;
    }

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
        assertNotNull( "Tiles array should be initialized",board.getTiles());

        String currentLevel = board.getLevel();
        int[] expectedTiles = levelConfigs.get(currentLevel);
        int[] actualTiles = board.get_game_info();

        for (int i = 0; i < expectedTiles.length; i++) {
            assertEquals(expectedTiles[i], actualTiles[i], "Mismatch in tile count for type " + i + " at level " + currentLevel);
        }

        List<Map<String, Object>> expectedTilePlacements = tilePlacements.get(currentLevel);
        if (expectedTilePlacements != null) {
            for (Map<String, Object> config : expectedTilePlacements) {
                int row = (Integer) config.get("row");
                int col = (Integer) config.get("col");
                if (row >= tiles.length || col >= tiles[row].length) {
                    fail("Tile position (" + row + "," + col + ") is out of bounds.");
                }

                Tile tile = tiles[row][col];
                if (tile == null) {
                    fail("Expected tile at position (" + row + "," + col + ") is missing. In level"+ currentLevel);
                }

                assertEquals(config.get("type"), tile.getClass().getSimpleName(), "Mismatch in tile type at position (" + row + "," + col + "). In level" + currentLevel);
                assertEquals(config.get("rotatable"), tile.getIsRotatable(), "Mismatch in rotatability at position (" + row + "," + col + "). In level"+ currentLevel);
                assertEquals(config.get("orientation"), tile.getOrientation(), "Mismatch in orientation at position (" + row + "," + col + "). In level" + currentLevel);
            }
        }
    }

}

