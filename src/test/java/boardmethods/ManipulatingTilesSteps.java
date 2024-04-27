package boardmethods;
import Model.Logic.Board;
import Model.Tiles.*;

import Model.Tiles.MirrorTile;
import Model.Tiles.Tile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;

import static org.junit.Assert.*;

public class ManipulatingTilesSteps {

    Board board;
    Tile selectedTile;

    @Given("a board \\({int},{int})")
    public void aBoard(int boardSize, int squareSize) {

        board = new Board(boardSize, squareSize, "0");
    }

    @And("a cursor position at \\({int},{int})")
    public void aCursorPositionAt(int x, int y) {

        board.setCursorLocation(0,0);
    }

    @When("the user adds a {string} to the cursor position")
    public void the_user_adds_a_mirror_tile_to_the_cursor_position(String tileType) {
        selectedTile = createTileOfType(tileType);
        board.setSelectedTile(selectedTile);
        board.addTile(true);
    }
    @Then("the board should have a {string} at position \\({int},{int})")
    public void the_board_should_have_a_mirror_tile_at_position(String tileType,int x, int y) {
        Tile tileAtCursor = board.getTiles()[x][y];
        assertNotNull(tileType + " should be added to the board", tileAtCursor);
        assertEquals("Tile at cursor position should be a " + tileType, selectedTile.getClass(), tileAtCursor.getClass());
    }

    @When("the user removes the tile at x {int} and y {int}")
    public void theUserRemovesTheMirrorTileAtXAndY(int x, int y) {
        board.getCursorPos().setLocation(x,y);
        board.removeTile();
        assertNull(board.getTiles()[2][2]);
    }

    @Then("the board should not have a tile at position x {int} and y {int}")
    public void theBoardShouldNotHaveATileAtPositionXAndY(int x, int y) {
        assertNull(board.getTiles()[x][y]);
    }


    private Tile createTileOfType(String tileType) {
        switch (tileType) {
            case "MirrorTile": return new MirrorTile(true, true);
            case "LaserTile": return new LaserTile(true, true);
            case "SplitterTile": return new SplitterTile(true,true);
            case "DoubleTile": return new DoubleTile(true,true);
            case "CheckPointTile": return new CheckPointTile(true,true);
            default: throw new IllegalArgumentException("Unknown tile type: " + tileType);
        }
    }

    int initialOrientation;
    @Given("the user adds a MirrorTile to the cursor position")
    public void theUserAddsAMirrorTileToTheCursorPosition() {
        selectedTile = new MirrorTile(true, true);
        board.setSelectedTile(selectedTile);
        board.addTile(true);
        Point cp = board.getCursorPos();

        initialOrientation = board.getTiles()[cp.x][cp.y].getOrientation();
    }


    @When("the user rotates the tile at cursor position")
    public void theUserRotatesTheTileAt() {
        board.rotateSelectedTile(false);
    }

    @Then("the tile at cursor position should be rotated")
    public void theTileAtShouldBeRotated() {
        Point cp = board.getCursorPos();
        int tileOrientation = board.getTiles()[cp.x][cp.y].getOrientation();
        int expectedOrientation = (initialOrientation+ 1) % 4; // Since orientation values are 0,1,2,3
        assertEquals("LaserTile should be rotated", expectedOrientation, tileOrientation);

    }
}












