package com.example.nattramn.features.article.ui

import com.example.nattramn.models.User

data class CommentView(
    val user: User,
    val image: Int,
    val body: String
)