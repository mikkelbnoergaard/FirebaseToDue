package com.example.todue.ui.event

import com.example.todue.dataLayer.source.local.ToDo

sealed interface CalendarEvent {

    data object CreateToDo: CalendarEvent
    data object ShowCreateDialog: CalendarEvent
    data object HideCreateDialog: CalendarEvent
    data object ShowDeleteToDoDialog: CalendarEvent
    data object HideDeleteToDoDialog: CalendarEvent
    data object ShowToDoDialog: CalendarEvent
    data object HideToDoDialog: CalendarEvent
    data object ShowEditToDoDialog: CalendarEvent
    data object HideEditToDoDialog: CalendarEvent
    data object ResetToDoState: CalendarEvent

    data class SetTitle(val title: String): CalendarEvent
    data class SetDescription(val description: String): CalendarEvent
    data class SetTag(val tag: String): CalendarEvent
    data class SetDueDate(val dueDate: String): CalendarEvent
    data class SetDueTime(val dueTime: String): CalendarEvent
    data class FinishToDo(val toDo: ToDo): CalendarEvent
    data class UnFinishToDo(val toDo: ToDo): CalendarEvent
    data class DeleteToDo(val toDo: ToDo): CalendarEvent
    data class DeleteTagFromToDos(val tag: String): CalendarEvent
    data class EditToDo(val newTitle: String, val newDescription: String, val newTag: String, val newDueDate: String, val newDueTime: String, val toDoId: Int): CalendarEvent
    data class SetToDoStateForEdit(val toDo: ToDo): CalendarEvent
    data class SortToDosByGivenDate(val date: String): CalendarEvent

}