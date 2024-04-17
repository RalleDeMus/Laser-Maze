package boardmethods;
import Board.Board;

import Tiles.LaserTile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class RotatingTileSteps {
    private Board board;
    @Given("a bord with a size of {int} and a square size of {int}")
    public void aBordWithASizeOfAndASquareSizeOf(int boardSize, int squareSize) {
        board = new Board(5,50);
    }

    @And("a LaserTile at position \\({double})")
    public void aLaserTileAtPosition(int x, int y) {
        board.setCursorPos(1,1);
        board.addTile(new LaserTile(true,true));

    }

    @When("the user rotates the LaserTile at \\({double})")
    public void theUserRotatesTheLaserTileAt(int x, int y) {
        board.setCursorPos(1,1);
        board.rotateSelectedTile();
    }

    @Then("the LaserTile at \\({double}) should be rotated")
    public void theLaserTileAtShouldBeRotated(int x, int y) {
        LaserTile rotatedTile = (LaserTile) board.tiles[1][1];
        int expectedOrientation = (rotatedTile.getOrientation() + 1) % 4; // Since orientation values are 0,1,2,3
        assertEquals("LaserTile should be rotated", expectedOrientation, rotatedTile.getOrientation());
    }
}
