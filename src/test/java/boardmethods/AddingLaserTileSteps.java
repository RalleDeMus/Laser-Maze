package boardmethods;
import Model.Logic.Board;
import Model.Tiles.LaserTile;

import Model.Tiles.MirrorTile;
import Model.Tiles.Tile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddingLaserTileSteps {

    Board board;

    @Given("a board \\({int},{int})")
    public void aBoard(int boardSize, int squareSize) {
        board = new Board(boardSize, squareSize, "0");
    }

    @And("a cursor position at \\({int},{int})")
    public void aCursorPositionAt(int x, int y) {

        board.setCursorPos(0,0);
    }

    @When("the user adds a LaserTile to the cursor position")
    public void the_user_adds_a_mirror_tile_to_the_cursor_position() {
        board.setSelectedTile(new LaserTile(true,true));
        board.addTile(true);
    }
    @Then("the board should have a LaserTile at position \\({int},{int})")
    public void the_board_should_have_a_mirror_tile_at_position(int x, int y) {
        Tile tileAtCursor = board.getTiles()[x][y];
        assertNotNull("LaserTile should be added to the board", tileAtCursor);
        assertTrue("Tile at cursor position should be a LaserTile", tileAtCursor instanceof LaserTile);

    }


}












