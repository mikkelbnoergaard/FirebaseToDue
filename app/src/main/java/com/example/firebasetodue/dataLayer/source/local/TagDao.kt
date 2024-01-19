package com.example.firebasetodue.dataLayer.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    /*
    @Query("SELECT * FROM tag")
    fun observeAll(): Flow<List<Tag>>

     */

    @Insert
    suspend fun createTag(tag: Tag)

    @Query("DELETE FROM tag WHERE title IS :tagTitle")
    suspend fun deleteTag(tagTitle: String)

    @Query("SELECT * FROM tag WHERE title LIKE '%' || :search || '%' AND toDoAmount >= :showFinished OR sort IS 1 GROUP BY title")
    fun getTagsOrderedByTitle(search: String, showFinished: Int): Flow<List<Tag>>

    @Query("UPDATE tag SET sort = 1 WHERE id IS :tagId")
    suspend fun sortByThisTag(tagId: Int)

    @Query("UPDATE tag SET sort = 0 WHERE title IS :tagTitle")
    suspend fun dontSortByThisTag(tagTitle: String)

    @Query("SELECT (EXISTS (SELECT * FROM tag WHERE sort IS 1))")
    suspend fun checkIfSortByTags(): Boolean

    @Query("UPDATE tag SET sort = 0")
    suspend fun resetTagSort()

    @Query("SELECT (EXISTS (SELECT * FROM tag WHERE title IS :tagTitle))")
    suspend fun checkIfTagExists(tagTitle: String): Boolean

    @Query("UPDATE tag SET toDoAmount = toDoAmount + 1 WHERE title IS :tagTitle")
    suspend fun increaseToDoAmount(tagTitle: String)

    @Query("UPDATE tag SET toDoAmount = toDoAmount -1 WHERE title IS :tagTitle")
    suspend fun decreaseToDoAmount(tagTitle: String)

    @Query("DELETE FROM tag WHERE toDoAmount IS 0")
    suspend fun deleteUnusedTags()
}