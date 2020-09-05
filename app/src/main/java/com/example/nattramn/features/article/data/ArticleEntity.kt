package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nattramn.features.user.data.UserEntity

@Entity
data class ArticleEntity(
    @PrimaryKey val articleId: Int,
    val user: UserEntity,
    val date: String,
    val title: String,
    val body: String,
    val tags: ArrayList<TagEntity>,
    val comments: ArrayList<CommentEntity>,
    val suggestions: ArrayList<SuggestionsEntity>,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean,
    val articleOwnerId: Long
)