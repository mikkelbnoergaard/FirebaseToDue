package com.example.firebasetodue.ui.event

import com.example.firebasetodue.dataLayer.source.local.ToDo

interface FirebaseEvent {

    data class SaveToDoInFirebaseDatabase(val toDo: ToDo): FirebaseEvent
    //data class GetToDoInFirebaseDatabase()

}