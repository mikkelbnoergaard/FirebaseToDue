package com.example.todue.ui.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todue.state.ToDoState
import com.example.todue.ui.event.ToDoEvent

val defaultMaxHeight = 600.dp

@Composable
fun StatisticsScreen(
    toDoState: ToDoState,
    onToDoEvent: (ToDoEvent) -> Unit
) {
    val barChartValues = listOf(
        toDoState.totalAmountOfCreatedToDos,
        toDoState.totalAmountOfFinishedToDos,
        toDoState.totalAmountOfUnfinishedToDos
    )
    val barChartLabelsUpper = listOf(
        "Created",
        "Finished",
        "Unfinished"
    )
    val barCharLabelsLower = "Todos"

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        onToDoEvent(ToDoEvent.GetStatistics)
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .height(20.dp)
        )
        BarChart(values = barChartValues)
        BarChartValue(modifier = Modifier, values = barChartValues)
        BarChartLabel(modifier = Modifier, labelsUpper = barChartLabelsUpper, labelLower = barCharLabelsLower)

    }

    /*
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        "Statistics",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 30.sp
                    )
                },
            )
        }
    ) { innerPadding ->

    }

     */
}

@Composable
fun BarChartLabel(modifier: Modifier = Modifier,
                  labelsUpper: List<String>,
                  labelLower: String

) {
    Row(
        modifier = modifier.then(
            modifier.fillMaxWidth()
        ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
    ) {
        labelsUpper.forEach { item ->
            Label(
                labelUpper = item,
                labelLower = labelLower
            )
        }
    }
}

@Composable
fun BarChartValue(
    modifier: Modifier = Modifier,
    values: List<Int>,
) {

    Row(
        modifier = modifier.then(
            modifier.fillMaxWidth()
        ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEach { item ->
            Value(
                value = item
            )
        }
    }
}

@Composable
internal fun BarChart(
    modifier: Modifier = Modifier,
    values: List<Int>,
    maxHeight: Dp = defaultMaxHeight,
    ) {

    assert(values.isNotEmpty()) { "Input values are empty" }

    val borderColor = MaterialTheme.colorScheme.primary
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }

    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(maxHeight)
                .drawBehind {
                    // draw X-Axis
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                    /*
                    // draw Y-Axis
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth
                    )

                     */
                }
        ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEach { item ->
            Bar(
                value = item,
                color = MaterialTheme.colorScheme.primary,
                maxHeight = maxHeight
            )
        }
    }

}

@Composable
private fun RowScope.Bar(
    value: Int,
    color: Color,
    maxHeight: Dp,
    ) {

    val itemHeight = remember(value) { value * maxHeight.value / 100 }
        Spacer(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .height(itemHeight.dp)
                .weight(1f)
                .background(color)
        )
}

@Composable
private fun RowScope.Label(
    labelUpper: String,
    labelLower: String
) {
    Column (
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .weight(1f)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            labelUpper,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            labelLower,
            color = MaterialTheme.colorScheme.onBackground
            )
    }
}
@Composable
private fun RowScope.Value(
    value: Int,
) {
    Column (
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .weight(1f)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value.toString(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}



