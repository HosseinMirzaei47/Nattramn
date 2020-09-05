package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val commentId: Int,
    val userId: Int,
    val image: Int,
    val body: String,
    val commentOwnerArticleId: Long
)