package hellocucumber

import io.cucumber.java.en.*
import org.junit.jupiter.api.Assertions.*
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert.*
import org.junit.Assert.assertEquals

class IsItFriday {
    companion object {
        fun isItFriday(today: String): String {
            return if ("Friday" == today) "TGIF" else "Nope"
        }
    }
}

class StepDefs {
    var today: String? = null
    var actualAnswer: String? = null
    @Given("an example scenario")
    fun anExampleScenario() {
    }

    @When("all step definitions are implemented")
    fun allStepDefinitionsAreImplemented() {
    }

    @Then("the scenario passes")
    fun theScenarioPasses() {
    }

    @Given("today is {string}")
    fun today_is(today: String?) {
        this.today = today
    }

    @When("I ask whether it's Friday yet")
    fun i_ask_whether_it_s_Friday_yet() {
        actualAnswer = today?.let { IsItFriday.isItFriday(it) }
    }

    @Then("I should be told {string}")
    fun i_should_be_told(expectedAnswer: String?) {
        assertEquals(expectedAnswer, actualAnswer)
    }

}