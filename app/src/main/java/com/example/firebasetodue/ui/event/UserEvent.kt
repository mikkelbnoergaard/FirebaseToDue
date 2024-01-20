package com.example.firebasetodue.ui.event

import com.example.firebasetodue.dataLayer.source.local.User

sealed interface UserEvent {

    data object SubscribeToKey: UserEvent
    data object RetrieveFirebaseToDos: UserEvent

    data object SetUserKey: UserEvent
    data class CreateUser(val user: User): UserEvent
    data class KeyTyping(val key: String): UserEvent

}