package com.example.todue.state

import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.ui.sortType.CalendarSortType

data class StatisticsState(

    val totalAmountOfCreatedToDos: Int = 0,
    val totalAmountOfFinishedToDos: Int = 0,
    val totalAmountOfUnfinishedToDos: Int = 0

)