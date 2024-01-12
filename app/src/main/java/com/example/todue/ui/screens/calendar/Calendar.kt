package com.example.todue.ui.screens.calendar

import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.state.CalendarState
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.CalendarEvent
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.screens.CheckToDoDialog
import com.example.todue.ui.screens.CreateToDoDialog
import com.example.todue.ui.screens.DeleteToDoDialog
import com.example.todue.ui.screens.EditToDoDialog
import com.example.todue.ui.screens.FinishToDoDialog
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

    var selectedToDo by remember {
        mutableStateOf(
            ToDo(
                title = "",
                description = "",
                tag = "",
                dueDate = "",
                dueTime = "",
                finished = false
            ) )
    }

    val (_, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }

    if(toDoState.isDeletingToDo) { DeleteToDoDialog(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, toDo = selectedToDo, onCalendarEvent = onCalendarEvent) }

    if(toDoState.isCheckingToDo) { CheckToDoDialog(onToDoEvent = onToDoEvent, toDo = selectedToDo) }

    if(toDoState.isEditingToDo) { EditToDoDialog(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, toDo = selectedToDo, toDoState = toDoState, onCalendarEvent = onCalendarEvent) }

    if(toDoState.isFinishingToDo) { FinishToDoDialog(onToDoEvent = onToDoEvent) }

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
            ElevatedButton(
                onClick = {
                    selectedToDo = toDo
                    onToDoEvent(ToDoEvent.ShowToDoDialog)
                },
                modifier = Modifier
                    .border(border = BorderStroke(10.dp, androidx.compose.ui.graphics.Color.Transparent))
                    .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .requiredWidth(width - 40.dp)
                    .fillMaxHeight(),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp, 5.dp, 5.dp, 5.dp, 5.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
            ) {
                ToDoItem(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, onCalendarEvent = onCalendarEvent, toDo = toDo)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    toDoState: ToDoState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    calendarState: CalendarState
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        "Calendar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 30.sp
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),

            ){
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
    }
}
