package com.example.firebasetodue.dataLayer.source.remote.database

import com.example.firebasetodue.dataLayer.source.local.ToDo
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val dataSource: FirebaseDao
){
    fun firebaseSaveToDo(toDo: ToDo) {
        dataSource.firebaseSaveToDo(toDo)
    }
}