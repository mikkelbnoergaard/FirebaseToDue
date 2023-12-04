package com.example.todue.dataLayer.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepository @Inject constructor(
    private val dataSource: ToDoDao
){

    /*
    fun observeAll() : Flow<List<ToDo>> {
        return dataSource.observeAll()
    }

     */

    suspend fun createToDo(title: String,
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

    fun getToDosOrderedByTitle() : Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByTitle()
    }

    fun getToDosOrderedByTags() : Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByTags()
    }

    fun getToDosOrderedByDescription(): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDescription()
    }

    fun getToDosOrderedByDueDate(): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDueDate()
    }

    fun getFinishedToDos(): Flow<List<ToDo>> {
        return dataSource.getFinishedToDos()
    }

    suspend fun deleteTagFromToDos(tag: String){
        dataSource.deleteTagFromToDos(tag)
    }

    suspend fun checkIfSortByTags(): Boolean {
        return dataSource.checkIfSortByTags()
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