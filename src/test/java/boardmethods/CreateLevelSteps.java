package boardmethods;

import Model.Logic.*;
import Model.Tiles.GameTiles.LaserTile;
import Model.Tiles.GameTiles.MirrorTile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class CreateLevelSteps {
    Board board = new Board("0");
    String customLevel;

    void rotatetimes(int times) {
        for (int i = 0; i < times; i++) {
            board.rotateSelectedTile(true);
        }
    }

    @When("the user places a MirrorTile and LaserTile on the board")
    public void theUserPlacesAMirrorTileAndLaserTileOnTheBoard() {
        board.getCursorPos().setLocation(1, 1);
        board.setSelectedTile(new LaserTile(false, true, 0));
        board.addTile(false);
        board.getCursorPos().setLocation(3, 3);
        board.setSelectedTile(new MirrorTile(false, false, 0));
        board.addTile(false);
        assertTrue(board.getTiles()[1][1] instanceof LaserTile);
        assertTrue(board.getTiles()[3][3] instanceof MirrorTile);

    }
    @And("makes the LaserTile rotateable")
    public void makesTheLaserTileRotateable() {
        board.getCursorPos().setLocation(1, 1);
        rotatetimes(4);
        assertEquals(4, board.getTiles()[1][1].getOrientation());

    }

    LevelMakerLogic levelMakerLogic = new LevelMakerLogic();

    @And("the user adds increments number of targets")
    public void theUserAddsIncrementsNumberOfTargets() {
        levelMakerLogic.decrementTargets();
        levelMakerLogic.incrementTargets();
        levelMakerLogic.decrementTargets();
        levelMakerLogic.incrementTargets();
        assertEquals(board.get_game_info()[4], 1);

    }


    @And("the user increments number of placeable MirrorTiles")
    public void theUserIncrementsNumberOfPlaceableMirrorTiles() {
        levelMakerLogic.changeTileCount(0, true);

        assertEquals(levelMakerLogic.getTileCounts()[0], 1);
    }

    @Then("the user should be able to save the temporary level")
    public void theUserShouldBeAbleToSaveTheTemporaryLevel() {
        JSONSaving.saveLevelWithFreeRotations(board, levelMakerLogic.getTileCounts(), levelMakerLogic.getTargets());
    }


    @Given("a newly created level")
    public void aNewlyCreatedLevel() {
        board.setCardLevel("temp");
        assertTrue(board.getTiles()[1][1] instanceof LaserTile);
        assertTrue(board.getTiles()[3][3] instanceof MirrorTile);
        assertEquals(board.get_game_info_by_index(0), 1);
    }

    @When("the user places the correct tiles")
    public void theUserPlacesTheCorrectTiles() {
        board.getCursorPos().setLocation(1, 1);
        rotatetimes(2);
        board.setSelectedTile(new MirrorTile(true, true));
        board.getCursorPos().setLocation(1, 3);
        board.addTile(false);
        rotatetimes(3);
        assertEquals(board.getTiles()[3][1].getOrientation(), 3);
        assertEquals(board.get_game_info_by_index(0), 0);
        assertTrue(board.getSelectedTile() instanceof MirrorTile);

    }

    @And("the user completes the level")
    public void theUserCompletesTheLevel() {
        LaserCalculator.constructLaserTree(board);
        assertTrue(board.getWin());
    }

    @Then("the user should be able to save the level")
    public void theUserShouldBeAbleToSaveTheLevel() {
        JSONSaving.saveTempAs("levelMakerTest");
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
        assertEquals(board.get_game_info_by_index(0),1);
    }

    @And("plays the level")
    public void playsTheLevel() {
        board.getCursorPos().setLocation(1, 1);
        rotatetimes(2);
        board.setSelectedTile(new MirrorTile(true, true));
        board.getCursorPos().setLocation(1, 3);
        board.addTile(false);
        rotatetimes(3);
        assertEquals(board.getTiles()[3][1].getOrientation(), 3);
        assertEquals(board.get_game_info_by_index(0), 0);
    }

    @Then("the player can complete the level")
    public void thePlayerCanCompleteTheLevel() {
        LaserCalculator.constructLaserTree(board);
        board.setLaserWasFired(true);
        assertTrue(board.getLaserFired());
        assertTrue(board.getWin());
    }



}


