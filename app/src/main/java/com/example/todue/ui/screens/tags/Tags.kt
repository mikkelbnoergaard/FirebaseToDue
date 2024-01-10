package com.example.todue.ui.screens.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todue.ui.event.TagEvent
import com.example.todue.state.TagState
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.screens.TagList
import com.example.todue.ui.screens.settings.SwitchButton
import com.example.todue.ui.theme.backgroundColor
import com.example.todue.ui.theme.barColor
import com.example.todue.ui.theme.textColor

@Composable
fun TagsScreen(
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit
) {
    ScaffoldTags(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, tagState = tagState)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTags(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
    tagState: TagState,
    ) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = textColor,
                ),
                title = {
                    Text(
                        "Tags",
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    TagList(tagState, onTagEvent, onToDoEvent)
                }
            }


        }
    }
}