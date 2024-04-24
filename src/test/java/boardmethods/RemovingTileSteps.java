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

    @Given("a board with a size of {int} and a square size of {int}")
    public void aBoardWithASizeOfXAndASquareSizeOf(int boardSize, int squareSize) {

        Board.getInstance();
    }

    @And("a MirrorTile at position \\({double})")
    public void aMirrorTileAtPosition(int x, int y) {
        Board.setCursorPos(2,2);
        Board.addTile(new MirrorTile(true,true,0),true);
    }

    @When("the user removes the MirrorTile at \\({double})")
    public void theUserRemovesTheMirrorTileAt(int x, int y) {
        Board.setCursorPos(2,2);
        Board.removeTile();
    }

    @Then("the board should not have a tile at position \\({double})")
    public void theBoardShouldNotHaveATileAtPosition(int x, int y) {
        Tile removedTile = Board.tiles[2][2];
        assertNull("Tile should be removed from the board", removedTile);
    }
}
