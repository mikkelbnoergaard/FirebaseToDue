package com.example.todue.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todue.dataLayer.source.local.ToDo
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val toDoRepository: ToDoRepository
): ViewModel() {
    private val calendarSortType = MutableStateFlow(CalendarSortType.GIVEN_DATE)
    private val selectedCalendarDate = MutableStateFlow(LocalDate.now().toString())


    @OptIn(ExperimentalCoroutinesApi::class)
    private val _toDos = calendarSortType
        .flatMapLatest { calendarSortType ->
            when(calendarSortType) {
                CalendarSortType.GIVEN_DATE -> toDoRepository.getToDosByGivenDate(selectedCalendarDate.value)
                CalendarSortType.PLACEHOLDER -> toDoRepository.getToDosByGivenDate(selectedCalendarDate.value)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _calendarState = MutableStateFlow(CalendarState())
    val calendarState = combine(_calendarState, calendarSortType, _toDos){ calendarState, calendarSortType, toDos ->
        calendarState.copy(
            toDos = toDos,
            calendarSortType = calendarSortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CalendarState())

    fun onEvent(calendarEvent: CalendarEvent) {

        when(calendarEvent) {

            is CalendarEvent.CreateToDo -> {

                val title = calendarState.value.title
                val description = calendarState.value.description
                val tag = calendarState.value.tag
                val dueDate = calendarState.value.dueDate
                val dueTime = calendarState.value.dueTime
                val finished = calendarState.value.finished

                if (title.isEmpty()) {
                    return
                }

                val toDoObject = ToDo(
                    title = title,
                    description = description,
                    tag = tag,
                    dueDate = dueDate,
                    dueTime = dueTime,
                    finished = finished
                )

                viewModelScope.launch{
                    toDoRepository.createToDo(
                        toDoObject.title,
                        toDoObject.description,
                        toDoObject.tag,
                        toDoObject.dueDate,
                        toDoObject.dueTime,
                        toDoObject.finished
                    )
                }

                _calendarState.update { it.copy(
                    isCreatingToDo = false,
                    isDeletingToDo = false,
                    isEditingToDo = false,
                    title = "",
                    description = "",
                    tag = "",
                    dueDate = "",
                    dueTime = "",
                    finished = false
                ) }

            }

            is CalendarEvent.DeleteToDo -> {
                viewModelScope.launch {
                    toDoRepository.deleteToDo(calendarEvent.toDo)
                }
            }

            is CalendarEvent.HideCreateDialog -> {
                _calendarState.update {it.copy(
                    isCreatingToDo = false
                ) }
            }

            is CalendarEvent.SetDescription -> {
                _calendarState.update {it.copy(
                    description = calendarEvent.description
                ) }
            }

            is CalendarEvent.SetDueDate -> {
                _calendarState.update {it.copy(
                    dueDate = calendarEvent.dueDate
                ) }
            }

            is CalendarEvent.SetDueTime -> {
                _calendarState.update {it.copy(
                    dueTime = calendarEvent.dueTime
                )}
            }

            is CalendarEvent.FinishToDo -> {
                viewModelScope.launch {
                    toDoRepository.finishToDo(calendarEvent.toDo)
                }
            }

            is CalendarEvent.UnFinishToDo -> {
                viewModelScope.launch {
                    toDoRepository.unFinishToDo(calendarEvent.toDo)
                }
            }

            is CalendarEvent.SetTag -> {
                _calendarState.update {it.copy(
                    tag = calendarEvent.tag
                ) }
            }

            is CalendarEvent.SetTitle -> {
                _calendarState.update {it.copy(
                    title = calendarEvent.title
                ) }
            }

            is CalendarEvent.ShowCreateDialog -> {
                _calendarState.update {it.copy(
                    isCreatingToDo = true
                ) }
            }

            is CalendarEvent.ShowDeleteToDoDialog -> {
                _calendarState.update {it.copy(
                    isDeletingToDo = true
                ) }
            }

            is CalendarEvent.HideDeleteToDoDialog -> {
                _calendarState.update {it.copy(
                    isDeletingToDo = false
                ) }
            }

            is CalendarEvent.ShowToDoDialog -> {
                _calendarState.update {it.copy(
                    isCheckingToDo = true
                ) }
            }

            is CalendarEvent.HideToDoDialog -> {
                _calendarState.update {it.copy(
                    isCheckingToDo = false
                ) }
            }

            is CalendarEvent.DeleteTagFromToDos -> {
                viewModelScope.launch {
                    toDoRepository.deleteTagFromToDos(calendarEvent.tag)
                }
            }

            is CalendarEvent.ShowEditToDoDialog -> {
                _calendarState.update {it.copy(
                    isEditingToDo = true
                )}
            }

            is CalendarEvent.HideEditToDoDialog -> {
                _calendarState.update {it.copy(
                    isEditingToDo = false
                )}
            }

            is CalendarEvent.EditToDo -> {

                viewModelScope.launch {
                    toDoRepository.editToDo(newTitle = calendarEvent.newTitle, newDescription = calendarEvent.newDescription, newTag = calendarEvent.newTag, newDueDate = calendarEvent.newDueDate, newDueTime = calendarEvent.newDueTime, toDoId = calendarEvent.toDoId)
                }

                _calendarState.update {
                    it.copy(
                        title = "",
                        description = "",
                        tag = "",
                        dueDate = "",
                        dueTime = "",
                        isEditingToDo = false,
                        isCheckingToDo = false
                    )
                }
            }

            is CalendarEvent.SetToDoStateForEdit -> {
                _calendarState.update {
                    it.copy(
                        title = calendarEvent.toDo.title,
                        description = calendarEvent.toDo.description,
                        tag = calendarEvent.toDo.tag,
                        dueDate = calendarEvent.toDo.dueDate,
                        dueTime = calendarEvent.toDo.dueTime
                    )
                }
            }

            is CalendarEvent.ResetToDoState -> {
                _calendarState.update {
                    it.copy(
                        isCreatingToDo = false,
                        isDeletingToDo = false,
                        isEditingToDo = false,
                        title = "",
                        description = "",
                        tag = "",
                        dueDate = "",
                        dueTime = "",
                        finished = false
                    )
                }
            }

            is CalendarEvent.SortToDosByGivenDate -> {
                calendarSortType.value = CalendarSortType.PLACEHOLDER
                selectedCalendarDate.value = calendarEvent.date
                calendarSortType.value = CalendarSortType.GIVEN_DATE
            }
        }
    }
}