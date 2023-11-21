package com.example.todue.ui.event

import com.example.todue.ui.sortType.ToDoSortType
import com.example.todue.dataLayer.source.local.ToDo

sealed interface ToDoEvent {
    object CreateToDo: ToDoEvent

    object CreateTag: ToDoEvent

    data class SetTitle(val title: String): ToDoEvent
    data class SetDescription(val description: String): ToDoEvent
    data class SetTag(val tag: String): ToDoEvent
    data class SetDueDate(val dueDate: String): ToDoEvent
    data class SetFinished(val finished: Boolean): ToDoEvent

    object ShowCreateDialog: ToDoEvent
    object HideCreateDialog: ToDoEvent
    object ShowDeleteDialog: ToDoEvent
    object HideDeleteDialog: ToDoEvent
    object ShowToDoDialog: ToDoEvent
    object HideToDoDialog: ToDoEvent

    data class FinishToDo(val toDo: ToDo): ToDoEvent

    data class DeleteToDo(val toDo: ToDo): ToDoEvent

    data class SortToDos(val toDoSortType: ToDoSortType, val tag: String): ToDoEvent

    data class DeleteToDosWithGivenTag(val tag: String): ToDoEvent


}