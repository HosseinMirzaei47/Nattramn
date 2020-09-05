package com.example.nattramn.features.article.data

import androidx.room.PrimaryKey
import com.example.nattramn.features.user.data.UserEntity

data class CommentEntity(
    @PrimaryKey val commentId: Int,
    val user: UserEntity,
    val image: Int,
    val body: String,
    val commentOwnerArticleId: Long
)