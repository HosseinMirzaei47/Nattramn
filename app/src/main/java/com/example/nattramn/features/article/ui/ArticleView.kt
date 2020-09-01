package com.example.nattramn.features.article.ui

import com.example.nattramn.features.user.ui.UserView

data class ArticleView(
    val userView: UserView,
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