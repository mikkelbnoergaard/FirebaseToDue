
package hellocucumber

import androidx.room.PrimaryKey
//import com.example.todue.dataLayer.local.ToDo
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*


class ToDo(
    val title: String,
    val description: String,
    val tag: String,
    val dueDate: String,
    val finished: Boolean,
)

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

    var title = ""
    var description = ""
    var tag = ""
    var dueDate = ""
    var finished = false

    var dummyToDo = CreateToDo.createToDo(title, description, tag, dueDate, finished)

    @Given("The user creates a to-do with title {string}, description {string}, tag {string} and dueDate {string}")
    fun user_creates_a_to_do(toDoTitle: String, toDoDescription: String, toDoTag: String, toDoDueDate: String) {
        title = toDoTitle
        description = toDoDescription
        tag = toDoTag
        dueDate = toDoDueDate
        finished = false
    }

    @Then("The to-do object is created")
    fun the_to_do_object_is_created() {
        val toDo = CreateToDo.createToDo(title, description, tag, dueDate, finished)
        dummyToDo = toDo
    }

    @When("I view that to-do I should see {string}, {string}, {string} and {string}")
    fun i_view_that_to_do_i_should_see(expectedToDoTitle: String, expectedToDoDescription: String, expectedToDoTag: String, expectedToDoDueDate: String) {
        assertEquals(expectedToDoTitle, dummyToDo.title)
        assertEquals(expectedToDoDescription, dummyToDo.description)
        assertEquals(expectedToDoTag, dummyToDo.tag)
        assertEquals(expectedToDoDueDate, dummyToDo.dueDate)
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