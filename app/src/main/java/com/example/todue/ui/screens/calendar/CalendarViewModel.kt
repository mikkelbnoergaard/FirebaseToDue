package com.example.todue.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todue.dataLayer.source.local.ToDoRepository
import com.example.todue.state.CalendarState
import com.example.todue.ui.event.CalendarEvent
import com.example.todue.ui.sortType.CalendarSortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class CalendarViewModel(
    private val toDoRepository: ToDoRepository
): ViewModel() {
    private val calendarSortType = MutableStateFlow(CalendarSortType.GIVEN_DATE)
    private val selectedCalendarDate = MutableStateFlow(LocalDate.now().toString())


    @OptIn(ExperimentalCoroutinesApi::class)
    private val _toDos = combine(calendarSortType, selectedCalendarDate) { calendarSortType, selectedCalendarDate ->

        when (calendarSortType) {
            CalendarSortType.GIVEN_DATE -> toDoRepository.getToDosByGivenDate(selectedCalendarDate)
            CalendarSortType.PLACEHOLDER -> toDoRepository.getToDosByGivenDate(selectedCalendarDate)
        }
    }.flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _calendarState = MutableStateFlow(CalendarState())
    val calendarState = combine(_calendarState, calendarSortType, _toDos){ calendarState, calendarSortType, toDos ->
        calendarState.copy(
            toDos = toDos,
            calendarSortType = calendarSortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CalendarState())

    fun onEvent(calendarEvent: CalendarEvent) {

        when(calendarEvent) {

            is CalendarEvent.SortToDosByGivenDate -> {
                selectedCalendarDate.value = calendarEvent.date
            }
        }
    }
}