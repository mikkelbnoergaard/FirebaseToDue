package com.example.firebasetodue.dataLayer.source.remote.database


import android.content.Context
import android.widget.Toast
import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseRepository(context: Context){

    val context = context

    private val firebaseDb = Firebase.firestore.collection("toDos")

    fun firebaseSaveToDo(toDo: ToDo) = CoroutineScope(Dispatchers.IO).launch{
        try {
            firebaseDb.add(toDo).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Successfully saved data", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun subscribeToRealtimeUpdates() {
        firebaseDb.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val stringBuilder = StringBuilder()
                for(document in it) {
                    val toDo = document.toObject<ToDo>()
                    stringBuilder.append("$toDo\n")
                }
                println(stringBuilder.toString())
            }
        }
    }

    fun retrieveOneToDo(id: Int) = CoroutineScope(Dispatchers.IO).launch {
        var toDo: ToDo? = ToDo(
            title = "",
            description = "",
            tag = "",
            dueDate = "",
            dueTime = "",
            finished = false
        )
        try {
            val querySnapshot = firebaseDb.whereEqualTo("id", id).get().await()
            for (document in querySnapshot.documents) {
                toDo = document.toObject<ToDo>()
            }
            withContext(Dispatchers.Main) {
                if (toDo != null) {
                    println(toDo.title)
                    println(toDo.description)
                    println(toDo.tag)
                    println(toDo.dueDate)
                    println(toDo.dueTime)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun retrieveToDosByDueDate() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = firebaseDb
                .orderBy("dueDate")
                .orderBy("dueTime")
                .get().await()
            val stringBuilder = StringBuilder()
            for(document in querySnapshot.documents) {
                val toDo = document.toObject<ToDo>()
                stringBuilder.append("$toDo\n")
            }
            withContext(Dispatchers.Main) {
                println(stringBuilder.toString())
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

