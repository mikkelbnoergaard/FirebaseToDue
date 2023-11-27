package com.example.todue.dataLayer.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    fun observeAll(): Flow<List<Tag>>

    @Insert
    suspend fun createTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tag GROUP BY title")
    fun getTagsOrderedByTitle(): Flow<List<Tag>>

    @Query("UPDATE tag SET sort = 1 WHERE id = :tagId")
    suspend fun sortByThisTag(tagId: Int)

    @Query("UPDATE tag SET sort = 0 WHERE id = :tagId")
    suspend fun dontSortByThisTag(tagId: Int)
}