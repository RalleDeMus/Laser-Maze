package boardmethods;
import Model.Logic.Board;
import Model.Tiles.LaserTile;

import Model.Tiles.Tile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddingLaserTileSteps {
    private Board board;
    @Given("a board with a size of {int} and a square size of {int}")
    public void aBoardWithASizeOfAndASquareSizeOf(int boardSize, int squareSize) {

        board = new Board(5,50);
    }

    @And("a cursor position at \\({double})")
    public void aCursorPositionAt(int x, int y) {
        Board.getInstance().setCursorPos(0,0);
    }

    @When("the user adds a LaserTile to the cursor position")
    public void theUserAddsALaserTileToTheCursorPosition() {
        board.addTile(new LaserTile(true,true));
    }

    @Then("the board should have a LaserTile at position \\({double})")
    public void theBoardShouldHaveALaserTileAtPosition(int x, int y) {
        Tile tileAtCursor = board.tiles[0][0];
        assertNotNull("LaserTile should be added to the board", tileAtCursor);
        assertTrue("Tile at cursor position should be a LaserTile", tileAtCursor instanceof LaserTile);

    }
}
