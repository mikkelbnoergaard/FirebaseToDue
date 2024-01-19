package com.example.firebasetodue.di

import android.content.Context
import androidx.room.Room
import com.example.firebasetodue.dataLayer.source.local.TagDao
import com.example.firebasetodue.dataLayer.source.local.ToDoDao
import com.example.firebasetodue.dataLayer.source.local.ToDoDatabase
import com.example.firebasetodue.dataLayer.source.remote.database.FirebaseDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    private val db = Firebase.firestore.collection("toDos")

    private val db = Firebase.firestore.collection("toDos")

    @Provides
    fun provideToDoDao(database: ToDoDatabase) : ToDoDao = database.toDoDao

    @Provides
    fun provideTagDao(database: ToDoDatabase) : TagDao = database.tagDao

    @Provides
    fun provideFirebaseDao() : FirebaseDao = firebase.firebaseDao
}