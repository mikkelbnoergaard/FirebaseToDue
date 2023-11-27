package com.example.todue.dataLayer.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//Might be used in the future
@Entity
data class Tag(
    val title: String,
    val toDoAmount: Int,
    val sort: Boolean,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)