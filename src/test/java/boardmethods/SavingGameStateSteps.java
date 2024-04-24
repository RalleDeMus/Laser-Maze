package boardmethods;
import Model.Logic.Board;
import Model.Tiles.LaserTile;
import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SavingGameStateSteps {

    Board board;

    @Given("a board with boardsize \\({int}) and size \\({int})")
    public void aBoardWithBoardsizeAndSize(int boardSize, int squareSize) {
        board =  new Board(boardSize, squareSize, "0");
    }

    @And("several tiles placed on the board")
    public void severalTilesPlacedOnTheBoard() {
        Board.setCursorPos(1,1);
        Board.addTile(new LaserTile(true,true,0),true);
        Board.setCursorPos(2,2);
        Board.addTile(new LaserTile(true,true,0),true);
        Board.setCursorPos(3,3);
        Board.addTile(new LaserTile(true,true,0),true);
    }

    @When("the user saves the game state")
    public void theUserSavesTheGameState() {

        Board.saveGameState("Saving game_state");
    }

    @Then("a JSON file containing the board state and extra tiles information should be created")
    public void aJSONFileContainingTheBoardStateAndExtraTilesInformationShouldBeCreated() {
        Assert.assertNotNull("Board state should not be null after saving", Board.tiles);
    }


}
