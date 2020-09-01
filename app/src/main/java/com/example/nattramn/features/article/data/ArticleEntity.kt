package com.example.nattramn.features.article.data

import com.example.nattramn.features.user.data.UserEntity

data class ArticleEntity(
    val user: UserEntity,
    val date: String,
    val title: String,
    val body: String,
    val tags: ArrayList<TagEntity>,
    val comments: ArrayList<CommentEntity>,
    val suggestions: ArrayList<SuggestionsEntity>,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean
)