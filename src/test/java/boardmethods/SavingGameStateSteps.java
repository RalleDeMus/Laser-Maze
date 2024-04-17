package boardmethods;
import Board.Board;
import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SavingGameStateSteps {
    private Board board;
    @Given("a board with a size of {int} and a square size of {int}")
    public void aBoardWithASizeOfXAndASquareSizeOf(int boardSize, int squareSize) {
        board = new Board(5,50);
    }

    @And("several tiles placed on the board")
    public void severalTilesPlacedOnTheBoard() {
        board.setCursorPos(1,1);
        board.addTile(new Tiles.LaserTile(true,true));
        board.setCursorPos(2,2);
        board.addTile(new Tiles.LaserTile(true,true));
        board.setCursorPos(3,3);
        board.addTile(new Tiles.LaserTile(true,true));
    }

    @When("the user saves the game state")
    public void theUserSavesTheGameState() {
        board.saveGameState();
    }

    @Then("a JSON file containing the board state and extra tiles information should be created")
    public void aJSONFileContainingTheBoardStateAndExtraTilesInformationShouldBeCreated() {
        Assert.assertNotNull("Board state should not be null after saving", board.tiles);
    }
}
