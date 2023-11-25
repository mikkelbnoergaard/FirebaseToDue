package com.example.todue.dataLayer.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
// import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ToDoDao {

    @Insert
    suspend fun createToDo(toDo: ToDo)

    //@Update
    //suspend fun finishToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("SELECT * FROM todo")
    fun observeAll(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE finished IS 0 ORDER BY title")
    fun getToDosOrderedByTitle(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE tag IN (:tag)")
    fun getToDosOrderedByTag(tag: List<String>): Flow<List<ToDo>>

    @Query("SELECT * FROM todo ORDER BY description")
    fun getToDosOrderedByDescription(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE finished = 0 ORDER BY dueDate")
    fun getToDosOrderedByDueDate(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE finished = 1 ORDER BY dueDate")
    fun getFinishedToDos(): Flow<List<ToDo>>

    @Query("UPDATE todo SET finished = 1 WHERE id = :toDoId")
    suspend fun finishToDo(toDoId: Int)

    @Query("UPDATE todo SET finished = 0 WHERE id = :toDoId")
    suspend fun unFinishToDo(toDoId: Int)

    //does not work yet
    @Query("UPDATE todo SET tag = null WHERE tag = :tag")
    suspend fun deleteTagFromTodos(tag: String)
}