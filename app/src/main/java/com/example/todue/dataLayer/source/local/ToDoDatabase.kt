package com.example.todue.dataLayer.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ToDo::class, Tag::class],
    version = 1
)
abstract class ToDoDatabase: RoomDatabase() {
    abstract val toDoDao: ToDoDao
    abstract val tagDao: TagDao
}
