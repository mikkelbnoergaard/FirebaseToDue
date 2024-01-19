package com.example.firebasetodue.ui.screens.settings

import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.example.firebasetodue.ui.event.CalendarEvent
import com.example.firebasetodue.ui.event.TagEvent
import com.example.firebasetodue.ui.event.ToDoEvent
import com.example.firebasetodue.ui.theme.*
import com.example.firebasetodue.dataLayer.source.remote.database.*
import com.example.firebasetodue.state.ToDoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    firebaseRepository: FirebaseRepository,
    toDoState: ToDoState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            title = {
                Text(
                    "Settings",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
        )
        Settings(onToDoEvent = onToDoEvent, onTagEvent = onTagEvent, onCalendarEvent = onCalendarEvent, firebaseRepository = firebaseRepository, toDoState = toDoState)
    }
}

@Composable
fun Settings(
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit,
    onCalendarEvent: (CalendarEvent) -> Unit,
    firebaseRepository: FirebaseRepository,
    toDoState: ToDoState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val paddingBetweenRows = 15.dp
        val sidePadding = 30.dp
        val spaceAfterIcon = 15.dp
        val rowHeight = 40.dp
        Spacer(Modifier.size(paddingBetweenRows))
        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .padding(start = sidePadding, end = sidePadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Visibility, "Visibility icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(spaceAfterIcon))
                Text(
                    text = "Dark Theme - TBA",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            DarkThemeSwitch()
        }

        Spacer(Modifier.size(paddingBetweenRows))
        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(sidePadding))
            Icon(
                Icons.Outlined.AccountCircle, "Visibility icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(spaceAfterIcon))
            Text(
                text = "Account - TBA",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.size(paddingBetweenRows))
        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .padding(start = sidePadding, end = sidePadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Spacer(Modifier.size(sidePadding))
                Icon(
                    Icons.Outlined.Notifications, "Visibility icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(spaceAfterIcon))
                Text(
                    text = "Notifications - TBA",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            NotificationSwitch()

        }

        Spacer(Modifier.size(paddingBetweenRows))
        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(sidePadding))
            Icon(
                Icons.Outlined.Lock, "Visibility icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(spaceAfterIcon))
            Text(
                text = "Privacy and Security - TBA",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.size(paddingBetweenRows))
        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(sidePadding))
            Icon(
                Icons.Outlined.QuestionMark, "Visibility icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(spaceAfterIcon))
            Text(
                text = "About - TBA",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.size(paddingBetweenRows))
        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))

        //this row is not meant to be included on release, it's purely for testing and showing the application
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .clickable(onClick = {
                    onTagEvent(TagEvent.PopulateTags)
                    onToDoEvent(ToDoEvent.PopulateToDoList)
                    onCalendarEvent(CalendarEvent.Recompose)
                }),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.size(sidePadding))
            Icon(
                Icons.Outlined.Add, "Visibility icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(spaceAfterIcon))
            Text(
                text = "Populate ToDo list - Experimental",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.size(paddingBetweenRows))

        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .padding(start = sidePadding, end = sidePadding)
                .clickable(onClick = {
                    for(item in toDoState.toDos){
                        firebaseRepository.firebaseSaveToDo(item)
                    }
                }),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Spacer(Modifier.size(sidePadding))
                Icon(
                    Icons.Outlined.Notifications, "Visibility icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(spaceAfterIcon))
                Text(
                    text = "Sync from Room to Firebase",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }


        Spacer(Modifier.size(paddingBetweenRows))

        Divider(
            modifier = Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(paddingBetweenRows))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .padding(start = sidePadding, end = sidePadding)
                .clickable(onClick = {
                    for(item in firebaseRepository.getToDoListInFirebase()){
                        firebaseRepository.clearToDoList()
                        if(onToDoEvent(ToDoEvent.CheckIfToDoExists(item)) != false) {

                        }
                        onToDoEvent(ToDoEvent.EditToDo(item.title, item.description, item.tag, item.dueDate, item.dueTime, item.id))
                    }
                }),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Spacer(Modifier.size(sidePadding))
                Icon(
                    Icons.Outlined.Notifications, "Visibility icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(spaceAfterIcon))
                Text(
                    text = "Sync from Firebase to Room",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
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
        },
        colors = SwitchDefaults.colors(
            uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
            uncheckedThumbColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun DarkThemeSwitch() {
    var checked by remember { mutableStateOf(false) }


    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors(
            uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
            uncheckedThumbColor = MaterialTheme.colorScheme.primary
        )
    )
}