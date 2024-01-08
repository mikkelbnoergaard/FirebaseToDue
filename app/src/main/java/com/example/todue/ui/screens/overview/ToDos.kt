package com.example.todue.ui.screens.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.state.TagState
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.CalendarEvent
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.screens.TopBar
import com.example.todue.ui.screens.AccountButton
import com.example.todue.ui.screens.FilterButton
import com.example.todue.ui.screens.ScrollableTagRow
import com.example.todue.ui.screens.ScrollableToDoColumn

@Composable
fun ToDosScreen(
    toDoState: ToDoState,
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.09f)
                .background(backgroundColor)
                .border(width = 3.dp, color = barColor, shape = getBottomLineShape()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //SettingsButton(onToDoEvent = onToDoEvent)
            Text("Overview")
            // AccountButton(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
        }
        TopBar(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
        ScrollableTagRow(tagState = tagState, onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
        ScrollableToDoColumn(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent, onCalendarEvent = onCalendarEvent)
        FilterButton(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
        ScrollableToDoColumn(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)

    }

}