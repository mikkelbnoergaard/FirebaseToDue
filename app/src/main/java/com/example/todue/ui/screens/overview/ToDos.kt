package com.example.todue.ui.screens.overview

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.TextField
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.modifiers.getBottomLineShape
import com.example.todue.state.TagState
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.TagEvent
//import com.example.todue.ui.screens.AccountButton
import com.example.todue.ui.screens.ScrollableTagRow
import com.example.todue.ui.screens.ScrollableToDoColumn
//import com.example.todue.ui.screens.SettingsButton
import com.example.todue.ui.theme.backgroundColor
import com.example.todue.ui.theme.barColor
import java.time.LocalDate

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.09f)
                .background(backgroundColor)
                .border(width = 3.dp, color = barColor, shape = getBottomLineShape()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //SettingsButton(onToDoEvent = onToDoEvent)
            Text(
                LocalDate.now().toString(),
                modifier = Modifier
                    .requiredWidth(120.dp)
                    .padding(start = 15.dp, end = 15.dp)
            )

            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current
            BackHandler(true){ focusManager.clearFocus() }

            TextField(
                value = toDoState.searchInToDos,
                onValueChange = {
                    onToDoEvent(ToDoEvent.SetSearchInToDos(it))
                    onTagEvent(TagEvent.SetSearchInTags(it))
                },
                placeholder = {
                    androidx.compose.material.Text(text = "search...")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(end = 5.dp)
                    .clickable(
                        onClick = {
                            focusManager.clearFocus()
                        }
                    ),
                trailingIcon = {
                    IconButton(
                    onClick = {
                        focusManager.clearFocus()
                    }) {
                    Icon(Icons.Filled.ArrowBack, "Lose focus button")
                    }
                }
            )
            //AccountButton(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
        }
        ScrollableTagRow(tagState = tagState, onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
        ScrollableToDoColumn(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
    }

}