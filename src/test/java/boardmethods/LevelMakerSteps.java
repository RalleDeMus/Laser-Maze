package boardmethods;

import Model.Logic.Board;
import Model.Logic.Card;
import Model.Tiles.LaserTile;
import Model.Tiles.MirrorTile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class LevelMakerSteps {
    Board board;
    String customLevel;

    @When("the user places a MirrorTile and LaserTile on the board")
    public void theUserPlacesAMirrorTileAndLaserTileOnTheBoard() {
        board = new Board("0");
        board.getCursorPos().setLocation(1,1);
        board.setSelectedTile(new LaserTile(false, false,1));
        board.addTile(false);
        board.getCursorPos().setLocation(3,3);
        board.setSelectedTile(new MirrorTile(false, false,0));
        board.addTile(false);
        assertTrue(board.getTiles()[1][1] instanceof LaserTile);
        assertTrue(board.getTiles()[3][3] instanceof MirrorTile);

    }

    @And("the user adds increments number of targets")
    public void theUserAddsIncrementsNumberOfTargets() {

    }

    @And("the user increments number of placeable MirrorTiles")
    public void theUserIncrementsNumberOfPlaceableMirrorTiles() {
    }

    @Then("the user should be able to save the level")
    public void theUserShouldBeAbleToSaveTheLevel() {

    }

    @Given("a newly created level")
    public void aNewlyCreatedLevel() {
        board = new Board("levelMakerTest");
        assertTrue(board.getTiles()[1][1] instanceof LaserTile);
        assertTrue(board.getTiles()[3][3] instanceof MirrorTile);
        assertEquals(board.get_game_info_by_index(2),1);
    }

    @When("the user places the correct tiles")
    public void theUserPlacesTheCorrectTiles() {
        //use int to tile
        board.setSelectedTile();


    }

    @And("the user completes the level")
    public void theUserCompletesTheLevel() {
    }

    @Given("a selected custom level")
    public void aSelectedCustomLevel() {
        customLevel = "levelMakerTest";
    }

    @When("the player loads the level")
    public void thePlayerLoadsTheLevel() {
        board = new Board(customLevel);
        assertTrue(board.getTiles()[1][1] instanceof LaserTile);
        assertTrue(board.getTiles()[3][3] instanceof MirrorTile);
        assertEquals(board.get_game_info_by_index(2),1);
    }

    @And("plays the level")
    public void playsTheLevel() {
    }

    @Then("the player can complete the level")
    public void thePlayerCanCompleteTheLevel() {
    }
}
