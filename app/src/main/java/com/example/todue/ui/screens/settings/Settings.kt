package com.example.todue.ui.screens.settings

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.theme.*


@Composable
fun Settings(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScaffoldSettings(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldSettings(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
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
                        "Settings",
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
            val paddingBetweenRows = 15.dp
            val leftSpacerPadding = 30.dp
            val spaceAfterIcon = 15.dp
            val spaceAfterOption = 150.dp
            val rowHeight = 40.dp
            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.size(paddingBetweenRows))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.size(leftSpacerPadding))
                Icon(Icons.Outlined.Visibility, "Visibility icon")
                Spacer(Modifier.size(spaceAfterIcon))
                Text(text = "Dark Theme")
                Spacer(Modifier.width(spaceAfterOption))
                DarkThemeSwitch(darkThemeProvider = DarkThemeProvider())
            }
            // Switch button messes with distance between
            // the options, so this spacer is hard coded
            //Spacer(Modifier.size(18.dp))
            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.size(paddingBetweenRows))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.size(leftSpacerPadding))
                Icon(Icons.Outlined.AccountCircle, "Visibility icon")
                Spacer(Modifier.size(spaceAfterIcon))
                Text(text = "Account")
            }

            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.size(paddingBetweenRows))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.size(leftSpacerPadding))
                Icon(Icons.Outlined.Notifications, "Visibility icon")
                Spacer(Modifier.size(spaceAfterIcon))
                Text(text = "Notifications")
                Spacer(Modifier.width(spaceAfterOption))
                NotificationSwitch()

            }

            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.size(paddingBetweenRows))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.size(leftSpacerPadding))
                Icon(Icons.Outlined.Lock, "Visibility icon")
                Spacer(Modifier.size(spaceAfterIcon))
                Text(text = "Privacy and security")
            }

            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.size(paddingBetweenRows))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.size(leftSpacerPadding))
                Icon(Icons.Outlined.QuestionMark, "Visibility icon")
                Spacer(Modifier.size(spaceAfterIcon))
                Text(text = "About")
            }
            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.size(paddingBetweenRows))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight)
                    .clickable(onClick = {
                        onToDoEvent(ToDoEvent.PopulateToDoList)
                        onTagEvent(TagEvent.PopulateTags)
                    }),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Spacer(Modifier.size(leftSpacerPadding))
                Icon(Icons.Outlined.Add, "Visibility icon")
                Spacer(Modifier.size(spaceAfterIcon))
                Text(text = "Populate ToDo list")
            }
            Spacer(Modifier.size(paddingBetweenRows))
            Divider(modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally), thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Composable
fun NotificationSwitch() {
    var checked by remember { mutableStateOf(false) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        }
    )}

@Composable
fun DarkThemeSwitch(
    darkThemeProvider: DarkThemeProvider
) {
    var checked by remember { mutableStateOf(false) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        }
    )
}

/*
@Composable
fun SettingsPreview(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit
){
    Settings(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent)
}

 */