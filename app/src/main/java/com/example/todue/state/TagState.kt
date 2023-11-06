package com.example.todue.state

import com.example.todue.dataLayer.TagSortType
import com.example.todue.dataLayer.Tag

data class TagState(
    val tags: List<Tag> = emptyList(),
    val title: String = "",
    val toDoAmount: Int = 0,
    val isDeletingTag: Boolean = false,
    val tagSortType: TagSortType = TagSortType.TITLE
)
