package com.example.nattramn.features.article.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.nattramn.features.user.data.UserEntity
import java.util.*

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val articleId: Int,
    val date: Date,
    val title: String,
    val body: String,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean,
    @ForeignKey(
        onDelete = CASCADE,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        entity = UserEntity::class
    ) val userOwnerId: Long
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