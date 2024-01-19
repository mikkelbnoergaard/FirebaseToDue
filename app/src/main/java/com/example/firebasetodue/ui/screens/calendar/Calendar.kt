package com.example.firebasetodue.ui.screens.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.example.firebasetodue.state.CalendarState
import com.example.firebasetodue.state.ToDoState
import com.example.firebasetodue.ui.event.CalendarEvent
import com.example.firebasetodue.ui.event.TagEvent
import com.example.firebasetodue.ui.event.ToDoEvent
import com.example.firebasetodue.ui.screens.CheckToDoDialog
import com.example.firebasetodue.ui.screens.CreateToDoDialog
import com.example.firebasetodue.ui.screens.DeleteToDoDialog
import com.example.firebasetodue.ui.screens.EditToDoDialog
import com.example.firebasetodue.ui.screens.FinishToDoDialog
import com.example.firebasetodue.ui.screens.PlusButtonRow
import com.example.firebasetodue.ui.screens.ToDoItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RestrictedApi")
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

    if(toDoState.isDeletingToDo) { DeleteToDoDialog(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, onCalendarEvent = onCalendarEvent, toDo = selectedToDo) }

    if(toDoState.isCheckingToDo) { CheckToDoDialog(onToDoEvent = onToDoEvent, toDo = selectedToDo) }

    if(toDoState.isEditingToDo) { EditToDoDialog(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, toDo = selectedToDo, toDoState = toDoState, onCalendarEvent = onCalendarEvent) }

    if(toDoState.isFinishingToDo) { FinishToDoDialog(onToDoEvent = onToDoEvent) }

    var selectedDate by remember { mutableStateOf(calendarState.givenDate) }

    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDateTime.now().toMillis(),
    )
    //check if user is entering date as string; without if statement, app crashes when we
    //try to convert to our date format in database (substring(0, 10)
    if(dateState.selectedDateMillis?.let { Instant.ofEpochMilli(it) }.toString().length == 20) {
        selectedDate = dateState.selectedDateMillis?.let { Instant.ofEpochMilli(it) }.toString()
            .substring(0, 10)
    }
    onCalendarEvent(CalendarEvent.SortToDosByGivenDate(selectedDate))

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        //date picker item
        item{
            Card(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .requiredWidth(width),
                elevation = CardDefaults.elevatedCardElevation(5.dp, 5.dp, 5.dp, 5.dp, 5.dp),
                shape = RoundedCornerShape(4)
            ) {
                DatePicker(
                    state = dateState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onTertiary),
                    colors = DatePickerDefaults.colors(
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        headlineContentColor = MaterialTheme.colorScheme.onBackground,
                        weekdayContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }

        items(calendarState.toDos) { toDo ->
            ElevatedButton(
                onClick = {
                    selectedToDo = toDo
                    onToDoEvent(ToDoEvent.ShowToDoDialog)
                },
                modifier = Modifier
                    .border(border = BorderStroke(10.dp, Color.Transparent))
                    .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .requiredWidth(width - 40.dp)
                    .fillMaxHeight(),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp, 5.dp, 5.dp, 5.dp, 5.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseOnSurface),
            ) {
                ToDoItem(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, toDo = toDo, onCalendarEvent = onCalendarEvent)
            }
        }
        item{
            Spacer(
                modifier = Modifier
                    .height(80.dp)
            )
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            title = {
                Text(
                    "Calendar",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 5.dp, bottom = 5.dp)
        ) {
            if (toDoState.isCreatingToDo) {
                CreateToDoDialog(
                    toDoState = toDoState,
                    onTagEvent = onTagEvent,
                    onToDoEvent = onToDoEvent,
                    onCalendarEvent = onCalendarEvent
                )
                println(calendarState.givenDate)
            }
            CalendarToDoList(toDoState, onToDoEvent, onTagEvent, onCalendarEvent, calendarState)
            PlusButtonRow(onToDoEvent)
        }
    }
}

//function to convert current time to epoch milliseconds, used in date picker in calendar view
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()