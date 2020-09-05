package com.example.nattramn.features.article.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.nattramn.features.user.data.UserEntity

@Entity(tableName = "articles")
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
    val userOwnerId: Long
)

data class ArticleAndTags(
    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "tagOwnerArticleId"
    )
    val tagEntity: List<TagEntity>
)

data class ArticleAndComments(
    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "commentOwnerArticleId"
    )
    val commentEntity: List<CommentEntity>
)

/*
data class ArticlesAndTagsAndComments(
)*/
