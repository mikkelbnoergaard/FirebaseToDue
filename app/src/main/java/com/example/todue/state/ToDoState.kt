package com.example.todue.state

import com.example.todue.dataLayer.local.Tag
import com.example.todue.ui.sortType.ToDoSortType
import com.example.todue.dataLayer.local.ToDo

data class ToDoState(
    val toDos: List<ToDo> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val title: String = "",
    val description: String = "",
    val tag: String = "",
    val dueDate: String = "",
    val finished: Boolean = false,
    val isCreatingToDo: Boolean = false,
    val isDeletingToDo: Boolean = false,
    val isCheckingToDo: Boolean = false,
    var toDoSortType: ToDoSortType = ToDoSortType.TITLE
)
