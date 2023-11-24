package com.example.todue.dataLayer.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepository @Inject constructor(
    private val dataSource: ToDoDao
){
    fun observeAll() : Flow<List<ToDo>> {
        return dataSource.observeAll()
    }

    suspend fun createTodo(title: String,
                           description: String,
                           tag: String,
                           dueDate: String,
                           finished: Boolean) {
        val toDo = ToDo(
            title = title,
            description = description,
            tag = tag,
            dueDate = dueDate,
            finished = finished
        )
        dataSource.createToDo(toDo)
    }

    suspend fun completeToDo(id : Int) {
        dataSource.updateFinished(id)
    }

    suspend fun deleteToDo(toDo: ToDo){
        dataSource.deleteToDo(toDo)
    }

    fun getToDosOrderedByTitle() : Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByTitle()
    }

    fun getToDosOrderedByTag(tag : String) : Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByTag(tag)
    }

    fun getToDosOrderedByDescription(): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDescription()
    }

    fun getToDosOrderedByDueDate(): Flow<List<ToDo>> {
        return dataSource.getToDosOrderedByDueDate()
    }

}