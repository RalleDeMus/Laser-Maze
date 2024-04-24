package boardmethods;
import Model.Logic.Board;

import Model.Tiles.LaserTile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class RotatingTileSteps {

    Board board;

    @Given("a board with size \\({int}) and size \\({int})")
    public void aBoardWithSizeAndSize(int boardSize, int squareSize) {
        board =  new Board(boardSize, squareSize, "0");
    }

    @And("a LaserTile at position \\({int}) \\({int})")
    public void aLaserTileAtPosition(int x, int y) {
        board.setCursorPos(1,1);
        board.addTile(new LaserTile(true,true),true);
    }

    @When("the user rotates the LaserTile at \\({int}) \\({int})")
    public void theUserRotatesTheLaserTileAt(int x, int y) {
        board.setCursorPos(1,1);
        board.rotateSelectedTile(false);
    }
    @Then("the LaserTile at \\({int}) \\({int}) should be rotated")
    public void theLaserTileAtShouldBeRotated(int x, int y) {
        LaserTile rotatedTile = (LaserTile) board.tiles[1][1];
        int expectedOrientation = (rotatedTile.getOrientation() + 1) % 4; // Since orientation values are 0,1,2,3
        assertEquals("LaserTile should be rotated", expectedOrientation, rotatedTile.getOrientation());
    }


}



