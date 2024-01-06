package com.example.todue.ui.screens.calendar

import android.widget.CalendarView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.screens.ScrollableToDoColumn
import java.time.LocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    toDoState: ToDoState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now().toString())}
    var monthZero = ""
    var dayZero = ""

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AndroidView(factory = { CalendarView(it) }, update = {
            it.setOnDateChangeListener {_, year, month, day ->
                if(day < 10){
                    dayZero = "0"
                }
                if(month < 10){
                    monthZero = "0"
                }
                selectedDate = "$year-$monthZero${month + 1}-$dayZero$day"
                onToDoEvent(ToDoEvent.SortToDosByGivenDate(selectedDate))
            }
        } )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ScrollableToDoColumn(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
            onToDoEvent(ToDoEvent.SortToDosByGivenDate(selectedDate))

        }
    }
}


