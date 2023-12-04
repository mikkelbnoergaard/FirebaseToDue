package com.example.todue.ui.screens.calendar

/*
import android.icu.util.Calendar
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.state.TagState
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.screens.ScrollableToDoColumn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onDateChanged: (Calendar, Int, Int, Int) -> Unit,
    calendarViewModel: CalendarViewModel,
    toDoState: ToDoState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit
) {
    // Use remember to store the selected date locally
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    // Observe the LiveData to get the selected date
    LaunchedEffect(calendarViewModel.selectedDate) {
        selectedDate = calendarViewModel.selectedDate.value
    }

    // Call the provided onDateChanged callback when the selected date changes
    selectedDate?.let { date ->
        onDateChanged(Calendar.getInstance(), date.year, date.month, date.day)
    }

    val todosForSelectedDate = remember(selectedDate) {
        mutableStateOf(selectedDate?.let { date ->
            calendarViewModel.todoList.value?.filter { todo ->
                val toDoDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(todo.dueDate)
                toDoDate == date
            } ?: emptyList()
        } ?: emptyList())
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Other Compose UI elements can be added here

        // AndroidView to integrate CalendarView into Compose
        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    // Set any customization options for the CalendarView here
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val selectedCalendar = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }
                        onDateChanged(selectedCalendar, year, month, dayOfMonth)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(androidx.compose.ui.graphics.Color.White) // Set background color if needed
        )
        // Section to display todos for the selected date
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Gray) // Set background color if needed
        ) {
            ScrollableToDoColumn(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)

        }
    }

}

 */

