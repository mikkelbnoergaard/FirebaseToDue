package com.example.todue.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.todue.ui.theme.ToDoTheme
import com.example.todue.dataLayer.ToDoDatabase
import com.example.todue.modelView.TagViewModel
import com.example.todue.modelView.ToDoViewModel
import com.example.todue.ui.screens.GeneralLayout
import com.example.todue.ui.theme.backgroundColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val db by lazy { // Lazy initialization of the Room database instance.
        Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            name = "todo.db"
        ).build()
    }
    private val toDoViewModel by viewModels<ToDoViewModel>( // ViewModels to manage the state of the to-do lists.
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ToDoViewModel(db.dao, db.tagDao) as T
                }
            }
        }
    )

    private val tagViewModel by viewModels<TagViewModel>( // ViewModels to manage the state of the tags.
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TagViewModel(db.tagDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) { // Collect the state of the to-do list and tags into composable functions.
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                val state by toDoViewModel.toDoState.collectAsState()
                val tagState by tagViewModel.tagState.collectAsState()
                val systemUiController = rememberSystemUiController() // Control the system UI bars.
                val useDarkIcons = !isSystemInDarkTheme()

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
                    color = backgroundColor
                ) {
                    GeneralLayout(toDoState = state, tagState = tagState, onToDoEvent = toDoViewModel::onEvent, onTagEvent = tagViewModel::onEvent)
                }
            }
        }
    }
}
