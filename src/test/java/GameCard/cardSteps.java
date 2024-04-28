package GameCard;

import Model.Tiles.Tile;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Model.Logic.*;

import static org.junit.Assert.assertNotNull;



public class cardSteps {
    private Board board;
    private Tile[][] tiles;

    @Given("I have started a game with multiple {string}")
    public void iHaveStartedAGameWithMultiple(String level) {
        try {
            board.setCardLevel(level);
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

    }
}
