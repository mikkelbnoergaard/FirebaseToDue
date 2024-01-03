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

    @Query("SELECT * FROM todo t WHERE (title LIKE '%' || :search || '%' OR description LIKE '%' || :search || '%' OR tag LIKE '%' || :search || '%') AND EXISTS (SELECT title FROM tag tag WHERE tag.sort = 1 AND tag.title = t.tag) AND t.finished = 0 ORDER BY t.dueDate, t.DueTime")
    fun getToDosOrderedByTags(search: String): Flow<List<ToDo>>

    @Query("SELECT * FROM todo ORDER BY description")
    fun getToDosOrderedByDescription(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE finished = 0 AND (" +
            "title LIKE '%' || :search || '%'" +
            "OR description LIKE '%' || :search || '%'" +
            "OR tag LIKE '%' || :search || '%'" +
            ") ORDER BY dueDate, dueTime")
    fun getToDosOrderedByDueDate(search: String): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE finished = 1 ORDER BY dueDate, dueTime")
    fun getFinishedToDos(): Flow<List<ToDo>>

    @Query("UPDATE todo SET finished = 1 WHERE id = :toDoId")
    suspend fun finishToDo(toDoId: Int)

    @Query("UPDATE todo SET finished = 0 WHERE id = :toDoId")
    suspend fun unFinishToDo(toDoId: Int)

    @Query("SELECT * FROM todo WHERE dueDate = :date")
    fun getToDosByGivenDate(date: String): Flow<List<ToDo>>



    @Query("UPDATE todo SET title = :newTitle, description = :newDescription, tag = :newTag, dueDate = :newDueDate, dueTime = :newDueTime WHERE id = :toDoId")
    suspend fun editToDo(newTitle: String, newDescription: String, newTag: String, newDueDate: String, newDueTime: String, toDoId: Int)




    //does not work yet
    @Query("UPDATE todo SET tag = '' WHERE tag = :tag")
    suspend fun deleteTagFromToDos(tag: String)





    //for testing
    @Insert
    fun createTestToDoInDB(toDo: ToDo)

    @Query("SELECT title FROM todo WHERE id = :toDoId")
    fun getTestToDoTitle(toDoId: Int): String

    @Query("SELECT description FROM todo WHERE id = :toDoId")
    fun getTestToDoDescription(toDoId: Int): String

    @Query("SELECT tag FROM todo WHERE id = :toDoId")
    fun getTestToDoTag(toDoId: Int): String

    @Query("SELECT dueDate FROM todo WHERE id = :toDoId")
    fun getTestToDoDueDate(toDoId: Int): String

    @Query("SELECT dueTime FROM todo WHERE id = :toDoId")
    fun getTestToDoDueTime(toDoId: Int): String
}