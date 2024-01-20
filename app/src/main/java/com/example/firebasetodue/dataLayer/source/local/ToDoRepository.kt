//suppression of a warning when checking if stats return null; compiler things statement is always true which is not the case
@file:Suppress("SENSELESS_COMPARISON")

package com.example.firebasetodue.dataLayer.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepository @Inject constructor(
    private val dataSource: ToDoDao
){
    suspend fun createToDo(title: String,
                           description: String,
                           tag: String,
                           dueDate: String,
                           dueTime: String,
                           finished: Boolean,
                           userKey: String) {
        val toDo = ToDo(
            title = title,
            description = description,
            tag = tag,
            dueDate = dueDate,
            dueTime = dueTime,
            finished = finished,
            userKey = userKey
        )
        dataSource.createToDo(toDo)
    }

    suspend fun finishToDo(toDo: ToDo) {
        dataSource.finishToDo(toDoId = toDo.id)
    }

    suspend fun unFinishToDo(toDo: ToDo) {
        dataSource.unFinishToDo(toDoId = toDo.id)
    }

    suspend fun deleteToDo(toDo: ToDo){
        dataSource.deleteToDo(toDo)
    }

    fun getToDosOrderedByTags(search: String, finished: Boolean) : Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByTags(search, finished)
    }

    fun getToDosOrderedByDueDate(search: String, finished: Boolean): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDueDate(search, finished)
    }

    fun getToDosByGivenDate(date: String): Flow<List<ToDo>> {
        return dataSource.getToDosByGivenDate(date = date)
    }

    suspend fun deleteTagFromToDos(tag: String){
        dataSource.deleteTagFromToDos(tag)
    }

    suspend fun editToDo(newTitle: String, newDescription: String, newTag: String, newDueDate: String, newDueTime: String, toDoId: Int) {
        dataSource.editToDo(newTitle = newTitle, newDescription = newDescription, newTag = newTag, newDueDate = newDueDate, newDueTime = newDueTime, toDoId = toDoId)
    }





    suspend fun createToDoFromFirebase(
        id: Int,
        title: String,
        description: String,
        tag: String,
        dueDate: String,
        dueTime: String,
        finished: Boolean) {
        val toDo = ToDo(
            id = id,
            title = title,
            description = description,
            tag = tag,
            dueDate = dueDate,
            dueTime = dueTime,
            finished = finished
        )
        dataSource.createToDo(toDo)
    }





    //for statistics. they have to check for null, if not then app doesn't work when 0 todos are created
    //warnings are suppressed, app doesn't run correctly without if statements
    suspend fun getTotalAmountOfCreatedToDos(): Int {
        return if(dataSource.getTotalAmountOfCreatedToDos() != null) {
            dataSource.getTotalAmountOfCreatedToDos()
        } else {
            0
        }
    }

    suspend fun getTotalAmountOfFinishedToDos(): Int {
        return if(dataSource.getTotalAmountOfFinishedToDos() != null) {
            dataSource.getTotalAmountOfFinishedToDos()
        } else {
            0
        }
    }

    suspend fun getTotalAmountOfUnfinishedToDos(): Int {
        return if(dataSource.getTotalAmountOfUnfinishedToDos() != null) {
            dataSource.getTotalAmountOfUnfinishedToDos()
        } else {
            0
        }
    }





    //for testing:
    fun createTestToDoInDB(title: String,
                           description: String,
                           tag: String,
                           dueDate: String,
                           dueTime: String,
                           finished: Boolean) {
        val toDo = ToDo(
            title = title,
            description = description,
            tag = tag,
            dueDate = dueDate,
            dueTime = dueTime,
            finished = finished
        )
        dataSource.createTestToDoInDB(toDo)
    }

    fun getTestToDoTitle(toDoId: Int): String {
        return dataSource.getTestToDoTitle(toDoId = toDoId)
    }

    fun getTestToDoDescription(toDoId: Int): String {
        return dataSource.getTestToDoDescription(toDoId = toDoId)
    }

    fun getTestToDoTag(toDoId: Int): String {
        return dataSource.getTestToDoTag(toDoId = toDoId)
    }

    fun getTestToDoDueDate(toDoId: Int): String {
        return dataSource.getTestToDoDueDate(toDoId = toDoId)
    }

    fun getTestToDoDueTime(toDoId: Int): String {
        return dataSource.getTestToDoDueTime(toDoId = toDoId)
    }
}