package com.example.todue.dataLayer.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val tag: String,
    val dueDate: String,
    val dueTime: String,
    val finished: Boolean
)