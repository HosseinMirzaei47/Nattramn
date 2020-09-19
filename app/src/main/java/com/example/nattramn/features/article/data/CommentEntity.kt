package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val commentId: Int,
    val username: String,
    val image: String,
    val body: String,
    val articleSlug: String
) {
    companion object {
        fun convertComment(
            username: String,
            commentBody: String,
            image: String,
            ownerSlug: String
        ): CommentEntity {
            return CommentEntity(
                commentId = 0,
                username = username,
                image = image,
                body = commentBody,
                articleSlug = ownerSlug
            )
        }
    }
}