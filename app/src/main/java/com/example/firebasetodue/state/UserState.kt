package com.example.firebasetodue.state

data class UserState(

    val userKey: String = "",
    val subscribedKeys: List<String> = emptyList(),
    val keyTyping: String = "",

    )