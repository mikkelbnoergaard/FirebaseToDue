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
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.screens.TopBar
import com.example.todue.ui.screens.ScrollableTagRow
import com.example.todue.ui.screens.ScrollableToDoColumn

@Composable
fun ToDosScreen(
    toDoState: ToDoState,
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        TopBar(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
        ScrollableTagRow(tagState = tagState, onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
        ScrollableToDoColumn(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent, withCalendar = false)
    }

}