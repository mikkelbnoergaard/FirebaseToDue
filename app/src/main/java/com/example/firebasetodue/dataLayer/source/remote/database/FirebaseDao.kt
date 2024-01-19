package com.example.firebasetodue.dataLayer.source.remote.database

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/*
private val db = Firebase.firestore.collection("toDos")

interface FirebaseDao {

    fun firebaseSaveToDo(toDo: ToDo) = CoroutineScope(Dispatchers.IO).launch{
        try {
            db.add(toDo).await()
            withContext(Dispatchers.Main) {
                println("Successfully uploaded ToDo")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                println(e.message)
            }
        }
    }

    fun subscribeToRealtimeUpdates() {
        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val stringBuilder = StringBuilder()
                for(document in it) {
                    val toDo = document.toObject<ToDo>()
                    stringBuilder.append("$toDo\n")
                }
                tvToDos.text = stringBuilder.toString()
            }
        }
    }

}

 */
