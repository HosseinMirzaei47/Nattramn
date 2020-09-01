package com.example.nattramn.features.article.ui

import com.example.nattramn.features.user.ui.UserView

data class CommentView(
    val userView: UserView,
    val image: Int,
    val body: String
)