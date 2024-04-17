package board;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddingLaserTileSteps {
    @Given("a board with a size of {int}x{int} and a square size of {int}")
    public void aBoardWithASizeOfXAndASquareSizeOf(int arg0, int arg1, int arg2) {
    }

    @And("a cursor position at \\({double})")
    public void aCursorPositionAt(int arg0, int arg1) {
    }

    @When("the user adds a LaserTile to the cursor position")
    public void theUserAddsALaserTileToTheCursorPosition() {
    }

    @Then("the board should have a LaserTile at position \\({double})")
    public void theBoardShouldHaveALaserTileAtPosition(int arg0, int arg1) {
    }
}
