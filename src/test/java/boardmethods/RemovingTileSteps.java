package boardmethods;
import Model.Logic.Board;
import Model.Tiles.MirrorTile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



import static org.junit.Assert.*;

public class RemovingTileSteps {
    Board board;
    @Given("a board with a size of {int} and a square size of {int}")
    public void aBoardWithASizeOfAndASquareSizeOf(int size, int squareSize) {
        board = new Board(size,squareSize,"0");
        assertEquals(board.getBoardSize(),5);
        assertEquals(board.getSquareSize(),100);
        assertEquals(board.getLevel(),"0");
    }


    @And("a MirrorTile at position x {int} and y {int}")
    public void aMirrorTileAtPositionXAndY(int x, int y) {
        MirrorTile mirrorTile = new MirrorTile(true,true);

        board.getTiles()[x][y] = mirrorTile;
        System.out.println(board.getTiles()[2][2]);
        assertTrue(board.getTiles()[2][2] instanceof MirrorTile);
    }

    @When("the user removes the MirrorTile at x {int} and y {int}")
    public void theUserRemovesTheMirrorTileAtXAndY(int x, int y) {
        board.getCursorPos().setLocation(x,y);
        board.removeTile();
        assertNull(board.getTiles()[2][2]);
    }

    @Then("the board should not have a tile at position x {int} and y {int}")
    public void theBoardShouldNotHaveATileAtPositionXAndY(int x, int y) {
        assertNull(board.getTiles()[x][y]);
    }
}
