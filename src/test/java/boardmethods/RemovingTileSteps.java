package boardmethods;
import Model.Logic.Board;
import Model.Tiles.MirrorTile;

import Model.Tiles.Tile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RemovingTileSteps {
    @And("a MirrorTile at position \\({int}) \\({int})")
    public void a_mirror_tile_at_position(Integer x, Integer y) {
        Board.setCursorPos(2,2);
        Board.addTile(new MirrorTile(true,true,0),true);
    }

    @When("the user removes the MirrorTile at \\({int}) \\({int})")
    public void theUserRemovesTheMirrorTileAt(int x, int y) {
        Board.setCursorPos(2,2);
        Board.removeTile();
    }

    @Then("the board should not have a tile at position \\({int}) \\({int})")
    public void theBoardShouldNotHaveATileAtPosition(int x, int y) {
        Tile removedTile = Board.tiles[2][2];
        assertNull("Tile should be removed from the board", removedTile);
    }
}


