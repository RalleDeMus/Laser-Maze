package boardmethods;
import Model.Logic.Board;
import Model.Tiles.LaserTile;

import Model.Tiles.Tile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class AddingLaserTileSteps {

    @Given("a board with size \\({int}) and squaresize \\({int})")
    public void aBoardWithSizeAndSquaresize(int boardSize, int squareSize) {
        assertEquals(boardSize, Board.getInstance().getBoardSize());
        assertEquals(Board.getInstance().getSquareSize(),squareSize);
    }

    @And("a cursor position at \\({int}) \\({int})")
    public void aCursorPositionAt(int x, int y) {
        Board.setCursorPos(x,y);
    }

    @When("the user adds a LaserTile to the cursor position")
    public void theUserAddsALaserTileToTheCursorPosition() {

        Board.addTile(new LaserTile(true,true,0),true);
    }

    @Then("the board should have a LaserTile at position \\({int}) \\({int})")
    public void theBoardShouldHaveALaserTileAtPosition(int x, int y) {
        Tile tileAtCursor = Board.tiles[0][0];
        assertNotNull("LaserTile should be added to the board", tileAtCursor);
        assertTrue("Tile at cursor position should be a LaserTile", tileAtCursor instanceof LaserTile);

    }



}












