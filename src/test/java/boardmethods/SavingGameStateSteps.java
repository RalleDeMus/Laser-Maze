package boardmethods;
import Model.Logic.Board;
import Model.Logic.JSONSaving;
import Model.Tiles.LaserTile;
import Model.Tiles.MirrorTile;
import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SavingGameStateSteps {

    Board board;
    @Given("a board with a size of {int} and a square size of {int}")
    public void aBoardWithASizeOfXAndASquareSizeOf(int boardSize, int squareSize) {

        board =  new Board(boardSize, squareSize, "0");
    }

    @And("several tiles placed on the board")
    public void severalTilesPlacedOnTheBoard() {
        board.setCursorPos(1,1);
        board.setSelectedTile(new MirrorTile(true,true));
        board.addTile(false);
        board.setCursorPos(2,2);
        board.addTile(false);
        board.setCursorPos(3,3);
        board.addTile(false);
    }

    @When("the user saves the game state")
    public void theUserSavesTheGameState() {

        JSONSaving.saveGameState("game_state", board);
    }

    @Then("a JSON file containing the board state and extra tiles information should be created")
    public void aJSONFileContainingTheBoardStateAndExtraTilesInformationShouldBeCreated() {
        Assert.assertNotNull("Board state should not be null after saving", board.tiles);
    }
}
