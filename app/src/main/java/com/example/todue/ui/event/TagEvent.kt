package com.example.todue.ui.event

import com.example.todue.dataLayer.source.local.Tag

sealed interface TagEvent {

    data object ShowDeleteDialog: TagEvent
    data object HideDeleteDialog: TagEvent
    data object ResetTagSort: TagEvent

    data class CreateTag(val title: String): TagEvent
    data class SetTitle(val title: String): TagEvent
    data class IncreaseToDoAmount(val toDoAmount: Int): TagEvent
    data class DecreaseToDoAmount(val title: String): TagEvent
    data class SortByThisTag(val tag: Tag): TagEvent
    data class DontSortByThisTag(val title: String): TagEvent
    data class DeleteTag(val title: String): TagEvent

}