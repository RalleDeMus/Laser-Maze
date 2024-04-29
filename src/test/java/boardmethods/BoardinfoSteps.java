package boardmethods;

import Model.Logic.BoardInfo;
import Model.Tiles.Tile;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class BoardinfoSteps {
    private int input;
    private Tile result;
    @Given("a valid integer input {int}")
    public void aValidIntegerInputInput(int num) {
        this.input = num;
    }

    @When("the intToTile method is called")
    public void theIntToTileMethodIsCalled() {
        result = BoardInfo.intToTile(input);
    }

    @Then("it should return a {string} object")
    public void itShouldReturnATileObject(String tileClassName) {
        assertNotNull(result);
        assertEquals(tileClassName, result.getClass().getSimpleName());
    }

    @Given("an invalid integer input {int}")
    public void anInvalidIntegerInput(int num) {
        this.input = num;
    }

    @Then("it should return null")
    public void itShouldReturnNull() {
        assertNull(result);
    }
}
