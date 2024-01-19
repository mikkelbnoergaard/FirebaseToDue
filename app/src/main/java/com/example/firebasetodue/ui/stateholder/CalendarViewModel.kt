package com.example.firebasetodue.ui.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetodue.dataLayer.source.local.ToDoRepository
import com.example.firebasetodue.state.CalendarState
import com.example.firebasetodue.ui.event.CalendarEvent
import com.example.firebasetodue.ui.sortType.CalendarSortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val toDoRepository: ToDoRepository
): ViewModel() {
    private val calendarSortType = MutableStateFlow(CalendarSortType.GIVEN_DATE)
    private val selectedCalendarDate = MutableStateFlow(LocalDate.now().toString())

    //Variable to trigger recompose when we perform tasks outside of the calendar that affects
    //the list of ToDos in the calendar view. Necessary to show the correct items in calendar view
    //at all times.
    private val recompose = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _toDos = combine(calendarSortType, selectedCalendarDate, recompose) { calendarSortType, selectedCalendarDate, _ ->
        when (calendarSortType) {
            CalendarSortType.GIVEN_DATE -> toDoRepository.getToDosByGivenDate(selectedCalendarDate)
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

            is CalendarEvent.Recompose -> {
                viewModelScope.launch {
                    //delay to ensure any other coroutines finish before this
                    delay(200L)
                    recompose.value = !recompose.value
                }
            }
        }
    }
}