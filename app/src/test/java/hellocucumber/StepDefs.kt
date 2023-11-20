
package hellocucumber

import com.example.todue.dataLayer.local.ToDo
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*


internal object IsItFriday {
    fun isItFriday(today: String): String? {
        return if(today == "Friday") {
            "TGIF"
        } else{
            "Nope"
        }
    }
}

internal object CreateToDo {
    fun createToDo(
        title: String,
        description: String,
        tag: String,
        dueDate: String,
        finished: Boolean
    ): ToDo {

        return ToDo(
            title = title,
            description = description,
            tag = tag,
            dueDate = dueDate,
            finished = finished
        )
    }
}

class Stepdefs {

    //create to-do, should be edited to actual object is created
    var title = ""
    var description = ""
    var tag = ""
    var dueDate = ""
    var finished = false

    @Given("I create a to-do")
    fun i_create_a_to_do() {
        title = "Cucumber title"
        description = "Cucumber description"
        tag = "Cucumber tag"
        dueDate = "20-11-2023\n22:30"
        finished = false
    }

    @Then("The to-do object is created")
    fun the_to_do_object_is_created() {
        //var toDo = CreateToDo.createToDo(title, description, tag, dueDate, finished)
    }

    @When("I view that to-do I should see {string}")
    fun i_view_that_to_do_i_should_see(expectedToDoTitle: String) {
        assertEquals(expectedToDoTitle, title)
    }




    //is it friday
    var today: String = ""
    var actualAnswer: String = ""

    @Given("today is Friday")
    fun today_is_Friday() {
        today = "Friday"
    }
    @Given("today is Sunday")
    fun today_is_sunday() {
        today = "Sunday"
    }
    @When("I ask whether it's Friday yet")
    fun i_ask_whether_it_s_friday_yet() {
        actualAnswer = IsItFriday.isItFriday(today).toString()
    }
    @Then("I should be told {string}")
    fun i_should_be_told(expectedAnswer: String) {
        assertEquals(expectedAnswer, actualAnswer)
    }
}