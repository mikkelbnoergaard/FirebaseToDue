package com.example.todue.ui.screens.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todue.ui.event.TagEvent
import com.example.todue.state.TagState
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.screens.TagList

@Composable
fun TagsScreen(
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Tags")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            TagList(tagState, onTagEvent, onToDoEvent)
        }
    }

}