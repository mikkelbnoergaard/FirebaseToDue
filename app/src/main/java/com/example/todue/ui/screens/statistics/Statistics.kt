package com.example.todue.ui.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.ToDoEvent

@Composable
fun StatisticsScreen(
    toDoState: ToDoState,
    onToDoEvent: (ToDoEvent) -> Unit
) {
    onToDoEvent(ToDoEvent.GetStatistics)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Created ToDos: " + toDoState.totalAmountOfCreatedToDos)
        Spacer(modifier = Modifier
            .height(10.dp))
        Text(text = "Finished ToDos: " + toDoState.totalAmountOfFinishedToDos)
        Spacer(modifier = Modifier
            .height(10.dp))
        Text(text = "Unfinished ToDos: " + toDoState.totalAmountOfUnfinishedToDos)
    }
}