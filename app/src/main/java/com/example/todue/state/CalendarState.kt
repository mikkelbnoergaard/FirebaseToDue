package com.example.todue.state

import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.ui.sortType.CalendarSortType

data class CalendarState(

    val toDos: List<ToDo> = emptyList(),
    val givenDate: String = "",
    val calendarSortType: CalendarSortType = CalendarSortType.GIVEN_DATE

)