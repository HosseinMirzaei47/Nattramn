package com.example.nattramn.features.article.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Relation
import com.example.nattramn.features.user.data.UserEntity

@Entity(
    tableName = "articles",
    primaryKeys = ["slug"],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            onUpdate = CASCADE,
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["ownerUsername"]
        )
    ]
)
data class ArticleEntity(
    val slug: String,
    val date: String,
    val title: String,
    val body: String,
    val likes: String,
    val favoriteCount: Int,
    val bookmarked: Boolean,
    val ownerUsername: String
)

data class ArticleWithCommentsAndTags(

    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "slug",
        entityColumn = "articleSlug"
    )
    val commentEntity: List<CommentEntity>,

    @Relation(
        parentColumn = "slug",
        entityColumn = "articleSlug"
    )
    val tagEntity: List<TagEntity>

)