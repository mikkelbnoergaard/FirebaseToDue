package com.example.todue.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Equalizer
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.elevatedButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todue.dataLayer.source.local.Tag
import com.example.todue.ui.event.TagEvent
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.navigation.TabItem
import com.example.todue.ui.modifiers.getBottomLineShape
import com.example.todue.state.TagState
import com.example.todue.state.ToDoState
import com.example.todue.ui.screens.calendar.CalendarScreen
import com.example.todue.ui.screens.overview.OverviewScreen
import com.example.todue.ui.screens.settings.Settings
import com.example.todue.ui.screens.statistics.StatisticsScreen
import com.example.todue.ui.screens.tags.TagsScreen
import com.example.todue.ui.theme.barColor
import com.example.todue.ui.theme.itemColor
import com.example.todue.ui.theme.textColor
import com.example.todue.ui.theme.backgroundColor
import com.example.todue.ui.theme.buttonColor
import com.example.todue.ui.theme.selectedItemColor
import com.example.todue.ui.theme.unselectedItemColor

//The general layout used on all the screens with navigation bar
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GeneralLayout(
    toDoState: ToDoState,
    tagState: TagState,
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit
){

    val tabItems = listOf(
        TabItem(
            title = "Overview",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Outlined.Home
        ),
        TabItem(
            title = "Tags",
            unselectedIcon = Icons.Outlined.Tag,
            selectedIcon = Icons.Outlined.Tag
        ),
        TabItem(
            title = "Calendar",
            unselectedIcon = Icons.Outlined.CalendarMonth,
            selectedIcon = Icons.Outlined.CalendarMonth
        ),
        TabItem(
            title = "Statistics",
            unselectedIcon = Icons.Outlined.Equalizer,
            selectedIcon = Icons.Outlined.Equalizer,
        ),
        TabItem(
            title = "Settings",
            unselectedIcon = Icons.Outlined.Settings,
            selectedIcon = Icons.Outlined.Settings
        )
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    //checks if a scroll is in progress, used to avoid trouble when scrolling
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {index ->
            when(index){
                0 -> OverviewScreen(toDoState = toDoState, tagState = tagState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
                1 -> TagsScreen(tagState = tagState, onTagEvent = onTagEvent)
                2 -> CalendarScreen()
                3 -> StatisticsScreen()
                4 -> Settings()
                else -> OverviewScreen(toDoState = toDoState, tagState = tagState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
            }
        }
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = barColor,

            //indicator to avoid ugly line under selected tab
            indicator = {
                Color.Transparent
            }
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier,
                    unselectedContentColor = unselectedItemColor,
                    selectedContentColor = selectedItemColor,
                    icon = {
                        Icon(
                            modifier = Modifier,
                            imageVector = if(index == selectedTabIndex){
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }

}

//Account button, probably going to be deleted
@Composable
fun AccountButton(
    onToDoEvent: (ToDoEvent) -> Unit
) {

    FloatingActionButton(
        //should not sort by due date, but it's for testing
        onClick = { onToDoEvent(ToDoEvent.SortToDosByDueDate) },
        containerColor = buttonColor,
        contentColor = selectedItemColor,
        modifier = Modifier
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
            .requiredSize(50.dp)
    ) {
        Icon(Icons.Filled.AccountCircle, "Floating account button")
    }

}

//Settings button, probably going to be deleted
@Composable
fun SettingsButton(
    onToDoEvent: (ToDoEvent) -> Unit
) {

    FloatingActionButton(
        //should not sort by finished, but it's for testing
        onClick = { onToDoEvent(ToDoEvent.SortToDosByFinished) },
        containerColor = buttonColor,
        contentColor = selectedItemColor,
        modifier = Modifier
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
            .requiredSize(50.dp)
    ) {
        Icon(Icons.Filled.Settings, "Floating settings button")
    }

}



@Composable
fun TagList(
    tagState: TagState,
    onTagEvent: (TagEvent) -> Unit
) {

    var selectedTag by remember {
        mutableStateOf(
            Tag(
                title = "",
                toDoAmount = 0,
                sort = false
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(tagState.tags) { tag ->

            if (tagState.isDeletingTag) { DeleteTagDialog(onTagEvent = onTagEvent, tag = selectedTag) }

            ElevatedButton(
                onClick = {
                    selectedTag = tag
                    onTagEvent(TagEvent.ShowDeleteDialog)
                },
                modifier = Modifier
                    .border(border = BorderStroke(10.dp, Color.Transparent))
                    .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                elevation = elevatedButtonElevation(5.dp, 5.dp, 5.dp, 5.dp, 5.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {

                Text(
                    text = "#" + tag.title,
                    color = textColor
                )
            }
        }
    }

}

//List of to-do composables, used in the scrollable to-do column
@Composable
fun ToDoList(
    toDoState: ToDoState,
    onToDoEvent: (ToDoEvent) -> Unit
) {

    var selectedToDo by remember {
        mutableStateOf(
            ToDo(
                title = "",
                description = "",
                tag = "",
                dueDate = "",
                dueTime = "",
                finished = false
            ) )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(toDoState.toDos) { toDo ->
            val (_, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }

            if (toDoState.isDeletingToDo) { DeleteToDoToDoDialog(onToDoEvent = onToDoEvent, toDo = selectedToDo) }

            if(toDoState.isCheckingToDo){ CheckToDoDialog(onToDoEvent = onToDoEvent, toDo = selectedToDo) }

            ElevatedButton(
                onClick = {
                    selectedToDo = toDo
                    onToDoEvent(ToDoEvent.ShowToDoDialog)
                },
                modifier = Modifier
                    .border(border = BorderStroke(10.dp, Color.Transparent))
                    .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .requiredWidth(width - 40.dp)
                    .fillMaxHeight(),
                elevation = elevatedButtonElevation(5.dp, 5.dp, 5.dp, 5.dp, 5.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(itemColor),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .requiredHeight(100.dp)
                            .weight(0.7f)
                    ) {
                        Text(
                            text = toDo.title,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 25.sp,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = toDo.description,
                            fontSize = 18.sp,
                            color = textColor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "#" + toDo.tag,
                            fontStyle = FontStyle.Italic,
                            fontSize = 15.sp,
                            color = unselectedItemColor,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .size(10.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .requiredHeight(100.dp)
                    ) {
                        Text(
                            text = toDo.dueDate + "\n" + toDo.dueTime,
                            fontSize = 15.sp,
                            color = textColor,
                            textAlign = TextAlign.End
                        )
                        FloatingActionButton(
                            onClick = {
                                selectedToDo = toDo
                                if(toDo.finished){
                                    onToDoEvent(ToDoEvent.UnFinishToDo(toDo = toDo))
                                } else{
                                    onToDoEvent(ToDoEvent.FinishToDo(toDo = toDo))
                                }
                            },
                            modifier = Modifier
                                .requiredSize(30.dp),
                            containerColor = buttonColor,
                            contentColor = selectedItemColor,
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Icon(Icons.Filled.Check, "Finish to do button",
                                modifier = Modifier
                                    .fillMaxSize(0.8f)
                            )
                        }
                    }
                }
            }
        }
    }

}

//Composable for the plus button at the bottom of the screen
//Is a row in a box to help with aligning it in the bottom right of the screen
@Composable
fun PlusButtonRow(
    onToDoEvent: (ToDoEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.End,
        ) {
            FloatingActionButton(
                onClick = {
                    onToDoEvent(ToDoEvent.ShowCreateDialog)
                },
                containerColor = buttonColor,
                contentColor = selectedItemColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .requiredSize(50.dp)
            ) {
                Icon(Icons.Filled.Add, "Floating action button")
            }
        }
    }

}

//Scrollable column of ToDos
@Composable
fun ScrollableToDoColumn(
    toDoState: ToDoState,
    onTagEvent: (TagEvent) -> Unit,
    onToDoEvent: (ToDoEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        if(toDoState.isCreatingToDo){
            CreateToDoDialog(toDoState = toDoState, onTagEvent = onTagEvent, onToDoEvent = onToDoEvent)
        }
        ToDoList(toDoState, onToDoEvent)
        PlusButtonRow(onToDoEvent)
    }

}

//Row of scrollable tags
@Composable
fun ScrollableTagRow(
    tagState: TagState,
    onToDoEvent: (ToDoEvent) -> Unit,
    onTagEvent: (TagEvent) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .requiredHeight(50.dp)
            .fillMaxWidth()
            .border(width = 3.dp, color = barColor, shape = getBottomLineShape())
            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)

    ) {
        items(tagState.tags) { tag ->

            val buttonColor = remember { mutableStateOf(backgroundColor) }
            val selected = remember{ mutableStateOf(false) }

            OutlinedButton(
                onClick = {
                    if(selected.value) {
                        selected.value = !selected.value
                        buttonColor.value = backgroundColor
                        onToDoEvent(ToDoEvent.RemoveTagToSortToDos(tag.title))
                        onTagEvent(TagEvent.DontSortByThisTag(tag))
                    } else {
                        selected.value = true
                        buttonColor.value = itemColor
                        onToDoEvent(ToDoEvent.AddTagToSortToDos(tag.title))
                        onTagEvent(TagEvent.SortByThisTag(tag))
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp),
                colors = ButtonDefaults.buttonColors(buttonColor.value)
            ) {
                Text(
                    text = "#" + tag.title,
                    color = textColor
                )
            }
        }
    }

}