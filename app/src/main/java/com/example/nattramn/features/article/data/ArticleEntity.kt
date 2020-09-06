package com.example.nattramn.features.article.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val articleId: Int,
    val userId: Int,
    val date: Date,
    val title: String,
    val body: String,
    /*val tags: ArrayList<TagEntity>,
    val comments: ArrayList<CommentEntity>,
    val suggestions: ArrayList<ArticleEntity>,*/
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean,
    val userOwnerId: Long
)

data class ArticleWithCommentsAndTags(

    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "commentOwnerArticleId"
    )
    val commentEntity: List<CommentEntity>,

    @Relation(
        parentColumn = "articleId",
        entityColumn = "tagOwnerArticleId"
    )
    val tagEntity: List<TagEntity>

)