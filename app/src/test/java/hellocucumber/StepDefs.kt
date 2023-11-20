package hellocucumber

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todue.dataLayer.local.Tag
import com.example.todue.dataLayer.local.ToDo
import com.example.todue.ui.event.ToDoEvent
import io.cucumber.java.PendingException
import io.cucumber.java.en.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.junit.Assert.*
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions.*

class ToDoFactory {
    fun createNewToDo(title: String, description: String, tag: String, dueDate: String, finished: Boolean) {
        val title = title,
        val description = String,
        val tag = String,
        val dueDate = String,
        val finished = Boolean,

        data class ToDo(
            val title: String,
            val description: String,
            val tag: String,
            val dueDate: String,
            val finished: Boolean,

            @PrimaryKey(autoGenerate = true)
            val id: Int = 0
        )
    }
}

class StepDefs {
    val myNewToDo

    @Given ("That I press the \"+\" button")
    fun i_Create_A_New_ToDo() {
        val myNewToDo = ToDoFactory(
            title = "test",
            description = "Dette er en test",
            tag = "testingToDo",
            dueDate = "01-01-1971\n00:01",
            finished = false
        )
    }

    @Then ("the app should accept inputs")
    fun toBeDetermined(){
    }
}