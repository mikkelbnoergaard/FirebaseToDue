package com.example.todue.dataLayer.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagRepository @Inject constructor (
    private val dataSource: TagDao
){
    /*
    fun observeAll() : Flow<List<Tag>> {
        return dataSource.observeAll()
    }

     */

    suspend fun createTag(
        title: String,
        toDoAmount: Int) {
        val tag = Tag(
            title = title,
            toDoAmount = toDoAmount
        )
        dataSource.createTag(tag)
    }

    suspend fun deleteTag(tag: Tag){
        dataSource.deleteTag(tag)
    }

    fun getTagsOrderedByTitle() : Flow<List<Tag>> {
        return dataSource.getTagsOrderedByTitle()
    }
}