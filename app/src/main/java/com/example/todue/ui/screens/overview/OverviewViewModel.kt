package com.example.todue.ui.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todue.ui.sortType.ToDoSortType
import com.example.todue.dataLayer.source.local.ToDo
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
    private val toDoRepository: ToDoRepository
): ViewModel() {
    private val toDoSortType = MutableStateFlow(ToDoSortType.DUE_DATE)

    private var tagList = mutableListOf<String>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _toDos = toDoSortType
        .flatMapLatest { sortType ->
            when(sortType) {
                ToDoSortType.TITLE -> toDoRepository.getToDosOrderedByTitle()
                ToDoSortType.TAG -> toDoRepository.getToDosOrderedByTags()
                ToDoSortType.DESCRIPTION -> toDoRepository.getToDosOrderedByDescription()
                ToDoSortType.DUE_DATE -> toDoRepository.getToDosOrderedByDueDate()
                ToDoSortType.FINISHED -> toDoRepository.getFinishedToDos()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _toDoState = MutableStateFlow(ToDoState())
    val toDoState = combine(_toDoState, toDoSortType, _toDos){ toDoState, toDoSortType, toDos ->
        toDoState.copy(
            toDos = toDos,
            toDoSortType = toDoSortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ToDoState())

    fun onEvent(toDoEvent: ToDoEvent) {
        when(toDoEvent){
            is ToDoEvent.CreateToDo -> {
                val title = toDoState.value.title
                val description = toDoState.value.description
                val tag = toDoState.value.tag
                val dueDate = toDoState.value.dueDate
                val dueTime = toDoState.value.dueTime
                val finished = toDoState.value.finished

                if(title.isBlank() || description.isBlank() || tag.isBlank() || dueDate.isBlank()) {
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
                    toDoRepository.createTodo(
                        toDoObject.title,
                        toDoObject.description,
                        toDoObject.tag,
                        toDoObject.dueDate,
                        toDoObject.dueTime,
                        toDoObject.finished
                    )
                }

                _toDoState.update { it.copy(
                    isCreatingToDo = false,
                    isDeletingToDo = false,
                    title = "",
                    description = "",
                    tag = "",
                    dueDate = "",
                    dueTime = "",
                    finished = false
                ) }

            }
            is ToDoEvent.DeleteToDo -> {
                viewModelScope.launch {
                    toDoRepository.deleteToDo(toDoEvent.toDo)
                }
            }
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
            is ToDoEvent.SetDueTime -> {
                _toDoState.update {it.copy(
                    dueTime = toDoEvent.dueTime
                )}
            }
            is ToDoEvent.FinishToDo -> {
                viewModelScope.launch {
                    toDoRepository.finishToDo(toDoEvent.toDo)
                }
            }
            is ToDoEvent.UnFinishToDo -> {
                viewModelScope.launch {
                    toDoRepository.unFinishToDo(toDoEvent.toDo)
                }
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

            is ToDoEvent.ShowDeleteToDoDialog -> {
                _toDoState.update {it.copy(
                    isDeletingToDo = true
                ) }

            }

            is ToDoEvent.HideDeleteToDoDialog -> {
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

            is ToDoEvent.AddTagToSortToDos -> {
                _toDoState.update {it.copy(
                    isDeletingToDo = false
                ) }
                toDoSortType.value = ToDoSortType.TAG
                _toDoState.update {it.copy(
                    isDeletingToDo = false
                ) }
            }

            is ToDoEvent.RemoveTagToSortToDos -> {

                tagList.remove(toDoEvent.tag)
                if(tagList.isEmpty()){
                    toDoSortType.value = ToDoSortType.DUE_DATE
                } else {
                    toDoSortType.value = ToDoSortType.TAG
                }
                println(tagList.toString())
                _toDoState.update {it.copy(
                    isDeletingToDo = false
                ) }
            }

            is ToDoEvent.SortToDosByFinished -> {
                toDoSortType.value = ToDoSortType.FINISHED
            }

            is ToDoEvent.SortToDosByDueDate -> {
                toDoSortType.value = ToDoSortType.DUE_DATE
            }

            //does not work yet
            is ToDoEvent.DeleteTagFromTodos -> {
                viewModelScope.launch {
                    toDoRepository.deleteTagFromTodos(toDoEvent.tag)
                }
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