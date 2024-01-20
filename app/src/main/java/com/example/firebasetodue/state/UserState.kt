package com.example.firebasetodue.state

import kotlinx.coroutines.flow.Flow

data class UserState(

    val userKey: String = "",
    val subscribedKeys: List<String> = emptyList(),
    val keyTyping: String = "",

    )