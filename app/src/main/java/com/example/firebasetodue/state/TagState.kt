package com.example.firebasetodue.state

import com.example.firebasetodue.ui.sortType.TagSortType
import com.example.firebasetodue.dataLayer.source.local.Tag

data class TagState(

    val tags: List<Tag> = emptyList(),
    val tagsToSort: List<String> = emptyList(),
    val title: String = "",
    val toDoAmount: Int = 0,
    val sort: Boolean = false,
    val isDeletingTag: Boolean = false,
    val tagSortType: TagSortType = TagSortType.TITLE,
    val searchInTags: String = ""

)