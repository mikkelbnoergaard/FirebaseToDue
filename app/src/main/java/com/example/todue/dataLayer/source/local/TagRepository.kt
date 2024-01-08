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
        toDoAmount: Int,
        sort: Boolean
        ) {
        val tag = Tag(
            title = title,
            toDoAmount = toDoAmount,
            sort = sort
        )
        if (checkIfTagExists(tag)) {
            increaseToDoAmount(tag)
        } else {
            if (tag.title == "") {
                return
            } else {
                dataSource.createTag(tag)
            }
        }
    }

    suspend fun deleteTag(tagTitle: String){
        dataSource.deleteTag(tagTitle)
    }

    fun getTagsOrderedByTitle(search: String): Flow<List<Tag>> {
        return dataSource.getTagsOrderedByTitle(search)
    }

    suspend fun sortByThisTag(tag: Tag) {
        dataSource.sortByThisTag(tagId = tag.id)
    }

    suspend fun dontSortByThisTag(tagTitle: String) {
        dataSource.dontSortByThisTag(tagTitle = tagTitle)
    }

    suspend fun resetTagSort() {
        dataSource.resetTagSort()
    }

    private suspend fun checkIfTagExists(tag: Tag): Boolean {
        return dataSource.checkIfTagExists(tagTitle = tag.title)
    }

    private suspend fun increaseToDoAmount(tag: Tag) {
        dataSource.increaseToDoAmount(tagTitle = tag.title)
    }

    suspend fun decreaseToDoAmount(tagTitle: String) {
        dataSource.decreaseToDoAmount(tagTitle = tagTitle)
    }

    suspend fun deleteUnusedTags() {
        dataSource.deleteUnusedTags()
    }
}