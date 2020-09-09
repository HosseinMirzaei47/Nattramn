package com.example.nattramn.features.article.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Relation
import com.example.nattramn.features.user.data.UserEntity
import java.util.*

@Entity(
    tableName = "articles",
    primaryKeys = ["articleId"],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            onUpdate = CASCADE,
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["ownerId"]
        )
    ]
)
data class ArticleEntity(
    val articleId: Int,
    val date: Date,
    val title: String,
    val body: String,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean,
    /*@ForeignKey(
        onDelete = CASCADE,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        entity = UserEntity::class
    )*/
    val ownerId: Long
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