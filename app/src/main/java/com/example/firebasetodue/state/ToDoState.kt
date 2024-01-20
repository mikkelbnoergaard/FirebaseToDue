package com.example.firebasetodue.state

import com.example.firebasetodue.dataLayer.source.local.Tag
import com.example.firebasetodue.ui.sortType.ToDoSortType
import com.example.firebasetodue.dataLayer.source.local.ToDo

data class ToDoState(

    val toDos: List<ToDo> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val title: String = "",
    val description: String = "",
    val tag: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val finished: Boolean = false,
    val isCreatingToDo: Boolean = false,
    val isDeletingToDo: Boolean = false,
    val isCheckingToDo: Boolean = false,
    val isEditingToDo: Boolean = false,
    val isFinishingToDo: Boolean = false,
    val toDoSortType: ToDoSortType = ToDoSortType.DUE_DATE,
    val searchInToDos: String = "",
    val sortByFinished: Boolean = false,

    val existsInDatabase: Boolean = false,

    //for statistics
    val totalAmountOfFinishedToDos: Int = 0,
    val totalAmountOfCreatedToDos: Int = 0,
    val totalAmountOfUnfinishedToDos: Int = 0

)