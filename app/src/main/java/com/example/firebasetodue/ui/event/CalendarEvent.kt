package com.example.firebasetodue.ui.event

sealed interface CalendarEvent {

    data class SortToDosByGivenDate(val date: String): CalendarEvent
    data object Recompose: CalendarEvent

}