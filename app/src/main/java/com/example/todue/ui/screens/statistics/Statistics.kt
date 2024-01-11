package com.example.todue.ui.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todue.ui.event.ToDoEvent

@Composable
fun StatisticsScreen(
    onToDoEvent: (ToDoEvent) -> Unit
) {
    val totalAmountOfFinishedToDos = remember {mutableIntStateOf(0)}
    //totalAmountOfFinishedToDos.value = onToDoEvent(ToDoEvent.GetStatistics)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = onToDoEvent(ToDoEvent.GetStatistics).toString())
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {

        }
    }

}