import io.cucumber.groovy.PendingException

import static io.cucumber.groovy.Hooks.*
import static io.cucumber.groovy.EN.*

Given(~/^I have initialized a game card with multiple "([^"]*)"$/) { String arg1 ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}