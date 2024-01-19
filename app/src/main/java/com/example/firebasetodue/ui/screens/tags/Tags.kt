package com.example.firebasetodue.ui.screens.tags

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
import com.example.firebasetodue.ui.event.TagEvent
import com.example.firebasetodue.state.TagState
import com.example.firebasetodue.state.ToDoState
import com.example.firebasetodue.ui.event.CalendarEvent
import com.example.firebasetodue.ui.event.ToDoEvent
import com.example.firebasetodue.ui.screens.TagList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    toDoState: ToDoState
) {
    Column(
        modifier = Modifier
        .fillMaxSize()
    ){
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            title = {
                Text(
                    "Tags",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
        )
        Tags(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, onCalendarEvent = onCalendarEvent, tagState = tagState, toDoState = toDoState)
    }


}

@Composable
fun Tags(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    tagState: TagState,
    toDoState: ToDoState
    ) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),

        ) {
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
                TagList(tagState, onTagEvent, onToDoEvent, onCalendarEvent, toDoState)
            }
        }
    }
}