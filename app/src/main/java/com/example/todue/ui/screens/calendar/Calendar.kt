package com.example.todue.ui.screens.calendar

import android.widget.CalendarView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.todue.state.CalendarState
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.CalendarEvent
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.screens.CreateToDoDialog
import com.example.todue.ui.screens.PlusButtonRow
import com.example.todue.ui.screens.ToDoItem

@Composable
fun CalendarToDoList(
    toDoState: ToDoState,
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    calendarState: CalendarState
) {

    var selectedDate by remember { mutableStateOf(calendarState.givenDate) }
    var monthZero = ""
    var dayZero = ""

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        item{
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AndroidView(factory = { CalendarView(it) } , update = {
                    it.setOnDateChangeListener { _, year, month, day ->
                        if (day < 10) {
                            dayZero = "0"
                        }
                        if (month < 10) {
                            monthZero = "0"
                        }
                        selectedDate = "$year-$monthZero${month + 1}-$dayZero$day"
                        onCalendarEvent(CalendarEvent.SortToDosByGivenDate(selectedDate))
                    }
                })
            }
        }

        items(calendarState.toDos) { toDo ->
            ToDoItem(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, toDoState = toDoState, onCalendarEvent = onCalendarEvent, toDo = toDo)
        }
    }
}

@Composable
fun CalendarScreen(
    toDoState: ToDoState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    calendarState: CalendarState
) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        if(toDoState.isCreatingToDo){
            CreateToDoDialog(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent, onCalendarEvent = onCalendarEvent)
            println(calendarState.givenDate)
        }
        CalendarToDoList(toDoState, onToDoEvent, onTagEvent, onCalendarEvent, calendarState)
        PlusButtonRow(onToDoEvent)
    }

}

