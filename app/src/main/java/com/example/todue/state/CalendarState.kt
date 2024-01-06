package com.example.todue.state

import com.example.todue.dataLayer.source.local.Tag
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.ui.sortType.CalendarSortType

data class CalendarState(

    val toDos: List<ToDo> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val title: String = "",
    val description: String = "",
    val tag: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val finished: Boolean = false,
    val givenDate: String = "",
    val isCreatingToDo: Boolean = false,
    val isDeletingToDo: Boolean = false,
    val isCheckingToDo: Boolean = false,
    val isEditingToDo: Boolean = false,
    val calendarSortType: CalendarSortType = CalendarSortType.GIVEN_DATE

)