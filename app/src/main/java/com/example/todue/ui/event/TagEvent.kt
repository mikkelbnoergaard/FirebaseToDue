package com.example.todue.ui.event

import com.example.todue.dataLayer.source.local.Tag


sealed interface TagEvent {
    data object CreateTag: TagEvent

    data class SetTitle(val title: String): TagEvent
    data class IncreaseToDoAmount(val toDoAmount: Int): TagEvent

    data class DecreaseToDoAmount(val toDoAmount: Int): TagEvent

    //object ShowDialog: TagEvent
    //object HideDialog: TagEvent

    data object ShowDeleteDialog: TagEvent
    data object HideDeleteDialog: TagEvent

    data class DeleteTag(val tag: Tag): TagEvent

}