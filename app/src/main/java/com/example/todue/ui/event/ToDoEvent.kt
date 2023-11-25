package com.example.todue.ui.event

import com.example.todue.dataLayer.source.local.ToDo

sealed interface ToDoEvent {
    data object CreateToDo: ToDoEvent

    data object CreateTag: ToDoEvent

    data class SetTitle(val title: String): ToDoEvent
    data class SetDescription(val description: String): ToDoEvent
    data class SetTag(val tag: String): ToDoEvent
    data class SetDueDate(val dueDate: String): ToDoEvent
    data class SetFinished(val finished: Boolean): ToDoEvent

    data object ShowCreateDialog: ToDoEvent
    data object HideCreateDialog: ToDoEvent
    data object ShowFinishDialog: ToDoEvent
    data object HideFinishDialog: ToDoEvent
    data object ShowToDoDialog: ToDoEvent
    data object HideToDoDialog: ToDoEvent

    data class FinishToDo(val toDo: ToDo): ToDoEvent

    data class DeleteToDo(val toDo: ToDo): ToDoEvent

    data class AddTagToSortToDos(val tag: String): ToDoEvent

    data object SortToDosByFinished: ToDoEvent

    data object SortToDosByDueDate: ToDoEvent

    data class RemoveTagToSortToDos(val tag: String): ToDoEvent

    data class DeleteToDosWithGivenTag(val tag: String): ToDoEvent


}