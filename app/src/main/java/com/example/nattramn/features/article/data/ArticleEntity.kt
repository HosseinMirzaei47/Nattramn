package com.example.nattramn.features.article.data

data class ArticleEntity(
    val user: User,
    val date: String,
    val title: String,
    val body: String,
    val tags: ArrayList<Tag>,
    val comments: ArrayList<Comment>,
    val suggestions: ArrayList<Suggestions>,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean
)