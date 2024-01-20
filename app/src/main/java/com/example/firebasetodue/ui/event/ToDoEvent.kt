package com.example.firebasetodue.ui.event

import com.example.firebasetodue.dataLayer.source.local.ToDo

sealed interface ToDoEvent {

    data object CreateToDo: ToDoEvent
    data object ShowCreateDialog: ToDoEvent
    data object HideCreateDialog: ToDoEvent
    data object ShowDeleteToDoDialog: ToDoEvent
    data object HideDeleteToDoDialog: ToDoEvent
    data object ShowToDoDialog: ToDoEvent
    data object HideToDoDialog: ToDoEvent
    data object ShowEditToDoDialog: ToDoEvent
    data object HideEditToDoDialog: ToDoEvent
    data object SortToDosByDueDate: ToDoEvent
    data object ResetToDoState: ToDoEvent

    //for statistics
    data object GetStatistics: ToDoEvent


    //used to avoid manually creating a bunch of todos when testing
    data object PopulateToDoList: ToDoEvent


    data class SortToDosByFinished(val finished: Boolean): ToDoEvent
    data class SetTitle(val title: String): ToDoEvent
    data class SetDescription(val description: String): ToDoEvent
    data class SetTag(val tag: String): ToDoEvent
    data class SetDueDate(val dueDate: String): ToDoEvent
    data class SetDueTime(val dueTime: String): ToDoEvent
    data class FinishToDo(val toDo: ToDo): ToDoEvent
    data class UnFinishToDo(val toDo: ToDo): ToDoEvent
    data class DeleteToDo(val toDo: ToDo): ToDoEvent
    data class AddTagToSortToDos(val tag: String): ToDoEvent
    data class RemoveTagToSortToDos(val tag: String): ToDoEvent
    data class DeleteTagFromToDos(val tag: String): ToDoEvent
    data class EditToDo(val newTitle: String, val newDescription: String, val newTag: String, val newDueDate: String, val newDueTime: String, val toDoId: Int): ToDoEvent
    data class SetToDoStateForEdit(val toDo: ToDo): ToDoEvent
    data class SetSearchInToDos(val searchInToDos: String): ToDoEvent
    data class CreateToDoFromFirebase(val id: Int, val title: String, val description: String, val tag: String, val dueDate: String, val dueTime: String, val finished: Boolean): ToDoEvent
    data class SetUserKey(val userKey: String): ToDoEvent

}