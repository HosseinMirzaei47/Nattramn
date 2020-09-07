package com.example.nattramn.features.article.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.nattramn.features.user.data.UserEntity
import java.util.*

@Entity(
    tableName = "articles",
    primaryKeys = ["article_id"],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            onUpdate = CASCADE,
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["owner_id"]
        )
    ]
)
data class ArticleEntity(
    /*@PrimaryKey val articleId: Int,*/
    @ColumnInfo(name = "article_id")
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
    @ColumnInfo(name = "owner_id")
    val ownerId: Long
)

data class ArticleWithCommentsAndTags(

    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "article_id",
        entityColumn = "commentOwnerArticleId"
    )
    val commentEntity: List<CommentEntity>,

    @Relation(
        parentColumn = "article_id",
        entityColumn = "tagOwnerArticleId"
    )
    val tagEntity: List<TagEntity>

)