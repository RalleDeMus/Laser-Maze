package LaserMethods;

import Model.Logic.Board;
import Model.Logic.Laser;
import Model.Logic.LaserCalculator;
import Model.Tiles.LaserTile;
import Model.Tiles.MirrorTile;
import Model.Tiles.SplitterTile;
import io.cucumber.java.en.*;

import java.awt.*;

import static org.junit.Assert.*;


public class LaserCalculation {

    Board board;
    @Given("i load level 6")
    public void iCreateABoardWithSomeTiles() {
        board = new Board("6");

        //Print all the tiles on the board


    }

    @And("rotate the tiles")
    public void rotateTheTiles() {


        // Rotate the tile at 3,0 4 times
        board.getCursorPos().setLocation(3,0);

        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);

        // Rotate the tile at (0,2) 3 times
        board.getCursorPos().setLocation(0,2);

        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);

        // Rotate the tile at (3,4) 3 times
        board.getCursorPos().setLocation(3,4);

        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);

        // Rotate the tile at (0,4) 1 time
        board.getCursorPos().setLocation(0,4);

        board.rotateSelectedTile(false);



    }

    @And("place a the splitter tile and rotate it")
    public void placeASplitterTile() {
        board.setSelectedTile(new SplitterTile(true, true,1));

        board.getCursorPos().setLocation(3,2);
        board.addTile(true);
        board.rotateSelectedTile(false);

        assertEquals(board.getSelectedTile().getOrientation(), 2);
    }

    @When("i calculate the laser tree")
    public void iCalculateTheLaserTree() {
        LaserCalculator.constructLaserTree(board);
    }

    @Then("the laser tree should be constructed")
    public void theLaserTreeShouldBeConstructed() {
        assertEquals("Laser tree should be constructed", board.getWin(), true);
    }
}
