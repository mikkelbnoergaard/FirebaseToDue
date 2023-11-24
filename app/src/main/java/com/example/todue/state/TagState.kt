package com.example.todue.state

import com.example.todue.ui.sortType.TagSortType
import com.example.todue.dataLayer.source.local.Tag
import com.example.todue.dataLayer.source.local.ToDo

data class TagState(
    val tags: List<Tag> = emptyList(),
    val tagsToSort: List<String> = emptyList(),
    val title: String = "",
    val toDoAmount: Int = 0,
    val isDeletingTag: Boolean = false,
    val tagSortType: TagSortType = TagSortType.TITLE
)
