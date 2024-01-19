@file:Suppress("UNCHECKED_CAST")

package com.example.firebasetodue.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebasetodue.ui.theme.ToDoTheme
import com.example.firebasetodue.di.DatabaseModules
import com.example.firebasetodue.ui.stateholder.TagsViewModel
import com.example.firebasetodue.ui.stateholder.ToDosViewModel
import com.example.firebasetodue.ui.stateholder.CalendarViewModel
import com.example.firebasetodue.ui.screens.GeneralLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.firebasetodue.dataLayer.source.local.TagRepository
import com.example.firebasetodue.dataLayer.source.local.ToDoRepository
import com.example.firebasetodue.dataLayer.source.remote.database.FirebaseRepository
import com.example.firebasetodue.ui.event.TagEvent
//import com.example.todue.ui.theme.DarkThemeProvider

class MainActivity : ComponentActivity() {

    private val toDosViewModel by viewModels<ToDosViewModel>( // ViewModels to manage the state of the to-do lists.
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ToDosViewModel(
                        ToDoRepository(DatabaseModules.provideToDoDao(DatabaseModules.provideDataBase(applicationContext)))
                    ) as T
                }
            }
        }
    )

    private val tagsViewModel by viewModels<TagsViewModel>( // ViewModels to manage the state of the tags.
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TagsViewModel(
                        TagRepository(DatabaseModules.provideTagDao(DatabaseModules.provideDataBase(applicationContext)))
                    ) as T
                }
            }
        }
    )

    private val calendarViewModel by viewModels<CalendarViewModel>( // ViewModels to manage the state of the tags.
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CalendarViewModel(
                        ToDoRepository(DatabaseModules.provideToDoDao(DatabaseModules.provideDataBase(applicationContext)))
                    ) as T
                }
            }
        }
    )

    private val firebaseRepository = FirebaseRepository(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) { // Collect the state of the to-do list and tags into composable functions.
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme(useDarkTheme = isSystemInDarkTheme()) {
                val toDoState by toDosViewModel.toDoState.collectAsState()
                val tagState by tagsViewModel.tagState.collectAsState()
                val systemUiController = rememberSystemUiController() // Control the system UI bars.
                val useDarkIcons = !isSystemInDarkTheme()
                val onTagEvent = tagsViewModel::onEvent
                val onToDoEvent = toDosViewModel::onEvent
                val onCalendarEvent = calendarViewModel::onEvent
                val calendarState by calendarViewModel.calendarState.collectAsState()

                onTagEvent(TagEvent.ResetTagSort)

                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
                systemUiController.setNavigationBarColor(
                    color = Color.Black
                )

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GeneralLayout(
                        toDoState = toDoState,
                        tagState = tagState,
                        onToDoEvent = onToDoEvent,
                        onTagEvent = onTagEvent,
                        onCalendarEvent = onCalendarEvent,
                        calendarState = calendarState,
                        firebaseRepository
                    )
                }
            }
        }
    }
}
