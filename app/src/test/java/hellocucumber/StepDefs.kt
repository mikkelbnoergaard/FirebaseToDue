
package hellocucumber

import androidx.room.PrimaryKey
//import com.example.todue.dataLayer.local.ToDo
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*


class ToDo(
    var title: String,
    var description: String,
    var tag: String,
    var dueDate: String,
    var finished: Boolean,
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

    //editing_a_todo
    //dummy used to store data outside of functions
    var dummyToDo1 = CreateToDo.createToDo("", "", "", "", false)
    @Given("The user has already created a to-do")
    fun user_has_created_a_to_do(){
        var toDo = CreateToDo.createToDo(
            title = "UserCreatedTitle",
            description = "UserCreatedDescription",
            tag = "UserCreatedTag",
            dueDate = "20-11-2023\n22:30",
            finished = false
        )
        dummyToDo1 = toDo
    }

    @When("The user edits the to-do information to {string}, {string}, {string} and {string}")
    fun user_edits_to_do(editedTitle: String, editedDescription: String, editedTag: String, editedDueDate: String){
        dummyToDo1.title = editedTitle
        dummyToDo1.description = editedDescription
        dummyToDo1.tag = editedTag
        dummyToDo1.dueDate = editedDueDate
    }

    @Then("I view the edited to-do and I should see {string}, {string}, {string} and {string}")
    fun i_view_the_edited_to_do_and_i_should_see(expectedTitle: String, expectedDescription: String, expectedTag: String, expectedDueDate: String){
        assertEquals(dummyToDo1.title, expectedTitle)
        assertEquals(dummyToDo1.description, expectedDescription)
        assertEquals(dummyToDo1.tag, expectedTag)
        assertEquals(dummyToDo1.dueDate, expectedDueDate)
    }





    //creating_a_todo
    var title = ""
    var description = ""
    var tag = ""
    var dueDate = ""
    var finished = false

    //dummy used to store data outside of functions
    var dummyToDo2 = CreateToDo.createToDo(title, description, tag, dueDate, finished)

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
        dummyToDo2 = toDo
    }

    @When("I view that to-do I should see {string}, {string}, {string} and {string}")
    fun i_view_that_to_do_i_should_see(expectedToDoTitle: String, expectedToDoDescription: String, expectedToDoTag: String, expectedToDoDueDate: String) {
        assertEquals(expectedToDoTitle, dummyToDo2.title)
        assertEquals(expectedToDoDescription, dummyToDo2.description)
        assertEquals(expectedToDoTag, dummyToDo2.tag)
        assertEquals(expectedToDoDueDate, dummyToDo2.dueDate)
    }




    //is_it_friday
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