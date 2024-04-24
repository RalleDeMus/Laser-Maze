package boardmethods;
import Model.Logic.Board;
import Model.Tiles.MirrorTile;

import Model.Tiles.Tile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNull;

public class RemovingTileSteps {

    Board board;

    @Given("a board with boardsize \\({int}) and squaresize \\({int})")
    public void aBoardWithBoardsizeAndSquaresize(int boardSize, int squareSize) {
        board =  new Board(boardSize, squareSize, "0");

    }

    @And("a MirrorTile at position \\({int}) \\({int})")
    public void aMirrorTileAtPosition(int x, int y) {
        board.setCursorPos(2,2);
        board.addTile(new MirrorTile(true,true),true);
    }

    @When("the user removes the MirrorTile at \\({int}) \\({int})")
    public void theUserRemovesTheMirrorTileAt(int x, int y) {
        board.setCursorPos(2,2);
        board.removeTile();
    }

    @Then("the board should not have a tile at position \\({int}) \\({int})")
    public void theBoardShouldNotHaveATileAtPosition(int x, int y) {
        Tile removedTile = board.tiles[2][2];
        assertNull("Tile should be removed from the board", removedTile);
    }


}
