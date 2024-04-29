package LaserMethods;

import Model.Logic.Laser;
import static org.junit.Assert.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LaserSteps {
    private Laser laser;
    private Laser anotherLaser;
    private String output;

    @Given("I initialize a laser at {int}, {int} with orientation {int}")
    public void iInitializeALaserAtWithOrientation(int x, int y, int orientation) {
        laser = new Laser(x, y, orientation);
    }

    @Then("the laser should be positioned at {int}, {int} with orientation {int}")
    public void theLaserShouldBePositionedAtWithOrientation(int x, int y, int orientation) {
        assertEquals("X coordinate mismatch", x, laser.getX());
        assertEquals("Y coordinate mismatch", y, laser.getY());
        assertEquals("Orientation mismatch", orientation, laser.getOrientation());
    }

    @Given("a laser is positioned at {int}, {int} with orientation {int}")
    public void aLaserIsPositionedAtWithOrientation(int x, int y, int orientation) {
        laser = new Laser(x, y, orientation);
    }

    @When("I retrieve the coordinates")
    public void iRetrieveTheCoordinates() {
        // This step does not need implementation as it's used for context
    }

    @Then("the result should be x={int} and y={int}")
    public void theResultShouldBeXAndY(int x, int y) {
        assertEquals("X coordinate mismatch on retrieval", x, laser.getX());
        assertEquals("Y coordinate mismatch on retrieval", y, laser.getY());
    }

    @When("I set the new position to {int}, {int}")
    public void iSetTheNewPositionToNewXNewY(int newX, int newY) {
        laser.setPos(newX, newY);
    }

    @Then("the laser should move to {int}, {int}")
    public void theLaserShouldMoveToNewXNewY(int newX, int newY) {
        assertEquals("New X coordinate mismatch after move", newX, laser.getX());
        assertEquals("New Y coordinate mismatch after move", newY, laser.getY());
    }

    @When("I set the orientation to {int}")
    public void iSetTheOrientationToNewOrientation(int newOrientation) {
        laser.setOrientation(newOrientation);
    }

    @Then("the orientation should be {int}")
    public void theOrientationShouldBeNewOrientation(int newOrientation) {
        assertEquals("Orientation mismatch after setting new orientation", newOrientation, laser.getOrientation());
    }

    @And("another laser is positioned at {int}, {int} with orientation {int}")
    public void anotherLaserIsPositionedAtWithOrientation(int x, int y, int orientation) {
        anotherLaser = new Laser(x, y, orientation);
    }

    @When("I compare the two lasers")
    public void iCompareTheTwoLasers() {
        assertTrue("Lasers should be considered equal", laser.Equals(anotherLaser));
    }

    @Then("they should be considered equal")
    public void theyShouldBeConsideredEqual() {
        assertTrue("Lasers comparison failed", laser.Equals(anotherLaser));
    }

    @When("I check the string representation of the laser")
    public void iCheckTheStringRepresentationOfTheLaser() {
        output = laser.toString();
    }

    @Then("the output should be {string}")
    public void theOutputShouldBe(String expectedOutput) {
        assertEquals("String representation mismatch", expectedOutput, output);
    }
}
