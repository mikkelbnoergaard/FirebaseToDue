package com.example.todue.dataLayer.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val toDoAmount: Int,
    val sort: Boolean

)