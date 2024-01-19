package com.example.firebasetodue.ui.screens.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.firebasetodue.ui.event.ToDoEvent
import com.example.firebasetodue.state.TagState
import com.example.firebasetodue.state.ToDoState
import com.example.firebasetodue.ui.event.CalendarEvent
import com.example.firebasetodue.ui.event.TagEvent
import com.example.firebasetodue.ui.screens.TopBar
import com.example.firebasetodue.ui.screens.ScrollableTagRow
import com.example.firebasetodue.ui.screens.ScrollableToDoColumn

@Composable
fun ToDosScreen(
    toDoState: ToDoState,
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit
) {
ToDos(toDoState = toDoState, tagState = tagState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent, onCalendarEvent = onCalendarEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDos(
    toDoState: ToDoState,
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit
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
                    "Overview",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    TopBar(
                        toDoState = toDoState,
                        onTagEvent = onTagEvent,
                        onToDoEvent = onToDoEvent
                    )
                    ScrollableTagRow(
                        tagState = tagState,
                        onToDoEvent = onToDoEvent,
                        onTagEvent = onTagEvent
                    )
                    ScrollableToDoColumn(
                        toDoState = toDoState,
                        onTagEvent = onTagEvent,
                        onToDoEvent = onToDoEvent,
                        onCalendarEvent = onCalendarEvent
                    )
                }
            }
        }
    }
}