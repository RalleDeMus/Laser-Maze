package LaserMethods;

import Model.Logic.Board;
import Model.Logic.LaserCalculator;
import Model.Tiles.GameTiles.SplitterTile;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;


public class CheckWinConditionSteps {

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
        //System.out.println(board.getTiles()[0][3].getOrientation());
        assertEquals(board.getTiles()[0][3].getOrientation(), 3);


        // Rotate the tile at (0,2) 3 times
        board.getCursorPos().setLocation(0,2);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        //System.out.println(board.getTiles()[2][0].getOrientation());
        assertEquals(board.getTiles()[2][0].getOrientation(), 2);

        // Rotate the tile at (3,4) 3 times
        board.getCursorPos().setLocation(3,4);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        board.rotateSelectedTile(false);
        //System.out.println(board.getTiles()[4][3].getOrientation());
        assertEquals(board.getTiles()[4][3].getOrientation(), 2);


        // Rotate the tile at (0,4) 1 time
        board.getCursorPos().setLocation(0,4);
        board.rotateSelectedTile(false);
        //System.out.println(board.getTiles()[4][0].getOrientation());
        assertEquals(board.getTiles()[4][0].getOrientation(), 0);



    }

    @And("place a the splitter tile and rotate it")
    public void placeASplitterTile() {
        board.setSelectedTile(new SplitterTile(true, true,1));

        board.getCursorPos().setLocation(3,2);
        board.addTile(false);


        assertEquals(board.getSelectedTile().getOrientation(), 1);


    }



    @Then("i calculate the laser tree and the laser tree should be constructed")
    public void theLaserTreeShouldBeConstructed() {
        LaserCalculator.constructLaserTree(board);
        assertEquals("Laser tree should be constructed", true,board.getWin());
    }
}
