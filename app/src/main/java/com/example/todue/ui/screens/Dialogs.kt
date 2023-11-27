package com.example.todue.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.todue.dataLayer.source.local.Tag
import com.example.todue.ui.event.TagEvent
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.ui.modifiers.getBottomLineShape
import com.example.todue.state.ToDoState
import com.example.todue.ui.theme.buttonColor
import com.example.todue.ui.theme.textColor
import com.example.todue.ui.theme.unselectedItemColor
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun CreateToDoDialog(
    toDoState: ToDoState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    //for date and time pickers
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("HH:mm")
                .format(pickedTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onToDoEvent(ToDoEvent.HideCreateDialog)
        },
        title = { Text(text = "Create To Do") },
        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = toDoState.title,
                    onValueChange = {
                        onToDoEvent(ToDoEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = toDoState.description,
                    onValueChange = {
                        onToDoEvent(ToDoEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )
                TextField(
                    value = toDoState.tag,
                    onValueChange = {
                        onToDoEvent(ToDoEvent.SetTag(it))
                    },
                    placeholder = {
                        Text(text = "Tag")
                    }
                )

                //date button
                Button(onClick = {
                    dateDialogState.show()
                }) {
                    Text(text = "Pick date")
                }
                Text(text = formattedDate)

                //time button
                Button(onClick = {
                    timeDialogState.show()
                }) {
                    Text(text = "Pick time")
                }
                Text(text = formattedTime)

            }
        },

        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                //these variables are a quick fix for hour and minute not being saved correctly in database
                //previously, the first 0 was left out of hh and mm
                var hourZero = ""
                var minuteZero = ""
                if(pickedTime.hour < 10){
                    hourZero = "0"
                }
                if(pickedTime.minute < 10){
                    minuteZero = "0"
                }

                val dueDateString: String = pickedDate.toString()
                onToDoEvent(ToDoEvent.SetDueDate(dueDateString))

                val dueTimeString: String = hourZero + pickedTime.hour.toString() + ":" + minuteZero + pickedTime.minute.toString()
                onToDoEvent(ToDoEvent.SetDueTime(dueTimeString))

                Button(
                    onClick = {
                        onToDoEvent(ToDoEvent.CreateToDo)
                        onTagEvent(TagEvent.CreateTag(toDoState.tag))
                    },
                    modifier = Modifier
                        .padding(5.dp)) {
                    Text(text = "Create")
                }
            }
        }
    )

    MaterialDialog(
        dialogState = dateDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(text = "OK")
            negativeButton(text = "Cancel")
        }
    ) {
        this.datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
        ) {
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(text = "OK")
            negativeButton(text = "Cancel")
        }
    ) {
        this.timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a date",
            is24HourClock = true,
        ) {
            pickedTime = it
        }
    }

    //use this in database
}

@Composable
fun DeleteToDoToDoDialog(
    onToDoEvent: (ToDoEvent) -> Unit,
    //onTagEvent: (TagEvent) -> Unit,
    modifier: Modifier = Modifier,
    toDo: ToDo
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onToDoEvent(ToDoEvent.HideDeleteToDoDialog)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Do you want to delete this ToDo?\n\nThis cannot be undone.")
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        onToDoEvent(ToDoEvent.DeleteToDo(toDo = toDo))
                        //should delete associated tag if it has no other todos attached
                        onToDoEvent(ToDoEvent.HideDeleteToDoDialog)
                        onToDoEvent(ToDoEvent.HideToDoDialog)
                    },
                    modifier = Modifier
                        .padding(5.dp)
                    ) {
                    Text(text = "Delete")
                }
            }
        }
    )
}

@Composable
fun CheckToDoDialog(
    onToDoEvent: (ToDoEvent) -> Unit,
    toDo: ToDo
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onToDoEvent(ToDoEvent.HideToDoDialog)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .border(width = 3.dp, color = Color.LightGray, shape = getBottomLineShape())
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ){
                    Text(
                        text = toDo.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = textColor,
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Visible
                    )
                }
                Text(
                    text = toDo.description,
                    fontSize = 18.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                )
                Text(
                    text = "\n#" + toDo.tag,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    color = unselectedItemColor,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .padding(start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                        onClick = {
                            onToDoEvent(ToDoEvent.ShowDeleteToDoDialog)
                        }
                    ) {
                        Text(text = "Delete")
                    }
                }
                Box (
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = toDo.dueDate + "\n" + toDo.dueTime,
                        fontSize = 18.sp,
                        color = textColor,
                        textAlign = TextAlign.Center,
                    )
                }
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                        onClick = {
                            onToDoEvent(ToDoEvent.HideToDoDialog)
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    )
}

@Composable
fun DeleteTagDialog(
    onTagEvent: (TagEvent) -> Unit,
    //onToDoEvent: (ToDoEvent) -> Unit,
    modifier: Modifier = Modifier,
    tag: Tag
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onTagEvent(TagEvent.HideDeleteDialog)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Do you want to delete this tag?")
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        onTagEvent(TagEvent.DeleteTag(tag = tag))
                        //does not work yet
                        //onToDoEvent(ToDoEvent.DeleteTagFromTodos("$tag"))
                        //onToDoEvent(ToDoEvent.DeleteToDosWithGivenTag("$tag"))
                        onTagEvent(TagEvent.HideDeleteDialog)
                    },
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(text = "Delete")
                }
            }
        }
    )
}