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

    fun getToDosOrderedByTags(search: String) : Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByTags(search)
    }

    fun getToDosOrderedByDescription(): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDescription()
    }

    fun getToDosOrderedByDueDate(search: String): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDueDate(search)
    }

    fun getFinishedToDos(): Flow<List<ToDo>> {
        return dataSource.getFinishedToDos()
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



    //for statistics:
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