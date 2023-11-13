package com.example.todue.dataLayer.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    val title: String,
    val description: String,
    val tag: String,
    val dueDate: String,
    val finished: Boolean,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
