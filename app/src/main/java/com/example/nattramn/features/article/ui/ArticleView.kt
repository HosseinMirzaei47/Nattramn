package com.example.nattramn.features.article.ui

import com.example.nattramn.models.User

data class ArticleView(
    val user: User,
    val date: String,
    val title: String,
    val body: String,
    val tagViews: ArrayList<TagView>,
    val commentViews: ArrayList<CommentView>,
    val suggestions: ArrayList<SuggestionsView>,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean
)