package com.example.firebasetodue.di

import android.content.Context
import androidx.room.Room
import com.example.firebasetodue.dataLayer.source.local.TagDao
import com.example.firebasetodue.dataLayer.source.local.ToDoDao
import com.example.firebasetodue.dataLayer.source.local.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModules {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "todo.db"
        ).build()
    }

    @Provides
    fun provideToDoDao(database: ToDoDatabase) : ToDoDao = database.toDoDao

    @Provides
    fun provideTagDao(database: ToDoDatabase) : TagDao = database.tagDao

}