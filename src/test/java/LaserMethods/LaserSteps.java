package LaserMethods;

import Model.Logic.Laser;
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
        assert laser.getX() == x;
        assert laser.getY() == y;
        assert laser.getOrientation() == orientation;
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
        assert laser.getX() == x;
        assert laser.getY() == y;
    }

    @When("I set the new position to {int}, {int}")
    public void iSetTheNewPositionToNewXNewY(int newX, int newY) {
        laser.setPos(newX, newY);
    }

    @Then("the laser should move to {int}, {int}")
    public void theLaserShouldMoveToNewXNewY(int newX, int newY) {
        assert laser.getX() == newX;
        assert laser.getY() == newY;
    }

    @When("I set the orientation to {int}")
    public void iSetTheOrientationToNewOrientation(int newOrientation) {
        laser.setOrientation(newOrientation);
    }

    @Then("the orientation should be {int}")
    public void theOrientationShouldBeNewOrientation(int newOrientation) {
        assert laser.getOrientation() == newOrientation;
    }

    @And("another laser is positioned at {int}, {int} with orientation {int}")
    public void anotherLaserIsPositionedAtWithOrientation(int x, int y, int orientation) {
        anotherLaser = new Laser(x, y, orientation);
    }

    @When("I compare the two lasers")
    public void iCompareTheTwoLasers() {
        assert laser.Equals(anotherLaser);
    }

    @Then("they should be considered equal")
    public void theyShouldBeConsideredEqual() {
        assert laser.Equals(anotherLaser);
    }

    @When("I check the string representation of the laser")
    public void iCheckTheStringRepresentationOfTheLaser() {
        output = laser.toString();
    }

    @Then("the output should be {string}")
    public void theOutputShouldBe(String expectedOutput) {
        assert output.equals(expectedOutput) : "Expected: " + expectedOutput + ", but got: " + output;
    }


}

