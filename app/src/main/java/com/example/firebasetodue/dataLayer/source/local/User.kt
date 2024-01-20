package com.example.firebasetodue.dataLayer.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userKey: String,
    val subscribedKeys: String = ""
)