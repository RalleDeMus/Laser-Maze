package boardmethods;
import Model.Logic.Board;

import Model.Tiles.LaserTile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RotatingTileSteps {

    private LaserTile laserTile;

    @And("a LaserTile at position \\({int}) \\({int})")
    public void aLaserTileAtPosition(int x, int y) {

        Board.setCursorPos(x,y);
        LaserTile laserTile = new LaserTile(true,true,0);
        // Orientation is 0
        Board.getInstance().addTile(laserTile, true);
        Board.getInstance().setSelectedTile(laserTile);
    }

    @When("the user rotates the LaserTile at \\({int}) \\({int})")
    public void theUserRotatesTheLaserTileAt(int x, int y) {
        Board.setCursorPos(x,y);
        Board.rotateSelectedTile(false);
    }

    @Then("the LaserTile at \\({int}) \\({int}) should be rotated")
    public void theLaserTileAtShouldBeRotated(int x, int y) {
        // Orientation of laserTile should be 0+1=1
        int actualOrientation = (laserTile.getOrientation());
        assertEquals("LaserTile should be rotated", 1, actualOrientation);
    }
}




