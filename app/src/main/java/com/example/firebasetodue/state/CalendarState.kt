package com.example.firebasetodue.state

import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.example.firebasetodue.ui.sortType.CalendarSortType

data class CalendarState(

    val toDos: List<ToDo> = emptyList(),
    val givenDate: String = "",
    val calendarSortType: CalendarSortType = CalendarSortType.GIVEN_DATE

)