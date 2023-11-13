package com.example.todue.ui.screens.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todue.dataLayer.local.TagDao
import com.example.todue.ui.event.TagEvent
import com.example.todue.ui.sortType.TagSortType
import com.example.todue.state.TagState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagsViewModel(
    private val tagDao: TagDao
): ViewModel() {
    private val tagSortType = MutableStateFlow(TagSortType.TITLE)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _tags = tagSortType
        .flatMapLatest { sortType ->
            when(sortType) {
                TagSortType.TITLE -> tagDao.getTagsOrderedByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _tagState = MutableStateFlow(TagState())
    val tagState = combine(_tagState, tagSortType, _tags){ state, sortType, tags ->
        state.copy(
            tags = tags,
            tagSortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TagState())


    fun onEvent(tagEvent: TagEvent) {
        when(tagEvent){
            is TagEvent.CreateTag -> {
                val title = tagState.value.title
                val toDoAmount = tagState.value.toDoAmount

                if(title.isBlank() || toDoAmount == 0) {
                    return
                }
            }
            is TagEvent.DeleteTag -> {
                viewModelScope.launch {
                    tagDao.deleteTag(tagEvent.tag)
                }
            }
            is TagEvent.ShowDeleteDialog -> {
                _tagState.update {it.copy(
                    isDeletingTag = true
                ) }
            }
            is TagEvent.HideDeleteDialog -> {
                _tagState.update {it.copy(
                    isDeletingTag = false
                ) }
            }
            is TagEvent.SetTitle -> {
                _tagState.update {it.copy(
                    title = tagEvent.title
                ) }
            }
            is TagEvent.IncreaseToDoAmount -> {
                _tagState.update {it.copy(
                    toDoAmount = it.toDoAmount+1
                ) }
            }
            is TagEvent.DecreaseToDoAmount -> {
                _tagState.update {it.copy(
                    toDoAmount = it.toDoAmount-1
                ) }
            }
        }
    }
}