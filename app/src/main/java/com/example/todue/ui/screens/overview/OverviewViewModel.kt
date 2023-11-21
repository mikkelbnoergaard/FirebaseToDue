package com.example.todue.ui.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todue.dataLayer.source.local.Tag
import com.example.todue.dataLayer.source.local.TagDao
import com.example.todue.ui.sortType.ToDoSortType
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.dataLayer.source.local.ToDoDao
import com.example.todue.dataLayer.source.local.ToDoRepository
import com.example.todue.ui.event.ToDoEvent
import com.example.todue.state.ToDoState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class OverviewViewModel(
    private val toDoRepository: ToDoDao,
    private val tagDao: TagDao,
): ViewModel() {
    private val toDoSortType = MutableStateFlow(ToDoSortType.DUE_DATE)

    private var tagSort = "1234"

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _toDos = toDoSortType
        .flatMapLatest { sortType ->
            when(sortType) {
                ToDoSortType.TITLE -> toDoRepository.getToDosOrderedByTitle()
                ToDoSortType.TAG -> toDoRepository.getToDosOrderedByTag(tagSort)
                ToDoSortType.DESCRIPTION -> toDoRepository.getToDosOrderedByDescription()
                ToDoSortType.DUE_DATE -> toDoRepository.getToDosOrderedByDueDate()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _toDoState = MutableStateFlow(ToDoState())
    val toDoState = combine(_toDoState, toDoSortType, _toDos){ state, sortType, toDos ->
        state.copy(
            toDos = toDos,
            toDoSortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ToDoState())

    fun onEvent(toDoEvent: ToDoEvent) {
        when(toDoEvent){
            is ToDoEvent.CreateToDo -> {
                val title = toDoState.value.title
                val description = toDoState.value.description
                val tag = toDoState.value.tag
                val dueDate = toDoState.value.dueDate
                val finished = toDoState.value.finished

                if(title.isBlank() || description.isBlank() || tag.isBlank() || dueDate.isBlank()) {
                    return
                }

                val toDo = ToDo(
                    title = title,
                    description = description,
                    tag = tag,
                    dueDate = dueDate,
                    finished = finished
                )

                val tagObject = Tag(
                    title = toDo.tag,
                    toDoAmount = 1
                )

                viewModelScope.launch{
                    tagDao.createTag(tagObject)
                }

                viewModelScope.launch{
                    toDoRepository.createToDo(toDo)
                }
                _toDoState.update { it.copy(
                    isCreatingToDo = false,
                    isDeletingToDo = false,
                    title = "",
                    description = "",
                    tag = "",
                    dueDate = "",
                    finished = false
                ) }

            }
            /* SKAL IMPLEMENTERES SENERE
            is ToDoEvent.DeleteToDo -> {
                viewModelScope.launch {
                    toDoRepository.deleteToDo(toDoEvent.toDo)
                }
            }
            */
            is ToDoEvent.HideCreateDialog -> {
                _toDoState.update {it.copy(
                    isCreatingToDo = false
                ) }
            }
            is ToDoEvent.SetDescription -> {
                _toDoState.update {it.copy(
                    description = toDoEvent.description
                ) }
            }
            is ToDoEvent.SetDueDate -> {
                _toDoState.update {it.copy(
                    dueDate = toDoEvent.dueDate
                ) }
            }
            is ToDoEvent.FinishToDo -> {
                _toDoState.update {it.copy(
                    finished = true
                ) }

            }
            is ToDoEvent.SetFinished -> {
                _toDoState.update {it.copy(
                    finished = toDoEvent.finished
                ) }

            }
            is ToDoEvent.SetTag -> {
                _toDoState.update {it.copy(
                    tag = toDoEvent.tag
                ) }
            }
            is ToDoEvent.SetTitle -> {
                _toDoState.update {it.copy(
                    title = toDoEvent.title
                ) }
            }
            is ToDoEvent.ShowCreateDialog -> {
                _toDoState.update {it.copy(
                    isCreatingToDo = true
                ) }
            }
            is ToDoEvent.ShowDeleteDialog -> {
                _toDoState.update {it.copy(
                    isDeletingToDo = true
                ) }
            }
            is ToDoEvent.HideDeleteDialog -> {
                _toDoState.update {it.copy(
                    isDeletingToDo = false
                ) }
            }
            is ToDoEvent.ShowToDoDialog -> {
                _toDoState.update {it.copy(
                    isCheckingToDo = true
                ) }
            }
            is ToDoEvent.HideToDoDialog -> {
                _toDoState.update {it.copy(
                    isCheckingToDo = false
                ) }
            }
            is ToDoEvent.SortToDos -> {
                tagSort = toDoEvent.tag
                toDoSortType.value = toDoEvent.toDoSortType
            }
            /*
            is ToDoEvent.DeleteToDosWithGivenTag -> {
                dao.deleteToDosWithGivenTag("123")
            }

             */
            else -> {
                return
            }
        }
    }
}