package com.example.firebasetodue.dataLayer.source.remote.database


import android.content.Context
import android.widget.Toast
import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseRepository(private val context: Context){

    private val firebaseDb = Firebase.firestore.collection("toDos")

    //edits an object in Firebase to match the input object
    private fun updateToDoInFirebase(toDo: ToDo) = CoroutineScope(Dispatchers.IO).launch {

        val newToDo = ToDo(
            id = toDo.id,
            title = toDo.title,
            description = toDo.description,
            tag = toDo.tag,
            dueDate = toDo.dueDate,
            dueTime = toDo.dueTime,
            finished = toDo.finished
        )

        val toDoQuery = firebaseDb
            .whereEqualTo("id", toDo.id)
            .get()
            .await()

        if(toDoQuery.documents.isNotEmpty()) {
            for(document in toDoQuery) {
                try {
                    firebaseDb.document(document.id).set(
                        newToDo,
                        SetOptions.merge()
                    ).await()
                } catch(e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "No ToDo to update", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Saves an object in the firebase database. Only if the todo doesn't exist already.
    fun firebaseSaveToDo(toDo: ToDo) = CoroutineScope(Dispatchers.IO).launch{
        var retrievedToDo: ToDo? = ToDo(
            title = "",
            description = "",
            tag = "",
            dueDate = "",
            dueTime = "",
            finished = false
        )

        try {
            //tries to retrieve an object with the same id. if it exists, it does nothing; if not, it uploads the object
            val querySnapshot = firebaseDb.whereEqualTo("id", toDo.id).get().await()
            for (document in querySnapshot.documents) {
                retrievedToDo = document.toObject<ToDo>()
            }
            if (retrievedToDo != null) {
                if(retrievedToDo.id != toDo.id) {
                    firebaseDb.add(toDo).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Successfully saved data", Toast.LENGTH_LONG).show()
                    }
                } else if(retrievedToDo.id == toDo.id) {
                    updateToDoInFirebase(toDo)
                }
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

    //gets a single object from firebase database
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
                    //add stuff
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private val toDoList = mutableListOf<ToDo>()

    //returns toDoList
    fun getToDoListInFirebase(): List<ToDo> {
        retrieveToDosByDueDate()
        return toDoList.toList()
    }

    //clears the toDoList, to be used after getToDoListInFirebase() is called so list is up to date at all times
    fun clearToDoList() {
        toDoList.clear()
    }

    private fun retrieveToDosByDueDate() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = firebaseDb
                .orderBy("dueDate")
                .orderBy("dueTime")
                .get().await()
            val stringBuilder = StringBuilder()
            for(document in querySnapshot.documents) {
                val toDo = document.toObject<ToDo>()
                if (toDo != null) {
                    toDoList.add(toDo)
                }
                stringBuilder.append("$toDo\n")
            }
            withContext(Dispatchers.Main) {
                //println(stringBuilder.toString())
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                println(e.message)
            }
        }
    }
}

