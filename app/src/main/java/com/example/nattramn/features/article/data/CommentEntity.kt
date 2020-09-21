package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nattramn.features.article.ui.CommentView

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val commentId: String,
    val username: String,
    val image: String,
    val body: String,
    val createdAt: String,
    val articleSlug: String
) {

    fun toCommentView(): CommentView {
        return CommentView(
            username = username,
            image = image,
            body = body
        )
    }

    companion object {
        fun convertComment(
            id: String,
            username: String,
            commentBody: String,
            image: String,
            createdAt: String,
            ownerSlug: String
        ): CommentEntity {
            return CommentEntity(
                commentId = id,
                username = username,
                image = image,
                body = commentBody,
                createdAt = createdAt,
                articleSlug = ownerSlug
            )
        }
    }
}