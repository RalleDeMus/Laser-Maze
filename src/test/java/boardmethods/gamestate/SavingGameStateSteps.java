package boardmethods.gamestate;

import Model.Logic.Board;

import Model.Logic.JSONSaving;
import Model.Tiles.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SavingGameStateSteps {
    Board board;
    @Given("a board")
    public void aBoard() {
        board = new Board("0");
        assertNotNull(board);
    }

    @And("a MirrorTile that is not movable and not rotatable placed on the board")
    public void aMirrorTileThatIsNotMovableAndNotRotatablePlacedOnTheBoard() {
        board.setSelectedTile(new MirrorTile(false,false));
        board.getCursorPos().setLocation(0,0);
        board.addTile(false);
        assertTrue(board.getTiles()[0][0] instanceof MirrorTile);
        assertFalse(board.getTiles()[0][0].getIsRotatable());
        assertFalse(board.getTiles()[0][0].getIsMovable());

    }

    @And("a LaserTile that is movable and rotatable placed on the board")
    public void aLaserTileThatIsMovableAndRotatablePlacedOnTheBoard() {
        board.setSelectedTile(new LaserTile(true,true));
        board.getCursorPos().setLocation(2,2);
        board.addTile(false);
        assertTrue(board.getTiles()[2][2] instanceof LaserTile);
        assertTrue(board.getTiles()[2][2].getIsRotatable());
        assertTrue(board.getTiles()[2][2].getIsMovable());
    }

    @When("the user triggers the save game state")
    public void theUserTriggersTheSaveGameState() {
        JSONSaving.saveGameState("test",board);
    }

    @Then("a JSON file containing the board state and extra tiles information should be created")
    public void aJSONFileContainingTheBoardStateAndExtraTilesInformationShouldBeCreated() {
        // Check if the file exists
        assertTrue(Files.exists(Paths.get("src/main/levels/custom/test.json")));
    }

    @Given("an empty board")
    public void anEmptyBoard() {
    }

    @And("{int} extra MirrorTiles and {int} extra SplitterTile")
    public void extraMirrorTilesAndExtraSplitterTile(int arg0, int arg1) {
    }
}
