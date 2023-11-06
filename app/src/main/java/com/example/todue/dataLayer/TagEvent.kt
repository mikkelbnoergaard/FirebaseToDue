package com.example.todue.dataLayer

//Might be used in the future
sealed interface TagEvent {
    object CreateTag: TagEvent

    data class SetTitle(val title: String): TagEvent
    data class IncreaseToDoAmount(val toDoAmount: Int): TagEvent

    data class DecreaseToDoAmount(val toDoAmount: Int): TagEvent

    //object ShowDialog: TagEvent
    //object HideDialog: TagEvent

    object ShowDeleteDialog: TagEvent
    object HideDeleteDialog: TagEvent

    data class DeleteTag(val tag: Tag): TagEvent

}