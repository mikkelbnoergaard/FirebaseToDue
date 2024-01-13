package com.example.todue.ui.event

sealed interface CalendarEvent {

    data class SortToDosByGivenDate(val date: String): CalendarEvent

}