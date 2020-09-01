package com.example.nattramn.features.article.data

import com.example.nattramn.features.user.data.UserEntity

data class CommentEntity(
    val user: UserEntity,
    val image: Int,
    val body: String
)