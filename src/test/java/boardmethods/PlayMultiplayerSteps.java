package boardmethods;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Model.Logic.*;

import static org.junit.Assert.*;

public class PlayMultiplayerSteps {


    @Given("a level is selected")
    public void aLevelIsSelected() {
        new Board("1");
    }
    MultiPlayerLogic multiPlayerLogic = new MultiPlayerLogic(2);
    @When("the first player solves the level in {int} second")
    public void theFirstPlayerSolvesTheLevelInSecond(int seconds) {
        assertEquals("Player 1", multiPlayerLogic.getCurrentPlayer());
        multiPlayerLogic.addPlayerTime(seconds);

    }

    @And("the second player solves the level in {int} seconds")
    public void theSecondPlayerSolvesTheLevelInSeconds(int seconds) {
        assertEquals("Player 2", multiPlayerLogic.getCurrentPlayer());
        assertTrue(multiPlayerLogic.playerEqualPlayers());
        multiPlayerLogic.addPlayerTime(seconds);

    }

    @Then("the scoreboard is shown")
    public void theScoreboardIsShown() {
        assertEquals("Player 1: 1.0", multiPlayerLogic.getSortedPlayerTimes().get(0));
        assertEquals("Player 2: 2.0", multiPlayerLogic.getSortedPlayerTimes().get(1));
    }


}
