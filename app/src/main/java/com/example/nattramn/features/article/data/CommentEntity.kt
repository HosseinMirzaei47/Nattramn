package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nattramn.features.user.data.UserEntity

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val commentId: Int,
    val user: UserEntity,
    val image: Int,
    val body: String,
    val commentOwnerArticleId: Long
)